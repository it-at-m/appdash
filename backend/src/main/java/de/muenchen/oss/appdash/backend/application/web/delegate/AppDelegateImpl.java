package de.muenchen.oss.appdash.backend.application.web.delegate;

import de.muenchen.oss.appdash.backend.application.db.model.App;
import de.muenchen.oss.appdash.backend.application.service.AppService;
import de.muenchen.oss.appdash.backend.application.web.mapper.AppMapper;
import de.muenchen.oss.appdash.backend.openapi.api.AppsApiDelegate;
import de.muenchen.oss.appdash.backend.openapi.model.AppRequestDTO;
import de.muenchen.oss.appdash.backend.openapi.model.AppResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
