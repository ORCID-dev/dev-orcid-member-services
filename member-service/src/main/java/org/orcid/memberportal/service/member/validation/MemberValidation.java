package org.orcid.memberportal.service.member.validation;

import java.util.List;

public class MemberValidation {

    private boolean valid;

    private List<String> errors;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

}
