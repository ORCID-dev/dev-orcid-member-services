package org.orcid.domain.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.orcid.domain.Assertion;
import org.orcid.domain.OrcidRecord;
import org.orcid.domain.enumeration.AffiliationStatus;

class AffiliationUtilsTest {

	@Test
	void testGetAffiliationStatusWhereErrorOccured() {
		OrcidRecord record = new OrcidRecord();
		Assertion assertion = new Assertion();

		JSONObject error = getDummyError(404);
		assertion.setOrcidError(error.toString());
		assertEquals(AffiliationStatus.USER_DELETED_FROM_ORCID.value,
				AffiliationUtils.getAffiliationStatus(assertion, record));

		error = getDummyError(403);
		assertion.setOrcidError(error.toString());
		assertEquals(AffiliationStatus.USER_REVOKED_ACCESS.value,
				AffiliationUtils.getAffiliationStatus(assertion, record));

		error = getDummyError(500);
		assertion.setOrcidError(error.toString());
		assertEquals(AffiliationStatus.ERROR_ADDIN_TO_ORCID.value,
				AffiliationUtils.getAffiliationStatus(assertion, record));
	}

	@Test
	void testGetAffiliationStatusWhereNoErrorOccured() {
		OrcidRecord record = new OrcidRecord();
		Assertion assertion = new Assertion();

		record.setDeniedDate(Instant.now());
		assertEquals(AffiliationStatus.USER_DENIED_ACCESS.value,
				AffiliationUtils.getAffiliationStatus(assertion, record));
		record.setDeniedDate(null);

		record.setIdToken("idToken");
		assertEquals(AffiliationStatus.USER_GRANTED_ACCESS.value,
				AffiliationUtils.getAffiliationStatus(assertion, record));
		record.setIdToken(null);

		assertion.setPutCode("put-code");
		assertion.setDeletedFromORCID(Instant.now());
		assertEquals(AffiliationStatus.DELETED_IN_ORCID.value,
				AffiliationUtils.getAffiliationStatus(assertion, record));
		assertion.setDeletedFromORCID(null);

		assertEquals(AffiliationStatus.IN_ORCID.value, AffiliationUtils.getAffiliationStatus(assertion, record));
	}

	private JSONObject getDummyError(int statusCode) {
		JSONObject error = new JSONObject();
		error.put("statusCode", statusCode);
		error.put("error", "dummy");
		return error;
	}

}