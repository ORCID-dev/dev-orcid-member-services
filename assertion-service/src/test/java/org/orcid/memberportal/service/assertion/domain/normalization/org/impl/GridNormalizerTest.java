package org.orcid.memberportal.service.assertion.domain.normalization.org.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.orcid.memberportal.service.assertion.config.Constants;

public class GridNormalizerTest {

    private GridNormalizer normalizer = new GridNormalizer();

    @Test
    public void testGetOrgSource() {
        assertThat(normalizer.getOrgSource())
            .isEqualTo(Constants.GRID_ORG_SOURCE);
    }

    @Test
    public void testNormalizeOrgId() {
        assertThat(normalizer.normalizeOrgId("test")).isEqualTo("test");
    }
}
