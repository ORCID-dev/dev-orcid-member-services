package org.orcid.sync;

import javax.xml.bind.JAXBException;

import org.orcid.service.AssertionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class OrcidSyncManager {
    
    private final Logger log = LoggerFactory.getLogger(OrcidSyncManager.class);

    @Autowired
    private AssertionService assertionsService;
    
    @Scheduled(fixedDelayString = "${application.cron.postAffiliations}")
    public void createAffiliations() throws JAXBException {
        log.info("Running cron to create assertions to ORCID");
        assertionsService.postAssertionsToOrcid();
    }
    
    @Scheduled(fixedDelayString = "${application.cron.putAffiliations}")
    public void updateAffiliations() throws JAXBException {
        log.info("Running cron to update assertions to ORCID");
        assertionsService.putAssertionsToOrcid();
    }
}
