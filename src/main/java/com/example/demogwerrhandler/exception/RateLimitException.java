package com.example.demogwerrhandler.exception;

public class RateLimitException extends RuntimeException {

	public RateLimitException(String msg) {
		super(msg);
	}

}
