package dtsa.mapper.client.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dtsa.mapper.client.response.MapperExceptionResponse;
import dtsa.mapper.client.response.RetrieveMapperResponse;
import dtsa.util.annotation.Nullable;

/**
 *
 * @description
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class ResultMergingClientRequest
extends ClientRequest {

	// Creation
		/**
		 * Create a retrieve request with `aPath' as `getPath'.
		 * @param aPath - {@link #getPath()}
		 * @param aUris - {@link #getUris()}
		 */
		@JsonCreator
		public ResultMergingClientRequest (@JsonProperty ("path") String aPath, @JsonProperty ("uris") String [] aUris) {
			path = aPath;
			uris = aUris;

			assert getPath () == aPath: "ensure: `getPath' set with `aPath'.";
			assert getUris () == aUris: "ensure: `getUris' set with `aUris'.";
		}

	// Access
		@Override
		public @Nullable RetrieveMapperResponse response () {
			return response;
		}

		/**
		 * @return Path where the entity should be stored.
		 */
		public String getPath () {
			return path;
		}

		/**
		 * @return Results locations.
		 */
		public String [] getUris () {
			return uris;
		}

		@Override
		public ResultMergingClientRequest partialCLone () {
			ResultMergingClientRequest result;

			result = new ResultMergingClientRequest (path, uris);

			assert this != result: "current object and result have not the same reference.";
			assert partialEquals (result): "ensure: current object and result are partially equal.";
			return result;
		}

	// Status
		@Override
		public boolean partialEquals (Object aOther) {
			boolean result;

			if (getClass () == aOther.getClass ()) {
				ResultMergingClientRequest temp = (ResultMergingClientRequest) aOther;

				result = path.equals (temp.getPath ()) && uris.equals (temp.getUris ());
			}
			else {
				result = false;
			}

			assert (! result) || (getClass () == aOther.getClass ()): "ensure: equal implies same type.";
			assert getClass () == aOther.getClass () || (! result): "esnure: different type implies not equal";
			return result;
		}

	// Change
		/**
		 * Set `response' with `aResponse'.
		 * @param aResponse - {@link #response()}
		 */
		public void setResponse (RetrieveMapperResponse aResponse) {
			assert ! hasException (): "require: has not exception.";

			response = aResponse;

			assert response () == aResponse: "ensure: `response' set with `aResponse'.";
		}

		/**
		 * Set `exception' with `aException'.
		 * @param aException - {@link #exception()}
		 */
		public void setException (MapperExceptionResponse aException) {
			assert ! hasResponse (): "require: has not response.";

			exception = aException;

			assert exception () == aException: "ensure: `exception' set with `aException'.";
		}

	// Other
		@Override
		public void accept (ClientRequestVisitor aProcessor) {
			aProcessor.visitResultMerging (this);

			assert aProcessor.isReactive () == (hasResponse () || hasException ()): "ensure: responded if `aProcessor' is reactive.";
		}

	// Implementation
		/**
		 * @see #getPath ()
		 */
		protected String path;

		/**
		 * @see #getUris ()
		 */
		protected String [] uris;

		/**
		 * Answer of this current request.
		 */
		protected @Nullable RetrieveMapperResponse response;

}
