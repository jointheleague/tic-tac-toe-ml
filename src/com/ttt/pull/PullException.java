package com.ttt.pull;

import java.io.IOException;

public class PullException extends IOException {
	private static final long serialVersionUID = 4968577520308248644L;

	public PullException(String message) {
		super(message);
	}

	public PullException(Throwable cause) {
		super(cause);
	}

	public PullException(String message, Throwable cause) {
		super(message, cause);
	}
}
