package dw314.appointmentbook.exceptions;

/**
 * This class describes the DateNotAvailableException. This
 * Exception's default message is "This Date is not available."
 * 
 * @author Kim Parisé
 * @author Natacha Gabbamonte
 * @author Caroline Castonguay-Boisvert
 * 
 * @since 1.5 10/21/2011
 */
@SuppressWarnings("serial")
public class DateNotAvailableException extends Exception {

	/**
	 * Constructs a DateNotAvailableException with "This Date is not available"
	 * as its error message.
	 */

	public DateNotAvailableException() {
		super("This Date is not available");
	}

	/**
	 * Constructs a DateNotAvailableException with the specified message.
	 * 
	 * @param message
	 *            the specified error message.
	 */
	public DateNotAvailableException(String message) {
		super(message);
	}

}
