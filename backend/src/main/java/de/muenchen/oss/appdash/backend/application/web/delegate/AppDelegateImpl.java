package de.muenchen.oss.appdash.backend.application.web.delegate;

import de.muenchen.oss.appdash.backend.application.db.model.App;
import de.muenchen.oss.appdash.backend.application.db.model.RevisionUser;
import de.muenchen.oss.appdash.backend.application.service.AppService;
import de.muenchen.oss.appdash.backend.application.web.mapper.AppMapper;
import de.muenchen.oss.appdash.backend.openapi.api.AppsApiDelegate;
import de.muenchen.oss.appdash.backend.openapi.model.AppRequestDTO;
import de.muenchen.oss.appdash.backend.openapi.model.AppResponseDTO;
import de.muenchen.oss.appdash.backend.openapi.model.AppRevisionDTO;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppDelegateImpl implements AppsApiDelegate {
  private final AppService appService;
  private final AppMapper appMapper;

  @Override
  public ResponseEntity<List<AppResponseDTO>> getAllApps(final Integer page, final Integer size) {
    final int pageIndex = page != null ? page : 0;
    final int pageSize = size != null ? size : 20;

    final Page<App> appPage = appService.getAllApps(pageIndex, pageSize);
    final List<AppResponseDTO> dtos = appPage.getContent().stream().map(appMapper::toDto).toList();

    return ResponseEntity.ok(dtos);
  }

  @Override
  public ResponseEntity<AppResponseDTO> getAppById(final Long id) {
    final App app = appService.getApp(id);
    return ResponseEntity.ok(appMapper.toDto(app));
  }

  @Override
  public ResponseEntity<List<AppRevisionDTO>> getAppHistory(final Long id) {
    final Revisions<Integer, App> revisions = appService.getAppHistory(id);

    final List<AppRevisionDTO> dtos =
        revisions.getContent().stream().map(this::mapToRevisionDto).toList();

    return ResponseEntity.ok(dtos);
  }

  private AppRevisionDTO mapToRevisionDto(final Revision<Integer, App> revision) {
    final RevisionUser customRev = revision.getMetadata().getDelegate();

    final AppRevisionDTO dto = new AppRevisionDTO();
    dto.setRevisionId(revision.getRequiredRevisionNumber());
    dto.setRevisionTimestamp(
        OffsetDateTime.ofInstant(
            revision.getMetadata().getRequiredRevisionInstant(), ZoneOffset.UTC));
    dto.setRevisionType(
        AppRevisionDTO.RevisionTypeEnum.fromValue(revision.getMetadata().getRevisionType().name()));
    dto.setUsername(customRev.getUsername());
    dto.setEntity(appMapper.toDto(revision.getEntity()));

    return dto;
  }

  @Override
  public ResponseEntity<AppResponseDTO> createApp(final AppRequestDTO requestDto) {
    final App app = appMapper.toEntity(requestDto);
    final App savedApp = appService.createApp(app);
    return ResponseEntity.status(HttpStatus.CREATED).body(appMapper.toDto(savedApp));
  }

  @Override
  public ResponseEntity<AppResponseDTO> updateApp(final Long id, final AppRequestDTO requestDto) {
    final App app = appService.getApp(id);

    appMapper.updateEntityFromDto(requestDto, app);

    final App updatedApp = appService.updateApp(app, id);
    return ResponseEntity.ok(appMapper.toDto(updatedApp));
  }

  @Override
  public ResponseEntity<Void> deleteApp(final Long id) {
    appService.deleteApp(id);
    return ResponseEntity.noContent().build();
  }
}
