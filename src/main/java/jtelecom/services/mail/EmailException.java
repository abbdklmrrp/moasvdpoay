package jtelecom.services.mail;


/**
 * @author Moiseienko Petro
 * @since 13.04.2017.
 */


public final class EmailException extends RuntimeException {

    protected static final String MISSING_RECIPIENT = "Email is not valid: missing recipient";
    protected static final String MISSING_SUBJECT = "Email is not valid: missing subject";
    protected static final String MISSING_CONTENT = "Email is not valid: missing content body";
    protected static final String WRONG_EMAIL_ADDRESS = "Email is not valid: wrong address";

    protected EmailException(final String message) {
        super(message);
    }

}