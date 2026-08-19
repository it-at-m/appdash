package de.muenchen.oss.appdash.backend.application.service.connector.kubernetes;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LeaderChangeEvent extends ApplicationEvent {
  private static final long serialVersionUID = 1L;

  private final boolean leader;

  public LeaderChangeEvent(final Object source, final boolean leader) {
    super(source);
    this.leader = leader;
  }
}
