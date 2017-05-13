package jtelecom.mail;

import javax.mail.MessagingException;

/**
 * @author Moiseienko Petro
 * @since 13.04.2017.
 */

@SuppressWarnings("serial")
public final class EmailException extends RuntimeException {

    protected static final String MISSING_RECIPIENT = "email is not valid: missing recipients";
    protected static final String MISSING_SUBJECT = "Email is not valid: missing subject";
    protected static final String MISSING_CONTENT = "Email is not valid: missing content body";

    protected EmailException(final String message) {
        super(message);
    }

    protected EmailException(final String message, final MessagingException cause) {
        super(message, cause);
    }
}