package me.javierca.interviews.invex.config.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * An empty authentication object just granting access to the authentication filter.
 */
public final class DummyAuthentication implements Authentication {

  private static final GrantedAuthority AUTHORITY = () -> "admin";

  private boolean authenticated = true;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(AUTHORITY);
  }

  @Override
  public Object getCredentials() {
    return "dummy";
  }

  @Override
  public Object getDetails() {
    return "dummy";
  }

  @Override
  public Object getPrincipal() {
    return "dummy";
  }

  @Override
  public boolean isAuthenticated() {
    return this.authenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.authenticated = isAuthenticated;
  }

  @Override
  public String getName() {
    return "dummy";
  }
}
