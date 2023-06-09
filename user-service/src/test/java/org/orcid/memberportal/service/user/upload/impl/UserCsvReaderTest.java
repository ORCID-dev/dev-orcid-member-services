package org.orcid.memberportal.service.user.upload.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.orcid.memberportal.service.user.domain.User;
import org.orcid.memberportal.service.user.dto.UserDTO;
import org.orcid.memberportal.service.user.upload.UserUpload;
import org.orcid.memberportal.service.user.upload.impl.UserCsvReader;
import org.orcid.memberportal.service.user.validation.UserValidation;
import org.orcid.memberportal.service.user.validation.UserValidator;
import org.springframework.context.MessageSource;

class UserCsvReaderTest {

    @Mock
    private UserValidator userValidator;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private UserCsvReader reader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testReadUsersUpload() throws IOException {
        Mockito.when(userValidator.validate(Mockito.any(UserDTO.class), Mockito.any(User.class))).thenReturn(getUserValidation(new ArrayList<>()));

        InputStream inputStream = getClass().getResourceAsStream("/users.csv");
        UserUpload upload = reader.readUsersUpload(inputStream, getUser("en"));
        assertEquals(3, upload.getUserDTOs().size());

        UserDTO userDTO1 = upload.getUserDTOs().get(0);
        UserDTO userDTO2 = upload.getUserDTOs().get(1);
        UserDTO userDTO3 = upload.getUserDTOs().get(2);

        assertEquals("1@user.com", userDTO1.getEmail());
        assertEquals("2@user.com", userDTO2.getEmail());
        assertEquals("3@user.com", userDTO3.getEmail());

        assertEquals("Angel", userDTO1.getFirstName());
        assertEquals("Leonardo", userDTO2.getFirstName());
        assertEquals("Daniel", userDTO3.getFirstName());

        assertEquals("Montenegro", userDTO1.getLastName());
        assertEquals("Mendoza", userDTO2.getLastName());
        assertEquals("Palafox", userDTO3.getLastName());

        assertEquals("sssalesforceid1", userDTO1.getSalesforceId());
        assertEquals("salesforceid3", userDTO2.getSalesforceId());
        assertEquals("salesforceid2", userDTO3.getSalesforceId());
    }

    @Test
    void testReadUsersUploadInvalidEmails() throws IOException, JSONException {
        Mockito.when(userValidator.validate(Mockito.any(UserDTO.class), Mockito.any(User.class))).thenReturn(getUserValidation(Arrays.asList("some-error")));

        InputStream inputStream = getClass().getResourceAsStream("/users.csv");
        UserUpload upload = reader.readUsersUpload(inputStream, getUser("en"));
        assertEquals(0, upload.getUserDTOs().size());
        assertEquals(3, upload.getErrors().length());

        assertTrue(upload.getErrors().get(0).toString().contains("some-error"));
        assertTrue(upload.getErrors().get(1).toString().contains("some-error"));
        assertTrue(upload.getErrors().get(2).toString().contains("some-error"));
    }

    private UserValidation getUserValidation(List<String> errors) {
        UserValidation validation = new UserValidation();
        validation.setValid(errors.isEmpty());
        validation.setErrors(errors);
        return validation;
    }

    private User getUser(String langKey) {
        User user = new User();
        user.setId("some-id");
        user.setLangKey(langKey);
        user.setEmail("something@orcid.org");
        user.setLoginAs("something@orcid.org");
        user.setSalesforceId("something");
        user.setMemberName("some member name");
        return user;
    }

}
