package org.orcid.memberportal.service.member.mail.client;

import java.io.File;
import org.orcid.memberportal.service.member.mail.MailException;

public interface MailClient {
    void sendMailWithAttachment(
        String to,
        String cc,
        String subject,
        String html,
        File attachment
    ) throws MailException;

    void sendMail(String to, String cc, String subject, String html)
        throws MailException;
}
