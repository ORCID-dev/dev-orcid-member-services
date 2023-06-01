package org.orcid.memberportal.service.user.client;

import feign.RequestInterceptor;
import java.io.IOException;
import org.springframework.context.annotation.Bean;

public class OAuth2UserClientFeignConfiguration {

  @Bean(name = "userFeignClientInterceptor")
  public RequestInterceptor getUserFeignClientInterceptor() throws IOException {
    return new UserFeignClientInterceptor();
  }
}
