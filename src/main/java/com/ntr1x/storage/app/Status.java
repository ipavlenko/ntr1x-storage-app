package com.ntr1x.storage.app;

import javax.ws.rs.core.Response;

public enum Status {

	UNAUTHORIZED(Response.Status.UNAUTHORIZED),
	
	FORBIDDEN(Response.Status.FORBIDDEN),
	WRONG_CREDENTIALS(Response.Status.FORBIDDEN),
	USER_NOT_FOUND(Response.Status.FORBIDDEN),
	
	INVALID_EMAIL(Response.Status.BAD_REQUEST),
	INVALID_PASSWORD(Response.Status.BAD_REQUEST),
	INVALID_TOKEN(Response.Status.BAD_REQUEST),
	INVALID_DATA(Response.Status.BAD_REQUEST),
	
	DUPLICATE_EMAIL(Response.Status.CONFLICT),
	
	UNKNOWN(Response.Status.INTERNAL_SERVER_ERROR),
	
	INVALID_BIRTH_DATE(Response.Status.BAD_REQUEST),
	
	DUPLICATE_PHONE(Response.Status.CONFLICT),

	LIMIT_EXCEEDED(Response.Status.FORBIDDEN),
	
	WRONG_CAPTCHA(Response.Status.FORBIDDEN),
	
	INVALID_PHONE(Response.Status.BAD_REQUEST),
	
	;
	
	public final int status;
	
	private Status(Response.Status status) {
		
		this.status = status.getStatusCode();
	}
}	