package org.orcid.memberportal.service.user.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.orcid.memberportal.service.user.UserServiceApp;
import org.orcid.memberportal.service.user.config.ApplicationProperties;
import org.orcid.memberportal.service.user.config.Constants;
import org.orcid.memberportal.service.user.domain.User;
import org.orcid.memberportal.service.user.mail.MailException;
import org.orcid.memberportal.service.user.mail.client.impl.MailgunClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * Integration tests for {@link MailService}.
 */
@SpringBootTest(classes = UserServiceApp.class)
public class MailServiceIT {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Spy
    private MailgunClient mailgunClient;

    @Captor
    private ArgumentCaptor<String> subjectCaptor;

    @Captor
    private ArgumentCaptor<String> recipientCaptor;

    @Captor
    private ArgumentCaptor<String> contentCaptor;

    private MailService mailService;

    @BeforeEach
    public void setup() throws MailException {
        MockitoAnnotations.initMocks(this);
        doNothing()
            .when(mailgunClient)
            .sendMail(anyString(), anyString(), anyString());
        mailService =
            new MailService(
                applicationProperties,
                messageSource,
                templateEngine,
                mailgunClient
            );
    }

    @Test
    public void testSendActivationEmail() throws Exception {
        User user = new User();
        user.setLangKey(Constants.DEFAULT_LANGUAGE);
        user.setEmail("john.doe@example.com");
        mailService.sendActivationEmail(user);
        verify(mailgunClient, Mockito.times(1))
            .sendMail(
                recipientCaptor.capture(),
                subjectCaptor.capture(),
                contentCaptor.capture()
            );
        assertThat(recipientCaptor.getValue())
            .isEqualTo("john.doe@example.com");
        assertThat(subjectCaptor.getValue())
            .isEqualTo("userservice account activation");
        assertThat(contentCaptor.getValue()).isNotNull();
    }

    @Test
    public void testSendPasswordResetMail() throws Exception {
        User user = new User();
        user.setLangKey(Constants.DEFAULT_LANGUAGE);
        user.setEmail("john.doe@example.com");
        mailService.sendPasswordResetMail(user);
        verify(mailgunClient, Mockito.times(1))
            .sendMail(
                recipientCaptor.capture(),
                subjectCaptor.capture(),
                contentCaptor.capture()
            );
        assertThat(recipientCaptor.getValue())
            .isEqualTo("john.doe@example.com");
        assertThat(subjectCaptor.getValue())
            .isEqualTo("ORCID Member Portal password reset");
        assertThat(contentCaptor.getValue()).isNotNull();
    }

    @Test
    public void testOrganizationOwnerChangedMail() throws Exception {
        User user = new User();
        user.setLangKey(Constants.DEFAULT_LANGUAGE);
        user.setEmail("john.doe@example.com");
        mailService.sendOrganizationOwnerChangedMail(user, "Member 1");
        verify(mailgunClient, Mockito.times(1))
            .sendMail(
                recipientCaptor.capture(),
                subjectCaptor.capture(),
                contentCaptor.capture()
            );
        assertThat(recipientCaptor.getValue())
            .isEqualTo("john.doe@example.com");
        assertThat(subjectCaptor.getValue())
            .isEqualTo("ORCID Member Portal organization owner updated");
        assertThat(contentCaptor.getValue()).isNotNull();
    }
}
