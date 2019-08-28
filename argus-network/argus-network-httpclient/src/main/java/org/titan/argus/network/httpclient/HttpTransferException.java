package org.titan.argus.network.httpclient;

/**
 * @author starboyate
 */
public class HttpTransferException extends Exception {
	public HttpTransferException(String message) {
		super(message);
	}

	public HttpTransferException(String message, Throwable cause) {
		super(message, cause);
	}
}
