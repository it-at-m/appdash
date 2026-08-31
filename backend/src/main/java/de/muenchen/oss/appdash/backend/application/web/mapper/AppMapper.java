package de.muenchen.oss.appdash.backend.application.web.mapper;

import de.muenchen.oss.appdash.backend.application.db.model.App;
import de.muenchen.oss.appdash.backend.application.db.model.InfoMapping;
import de.muenchen.oss.appdash.backend.common.DateTimeMapper;
import de.muenchen.oss.appdash.backend.common.ReferenceMapper;
import de.muenchen.oss.appdash.backend.openapi.model.AppRequestDTO;
import de.muenchen.oss.appdash.backend.openapi.model.AppResponseDTO;
import de.muenchen.oss.appdash.backend.openapi.model.InfoMappingDTO;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
    componentModel = "spring",
    uses = {ReferenceMapper.class, DateTimeMapper.class, AppProcessMapper.class})
public interface AppMapper {
  @Mapping(target = "priorityId", source = "priority.id")
  @Mapping(target = "categoryId", source = "category.id")
  @Mapping(target = "mbucId", source = "mbuc.id")
  @Mapping(target = "visibilityId", source = "visibility.id")
  @Mapping(target = "eKey", source = "EKey")
  AppResponseDTO toDto(App entity);

  @Mapping(target = "priority", source = "priorityId")
  @Mapping(target = "category", source = "categoryId")
  @Mapping(target = "mbuc", source = "mbucId")
  @Mapping(target = "visibility", source = "visibilityId")
  @Mapping(target = "EKey", source = "eKey")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "timestampCreated", ignore = true)
  @Mapping(target = "timestampUpdated", ignore = true)
  @Mapping(target = "cosus", ignore = true)
  @Mapping(target = "appGroups", ignore = true)
  @Mapping(target = "appProcesses", ignore = true)
  App toEntity(AppRequestDTO dto);

  @InheritConfiguration(name = "toEntity")
  void updateEntityFromDto(AppRequestDTO dto, @MappingTarget App entity);

  InfoMapping toRecord(InfoMappingDTO dto);

  InfoMappingDTO toDto(InfoMapping infoMapping);
}
