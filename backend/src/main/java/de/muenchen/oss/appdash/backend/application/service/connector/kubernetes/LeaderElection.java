package de.muenchen.oss.appdash.backend.application.service.connector.kubernetes;

public interface LeaderElection {
  boolean isLeader();
}
