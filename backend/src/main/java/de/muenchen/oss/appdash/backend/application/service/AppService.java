package de.muenchen.oss.appdash.backend.application.service;

import de.muenchen.oss.appdash.backend.application.db.model.App;
import de.muenchen.oss.appdash.backend.application.db.repository.AppRepository;
import de.muenchen.oss.appdash.backend.application.exception.EntityNotFoundException;
import de.muenchen.oss.appdash.backend.security.Authorities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.history.Revisions;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@PreAuthorize(Authorities.USER)
public class AppService {
  private final AppRepository appRepository;

  @Transactional(readOnly = true)
  public Page<App> getAllApps(final int page, final int size) {
    log.debug("Fetching Page {} of Apps (size: {})", page, size);
    final Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
    return appRepository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public App getApp(final Long appId) {
    log.debug("Retrieving App with ID: {}", appId);
    return getEntityOrThrowException(appId);
  }

  @Transactional(readOnly = true)
  @PreAuthorize(Authorities.ADMIN)
  public Revisions<Integer, App> getAppHistory(final Long appId) {
    log.debug("Retrieving history for App with ID: {}", appId);
    getEntityOrThrowException(appId);
    return appRepository.findRevisions(appId);
  }

  @Transactional
  @PreAuthorize(Authorities.ADMIN)
  public App createApp(final App entity) {
    log.debug("Creating App: {}", entity);
    return appRepository.save(entity);
  }

  @Transactional
  @PreAuthorize(Authorities.ADMIN)
  public App updateApp(final App entity, final Long appId) {
    log.debug("Updating App with ID: {}", appId);
    getEntityOrThrowException(appId);
    return appRepository.save(entity);
  }

  @Transactional
  @PreAuthorize(Authorities.ADMIN)
  public void deleteApp(final Long appId) {
    log.debug("Deleting App with ID: {}", appId);
    final App app = getEntityOrThrowException(appId);
    appRepository.delete(app);
  }

  private App getEntityOrThrowException(final Long appId) {
    return appRepository
        .findById(appId)
        .orElseThrow(() -> new EntityNotFoundException("App with ID " + appId + " was not found"));
  }
}
