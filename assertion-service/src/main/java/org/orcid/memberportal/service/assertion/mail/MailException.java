package org.orcid.memberportal.service.assertion.mail;

public class MailException extends Exception {

      private static final long serialVersionUID = 1L;

      public MailException(String message) {
            super(message);
      }

      public MailException(String message, Throwable cause) {
            super(message, cause);
      }
}
