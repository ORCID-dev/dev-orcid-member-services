package org.orcid.memberportal.service.user.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

  public static final String ADMIN = "ROLE_ADMIN";

  public static final String USER = "ROLE_USER";

  public static final String ORG_OWNER = "ROLE_ORG_OWNER";

  public static final String ANONYMOUS = "ROLE_ANONYMOUS";

  public static final String ASSERTION_SERVICE_ENABLED = "ASSERTION_SERVICE_ENABLED";

  public static final String CONSORTIUM_LEAD = "ROLE_CONSORTIUM_LEAD";

  private AuthoritiesConstants() {}
}
