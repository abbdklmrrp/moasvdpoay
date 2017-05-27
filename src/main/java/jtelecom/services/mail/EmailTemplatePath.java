package jtelecom.services.mail;

/**
 * @author Moiseienko Petro
 * @since 19.05.2017.
 */
public enum EmailTemplatePath {
    REGISTRATION("registration.subject", "fm_registrationTemplate.fpl"),
    REGISTRATION_WITHOUT_PASSWORD("registration.subject", "fm_registrationAndPasswordTemplate.fpl"),
    COMPLAINT_SENT("complaint.sent.subject", "fm_complaintSentTemplate.fpl"),
    COMPLAINT_PROCESSING("complaint.processing.subject", "fm_complaintProcessingTemplate.fpl"),
    COMPLAINT_PROCESSED("complaint.processed.subject", "fm_complaintProcessedTemplate.fpl"),
    PRODUCT_ACTIVATED("product.activated.subject", "fm_productActivatedTemplate.fpl"),
    PRODUCT_SUSPENDED("product.suspended.subject", "fm_productSuspendedTemplate.fpl"),
    PRODUCT_WILL_SUSPEND("product.will.suspend.subject", "fm_productWillSuspendTemplate.fpl"),
    PRODUCT_DEACTIVATED("product.deactivated.subject", "fm_productDeactivatedTemplate.fpl"),
    PRODUCT_DELETED("product.deleted.subject", "fm_productDeletedTemplate.fpl"),
    PRODUCT_PROCESSING("product.processing.subject", "fm_productProcessingTemplate.fpl"),
    NEW_PRODUCT("new.product.subject", "fm_newProductTemplate.fpl"),
    NEW_PASSWORD("new.password.subject", "fm_newPasswordTemplate.fpl"),
    PASSWORD_CHANGED("password.changed.subject", "fm_passwordChangedTemplate.fpl"),
    ACTIVATED("activated.subject","fm_activatedTemplate.fpl"),
    BANNED("banned.subject","fm_bannedTemplate.fpl");


    private final String subject;
    private final String templateName;

    EmailTemplatePath(String subject, String templateName) {
        this.subject = subject;
        this.templateName = templateName;
    }

    public String getSubject() {
        return subject;
    }

    public String getTemplateName() {
        return templateName;
    }
}
