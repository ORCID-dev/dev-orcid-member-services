package org.orcid.memberportal.service.assertion.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import javax.ws.rs.core.MediaType;
import org.orcid.memberportal.service.assertion.domain.AssertionServiceMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@AuthorizedFeignClient(name = "memberservice")
public interface MemberServiceClient {
      @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/members/{id}",
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON
      )
      @HystrixProperty(
            name = "hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds",
            value = "20000"
      )
      ResponseEntity<AssertionServiceMember> getMember(
            @PathVariable("id") String id
      );

      @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/members/{salesforceId}/language/{language}",
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON
      )
      @HystrixProperty(
            name = "hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds",
            value = "50000"
      )
      ResponseEntity<Void> updateMemberDefaultLanguage(
            @PathVariable("salesforceId") String salesforceId,
            @PathVariable("language") String language
      );
}
