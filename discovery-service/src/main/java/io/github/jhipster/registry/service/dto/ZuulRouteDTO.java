package io.github.jhipster.registry.service.dto;

import java.util.Set;
import javax.validation.constraints.NotNull;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

/**
 * Extends a ZuulRoute to add the instance status ("UP", "DOWN", etc...) .
 */
public class ZuulRouteDTO extends ZuulProperties.ZuulRoute {

  private String status;

  public ZuulRouteDTO(
    String id,
    String path,
    String serviceId,
    String url,
    boolean stripPrefix,
    Boolean retryable,
    @NotNull Set<String> sensitiveHeaders,
    String status
  ) {
    super(id, path, serviceId, url, stripPrefix, retryable, sensitiveHeaders);
    this.status = status;
  }

  public ZuulRouteDTO(String path, String location, String status) {
    super(path, location);
    this.status = status;
  }

  public ZuulRouteDTO(String status) {
    super();
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
