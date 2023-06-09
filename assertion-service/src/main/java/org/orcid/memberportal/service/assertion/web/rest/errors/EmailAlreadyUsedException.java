package org.orcid.memberportal.service.assertion.web.rest.errors;

import java.util.HashMap;
import java.util.Map;

public class EmailAlreadyUsedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private static final String PARAM = "params";

    private String message = "";

    private final Map<String, String> paramMap = new HashMap<>();

    public EmailAlreadyUsedException(String message) {
        super(message);
    }

    public EmailAlreadyUsedException(String message, String... params) {
        super(message);

        this.message = message;
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                paramMap.put(PARAM + i, params[i]);
            }
        }
    }

    public EmailAlreadyUsedException(String message, Map<String, String> paramMap) {
        super(message);
        this.message = message;
        this.paramMap.putAll(paramMap);
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }
}
