package org.orcid.memberportal.service.assertion.domain.normalization.org.impl;

import org.orcid.memberportal.service.assertion.config.Constants;
import org.orcid.memberportal.service.assertion.domain.normalization.org.OrgNormalizer;
import org.springframework.stereotype.Component;

@Component
public class RinggoldNormalizer implements OrgNormalizer {

    @Override
    public String normalizeOrgId(String orgId) {
        return orgId;
    }

    @Override
    public String getOrgSource() {
        return Constants.RINGGOLD_ORG_SOURCE;
    }
}
