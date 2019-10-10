package com.sexyuncle.springboot.security.exception;

public class BookNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8402875177807190482L;

	public BookNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public BookNotFoundException() {
		super();
	}

}