package wu.justin.rest2.exception;

public final class ApiError {
	private int status;
	// private int code;
	private String message;
	private String devMessage;

	public ApiError() {
		this.status = 500;
		// this.code = 101;
		this.message = "internal server error";
		this.devMessage = "";
	}

	/**
	 * @return The corresponding HTTP status code.
	 */
	public int getStatus() {
		return this.status;
	}

	/**
	 * @return specific error code that can be used to obtain more information.
	 */
	/*
	 * public int getCode() { return this.code; }
	 */

	/**
	 * @return A simple, easy to understand message that you can be shown directly
	 *         to the application end-user.
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * @return A clear, plain text explanation with technical details
	 */
	public String getDevMessage() {
		return this.devMessage;
	}

	/**
	 * @param status The corresponding HTTP status code.
	 */
	public ApiError setStatus(final int status) {
		this.status = status;
		return this;
	}

	/**
	 * @param code specific error code that can be used to obtain more information.
	 */
	/*
	 * public void setCode(final int code) { this.code = code; }
	 */

	/**
	 * @param message A simple, easy to understand message that you can be shown
	 *                directly to the application end-user.
	 */
	public ApiError setMessage(final String message) {
		this.message = message;
		return this;
	}

	/**
	 * @param devMessage A clear, plain text explanation with technical details that
	 *                   might assist a developer.
	 */
	public ApiError setDevMessage(final String devMessage) {
		this.devMessage = devMessage;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(this.status);
		builder.append(", ");
		// builder.append(this.code);
		if ((null != this.message) && !this.message.isEmpty()) {
			builder.append(", [").append(this.message).append("]");
		}
		builder.append("]");
		return builder.toString();
	}

}
