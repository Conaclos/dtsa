package dtsa.util.communication.session;

import java.net.Socket;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

import dtsa.util.annotation.Nullable;
import dtsa.util.annotation.ReadOnly;
import dtsa.util.communication.base.Request;
import dtsa.util.communication.base.RequestVisitor;
import dtsa.util.communication.base.Response;
import dtsa.util.communication.base.ResponseVisitor;
import dtsa.util.communication.listener.ConvertibleObjectListener;
import dtsa.util.communication.writer.ConvertibleObjectWriter;
import dtsa.util.log.Logger;

/**
 * 
 * @description High level client session based on request and response exchange.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class DefaultServerSession 
	extends ServerSession {

// Creation
	/**
	 * 
	 * @param aSocket - socket of session
	 * @param aLogger - logger
	 * @param aRequestProcessors - visitors of requests.
	 * @param aResponseProcessors - visitors of responses
	 * @param aListener - request listener
	 */
	@Requires ({
		"aSocket != null",
		"aLogger != null",
		"aRequestProcessors != null",
		"aResponseProcessors != null",
		"aListener != null"
	})
	@Ensures ({
		"requestProcessors == aRequestProcessors",
		"responseProcessors == aResponseProcessors",
		"listener == aListener",
		"isConcurrent == aClientConfiguration.isConcurrent ()",
		"timeout == aClientConfiguration.getTimeout ()"
	})
	public DefaultServerSession (Socket aSocket, Logger aLogger, ServerSessionConfiguration aClientConfiguration,
			ConvertibleObjectListener <Request <? extends RequestVisitor>> aListener, ConvertibleObjectWriter <Response <? extends ResponseVisitor>> aWwriter, 
			RequestVisitor [] aRequestProcessors, ResponseVisitor [] aResponseProcessors) {
		
		super (aSocket);
		
		requestProcessors = aRequestProcessors;
		responseProcessors = aResponseProcessors;
		listener = aListener;
		isConcurrent = aClientConfiguration.isConcurrent ();
		timeout = aClientConfiguration.getTimeout ();
		writer = aWwriter;
		
		listener.start ();
		writer.start ();
		
		
		assert requestProcessors == aRequestProcessors: "ensure: `requestProcessors' set with `aRequestProcessors'";
		assert responseProcessors == aResponseProcessors: "ensure: `responseProcessors' set with `aResponseProcessors'";
		assert listener == aListener: "ensure: `listener' set with `aListener'";
		assert isConcurrent == aClientConfiguration.isConcurrent (): "ensure: `isConcurrent' set with `aClientConfiguration.isConcurrent ()'";
		assert timeout == aClientConfiguration.getTimeout (): "ensure: `timeout' set with `aClientConfiguration.getTimeout ()'";
	}
	
// Thread operation
	@Override
	public void interrupt () {
		super.interrupt ();
		listener.interrupt ();
		writer.interrupt ();
	}

// Other
	@Override
	protected void process () {
		@Nullable Request <? extends RequestVisitor> maybe;
		
		try {
			maybe = listener.maybeNext (timeout);
			if (maybe != null) {
				visitRequest (maybe);
			}
			else {
				Thread.currentThread ().interrupt ();
			}
		}
		catch (InterruptedException e) {
			Thread.currentThread ().interrupt ();
		}
	}
	
	/**
	 * Visit `aVisited' with  all `requestProcessors'.
	 * @param aVisited
	 */
	protected void visitRequest (Request <? extends RequestVisitor> aVisited) {
		boolean localIsConcurrent = isConcurrent;
		
		for (RequestVisitor visitor : requestProcessors) {
			if (localIsConcurrent) {
				(new Thread (() -> visitRequestWith (aVisited, visitor))).start ();
			}
			else {
				visitRequestWith (aVisited, visitor);
			}
		}
	}
	
	protected void visitRequestWith (Request <? extends RequestVisitor> aVisited, RequestVisitor aVisitor) {
		Request <? extends RequestVisitor> fresh;
		@Nullable Response <? extends ResponseVisitor> localResponse;
		
		if (aVisitor.isReactive ()) {
			fresh = aVisited.partialCLone ();
			fresh.attemptAccept (aVisitor);
			
			localResponse = fresh.response ();
			if (localResponse != null) {
				visitResponse (localResponse);
			}
			else {
				localResponse = fresh.exception ();
				if (localResponse != null) {
					visitResponse (localResponse);
				}
			}
		}
		else {
			aVisited.attemptAccept (aVisitor);
		}
	}
	
	/**
	 * Visit `aVisited' with  all `responseProcessors'.
	 * @param aVisited
	 */
	protected void visitResponse (Response <? extends ResponseVisitor> aVisited) {
		boolean localIsConcurrent = isConcurrent;
		
		try {
			writer.write (aVisited);
		}
		catch (InterruptedException e) {
			Thread.currentThread ().interrupt ();
			e.printStackTrace();
		}
		
		for (ResponseVisitor visitor : responseProcessors) {
			if (localIsConcurrent) {
				(new Thread (() -> aVisited.attemptAccept (visitor))).start ();
			}
			else {
				aVisited.attemptAccept (visitor);
			}
		}
	}
	
// Implementation
	/**
	 * Visitors of requests.
	 */
	protected @ReadOnly RequestVisitor [] requestProcessors;
	
	/**
	 * Visitor of responses.
	 */
	protected @ReadOnly ResponseVisitor [] responseProcessors;
	
	/**
	 * Listener of requests.
	 */
	protected ConvertibleObjectListener <Request <? extends RequestVisitor>> listener;
	
	/**
	 * Writer of responses.
	 */
	protected ConvertibleObjectWriter <Response <? extends ResponseVisitor>> writer;
	
	/**
	 * Should request and response processing be launched in a separate thread?
	 */
	protected boolean isConcurrent;
	
	/**
	 * Time in seconds to wait a new request.
	 */
	protected int timeout;
	
}
