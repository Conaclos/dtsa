package dtsa.mapper.client.base;

import java.util.List;

import dtsa.mapper.client.request.RequestProcessor;
import dtsa.mapper.client.request.Request;
import dtsa.mapper.client.response.Response;
import dtsa.mapper.client.response.ResponseProcessor;
import dtsa.mapper.util.annotation.Nullable;
import dtsa.mapper.util.communication.InputListener;
import dtsa.mapper.util.communication.RepeatableTask;

public class MapperClientSession2
		extends RepeatableTask {

// Creation
	public MapperClientSession2 (InputListener <Request> aListener,
			List <RequestProcessor> aRequestVisitors, List <ResponseProcessor> aResponseVisitors) {
		listener = aListener;
		requestVisitors = aRequestVisitors;
		responseVisitors = aResponseVisitors;
		
		assert listener == aListener: "ensure: `listener' set with `aListener'.";
		assert requestVisitors == aRequestVisitors: "ensure: `requestVisitors' set with `aRequestVisitors'.";
		assert responseVisitors == aResponseVisitors: "ensure: `responseVisitors' set with `aResponseVisitors'.";
	}

// Constant
	/**
	 * Timeout in seconds to wait the next request.
	 */
	public final static int Timeout = 10;

// Thread operation
	@Override
	public void interrupt () {
		super.interrupt ();
		listener.interrupt ();
	}

// Change
	@Override
	protected void process () {
		@Nullable Request next;
		
		try {
			next = listener.maybeNext (Timeout);
			if (next != null) {
				processRequest (next);
			}
			else {
				System.out.println ("Fin du temps"); // TODO remove it
				interrupt ();
			}
		}
		catch (InterruptedException e) {
			Thread.currentThread ().interrupt ();
		}
	}

// Implementation
	/**
	 * Listener for requests.
	 */
	protected InputListener <Request> listener;

	/**
	 * Request visitors.
	 */
	protected List <RequestProcessor> requestVisitors;

	/**
	 * Response visitors.
	 */
	protected List <ResponseProcessor> responseVisitors;

	/**
	 * Process `aVisited' with each processor of `requestVisitors'.
	 * And if there is a response, then process it via `processResponse'.
	 * 
	 * @param aVisited
	 */
	protected void processRequest (Request aVisited) {
		@Nullable Response maybeAnswer;
		Request visited;
		
		visited = aVisited;
		for (RequestProcessor p : requestVisitors) {
			assert p != null: "check: `p' exists.";
			
			if (p.isReactive ()) {
				visited.process (p);
				maybeAnswer = aVisited.response ();
				if (maybeAnswer != null) {
					processResponse (maybeAnswer);
				}
				else {
					assert false: "check: reactive processor answers.";
				}
				visited = visited.partialCLone ();
			}
			else {
				visited.process (p);
			}
		}
	}

	/**
	 * Process `aVisited' with each processor of `responseVisitors'.
	 * 
	 * @param aVisited
	 */
	protected void processResponse (Response aVisited) {
		for (ResponseProcessor p : responseVisitors) {
			assert p != null: "check: `p' exists.";
			aVisited.process (p);
		}
	}

}
