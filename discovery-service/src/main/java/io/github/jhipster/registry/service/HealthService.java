package io.github.jhipster.registry.service;

import io.github.jhipster.registry.client.HealthClient;
import io.github.jhipster.registry.service.dto.SimpleHealthDTO;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

      @Autowired
      private HealthClient healthClient;

      public SimpleHealthDTO checkHealth(String healthCheckUrl)
            throws IOException {
            return healthClient.getHealth(healthCheckUrl);
      }
}
