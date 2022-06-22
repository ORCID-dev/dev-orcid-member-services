package org.orcid.memberportal.service.member.client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.orcid.memberportal.service.member.client.model.MemberDetails;
import org.orcid.memberportal.service.member.config.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SalesforceClient {

    private static final Logger LOG = LoggerFactory.getLogger(SalesforceClient.class);

    private CloseableHttpClient httpClient;

    @Autowired
    private ApplicationProperties applicationProperties;
    
    public SalesforceClient() {
        this.httpClient = HttpClients.createDefault();
    }

    public MemberDetails getMemberDetails(String salesforceId) throws IOException {
        String endpoint = applicationProperties.getSalesforceClientEndpoint();
        if (StringUtils.isBlank(endpoint)) {
            LOG.warn("No salesforce client endpoint, not fetching member details");
            return null;
        }
        
        HttpGet httpGet = new HttpGet(endpoint + "member/" + salesforceId + "/details");
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + applicationProperties.getSalesforceClientToken());

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() != Status.OK.getStatusCode()) {
                LOG.warn("Received non-200 response trying to find member details for {}", salesforceId);
                String responseString = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
                LOG.warn("Response received:");
                LOG.warn(responseString);
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                JsonNode root = objectMapper.readTree(response.getEntity().getContent());
                MemberDetails memberDetails = objectMapper.treeToValue(root.at("/member"), MemberDetails.class);
                return memberDetails;
            }
        } finally {
            if (response != null) {
                response.close();
            } else {
                LOG.warn("Network error asking registry for orcid id");
            }
        }
        return null;
    }

}