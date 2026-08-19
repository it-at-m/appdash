package de.muenchen.oss.appdash.backend.application.service.connector.kubernetes;

import java.util.concurrent.atomic.AtomicBoolean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.kubernetes.commons.leader.LeaderInitiator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.integration.leader.event.OnGrantedEvent;
import org.springframework.integration.leader.event.OnRevokedEvent;
import org.springframework.stereotype.Component;

/*
 * KubernetesLeaderElection v3.0
 */
@Slf4j
@Component
@ConditionalOnBean(LeaderInitiator.class)
public class KubernetesLeaderElection implements LeaderElection {
  private final AtomicBoolean leader = new AtomicBoolean(false);
  private final ApplicationEventPublisher applicationEventPublisher;

  public KubernetesLeaderElection(final ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @EventListener(OnGrantedEvent.class)
  public void onGranted(final OnGrantedEvent event) {
    leader.set(true);
    log.info("Leadership GRANTED to this pod (Role: {})", event.getRole());
    applicationEventPublisher.publishEvent(new LeaderChangeEvent(this, true));
  }

  @EventListener(OnRevokedEvent.class)
  public void onRevoked(final OnRevokedEvent event) {
    leader.set(false);
    log.warn("Leadership REVOKED from this pod (Role: {})", event.getRole());
    applicationEventPublisher.publishEvent(new LeaderChangeEvent(this, false));
  }

  @Override
  public boolean isLeader() {
    return leader.get();
  }
}
