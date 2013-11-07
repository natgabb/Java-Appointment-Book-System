package dw314.appointmentbook.exceptions;
/**
 * This class describes the NonExistingAppointmentException. This
 * Exception's default message is "The appointment does not exist."
 * 
 * @author Kim Parisé
 * @author Natacha Gabbamonte
 * @author Caroline Castonguay-Boisvert
 * 
 * @since 1.5 10/21/2011
 */
@SuppressWarnings("serial")
public class NonExistingAppointmentException extends Exception{
	
	/**
	 * Constructs a NonExistingAppointmentException with 
     * "The appointment does not exist" 
	 * as its error message.
	 */
	public NonExistingAppointmentException (){
		super("The appointment does not exist.");
	}

	/**
	 * Constructs a NonExistingAppointmentException with the specified message.
	 * 
	 * @param message
	 *            the specified error message.
	 */
	public NonExistingAppointmentException(String message) {
		super(message);
	}
}
