package dtsa.mapper.cloud.mapped.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

import dtsa.util.annotation.Nullable;
import dtsa.util.communication.base.RepeatableTask;
import dtsa.util.communication.base.Request;
import dtsa.util.communication.base.RequestVisitor;
import dtsa.util.communication.base.Response;
import dtsa.util.communication.base.ResponseVisitor;
import dtsa.util.communication.listener.ConvertibleObjectListener;
import dtsa.util.communication.writer.ConvertibleObjectWriter;


public class MappedProxy
	extends RepeatableTask {

// Creation
	/**
	 *
	 * @param aIp - Service IP
	 * @param aPort - Service port
	 */
	public MappedProxy (int aPort, ServiceAvailability aAvailabilty,
			Function <BufferedReader, ConvertibleObjectListener <? extends Response <? extends ResponseVisitor>>> aListenerFactory,
			Function <BufferedWriter, ConvertibleObjectWriter <Request <? extends RequestVisitor>>> aWriterFactory) {

		port = aPort;
		availabilty = aAvailabilty;
		listenerFactory = aListenerFactory;
		writerFactory = aWriterFactory;

		lock = new ReentrantLock ();
		initializedCondition = lock.newCondition ();

		availabilty.start ();
	}

// Thread operation
	@Override
	public void interrupt () {
		super.interrupt ();

		@Nullable Socket localSocket = socket;
		if (localSocket != null) {
			try {
				localSocket.close ();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Nullable RepeatableTask temp;
		temp = listener;
		if (temp != null) {
			temp.interrupt ();
		}

		temp = writer;
		if (temp != null) {
			temp.interrupt ();
		}
	}

// Access
	/**
	 * Get and remove the oldest input.
	 * Wait the initialization of the communication until `aInitailzedTimeout'.
	 * ANd then if no input is available, then wait one until `aTimeout'.
	 *
	 * @SideEffect
	 *
	 * @param aInitailzedTimeout - in milliseconds
	 * @param aTimeout - in seconds
	 *
	 * @return Oldest input or null if `timeout' elapses before an input is available.
	 * @throws InterruptedException - `aInitailzedTimeout' or `aTimeout' reached.
	 */
	public @Nullable Response <? extends ResponseVisitor> maybeNext (long aInitailzedTimeout, long aTimeout) throws InterruptedException {
		@Nullable ConvertibleObjectListener <? extends Response <? extends ResponseVisitor>> localListener;
		@Nullable Response <? extends ResponseVisitor> result;

		localListener = listener;
		if (localListener != null) {
			result = localListener.maybeNext (aTimeout);
		}
		else {
			initializedCondition.wait (aInitailzedTimeout);

			localListener = listener;
			if (localListener != null) {
				result = localListener.maybeNext (aTimeout);
			}
			else {
				result = null;
			}
		}

		return result;
	}

// Other
	/**
	 * Transmitt `aObject'.
	 * @param aObject
	 * @throws InterruptedException
	 */
	public void write (Request <? extends RequestVisitor> aObject) throws InterruptedException {
		@Nullable ConvertibleObjectWriter <Request <? extends RequestVisitor>> localWriter;

		lock.lock ();

		try {
			localWriter = writer;
			while (localWriter == null) {
				initializedCondition.await ();
				localWriter = writer;
			}

			System.out.println ("writing");

			localWriter.write (aObject);
		}
		finally {
			lock.unlock ();
		}
	}

// Implementation
	/**
	 * Lock
	 */
	protected Lock lock;

	/**
	 * COndition variable for initialization.
	 */
	protected Condition initializedCondition;

	/**
	 * Service port.
	 */
	protected int port;

	/**
	 * Availability notifier.
	 */
	protected ServiceAvailability availabilty;

	/**
	 * Listener of response.
	 */
	protected Function <BufferedReader, ConvertibleObjectListener <? extends Response <? extends ResponseVisitor>>> listenerFactory;

	/**
	 * Factory for {@link #listener}
	 */
	protected Function <BufferedWriter, ConvertibleObjectWriter <Request <? extends RequestVisitor>>> writerFactory;

	/**
	 * Factory for {@link #writer}
	 */
	protected @Nullable ConvertibleObjectListener <? extends Response <? extends ResponseVisitor>> listener;

	/**
	 * Listener of request.
	 */
	protected @Nullable ConvertibleObjectWriter <Request <? extends RequestVisitor>> writer;

	/**
	 * Socket of the service client.
	 */
	protected @Nullable Socket socket;

	@Override
	protected void initialize () {
		Socket localSocket;

		try {
			availabilty.waitAvailability ();
		}
		catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println (availabilty.getIp () + ":" + port);

			localSocket = new Socket (availabilty.getIp (), port);
			socket = localSocket;
			listener = listenerFactory.apply (new BufferedReader (new InputStreamReader (localSocket.getInputStream ())));
			writer = writerFactory.apply (new BufferedWriter (new OutputStreamWriter (localSocket.getOutputStream ())));

			listener.start ();
			writer.start ();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println ("available");

		lock.lock ();
		initializedCondition.signalAll ();
		lock.unlock ();
	}

	@Override
	protected void process () {
		isActive.set (false);
	}

}
