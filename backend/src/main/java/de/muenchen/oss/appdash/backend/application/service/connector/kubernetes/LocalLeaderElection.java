package de.muenchen.oss.appdash.backend.application.service.connector.kubernetes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnMissingBean(KubernetesLeaderElection.class)
public class LocalLeaderElection implements LeaderElection {
  private final ApplicationEventPublisher applicationEventPublisher;

  public LocalLeaderElection(final ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void onApplicationReady() {
    log.info("Running without Kubernetes leader election. Assuming leadership by default.");
    applicationEventPublisher.publishEvent(new LeaderChangeEvent(this, true));
  }

  @Override
  public boolean isLeader() {
    return true;
  }
}
