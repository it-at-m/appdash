package de.muenchen.oss.appdash.backend.application.web.mapper;

import de.muenchen.oss.appdash.backend.application.db.model.App;
import de.muenchen.oss.appdash.backend.common.DateTimeMapper;
import de.muenchen.oss.appdash.backend.common.ReferenceMapper;
import de.muenchen.oss.appdash.backend.openapi.model.AppRequestDTO;
import de.muenchen.oss.appdash.backend.openapi.model.AppResponseDTO;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
    componentModel = "spring",
    uses = {ReferenceMapper.class, DateTimeMapper.class})
public interface AppMapper {
  // Entity -> DTO mapping
  @Mapping(target = "clientId", source = "client.id")
  @Mapping(target = "softwareTypeId", source = "softwareType.id")
  @Mapping(target = "softwareNatureId", source = "softwareNature.id")
  @Mapping(target = "priorityId", source = "priority.id")
  @Mapping(target = "categoryId", source = "category.id")
  @Mapping(target = "referatId", source = "referat.id")
  @Mapping(target = "mbucId", source = "mbuc.id")
  @Mapping(target = "osId", source = "os.id")
  @Mapping(target = "statusId", source = "status.id")
  @Mapping(target = "laneId", source = "lane.id")
  @Mapping(target = "sourceId", source = "source.id")
  @Mapping(target = "visibilityId", source = "visibility.id")
  @Mapping(target = "eKey", source = "EKey")
  AppResponseDTO toDto(App entity);

  // DTO -> Entity mapping
  @Mapping(target = "client", source = "clientId")
  @Mapping(target = "softwareType", source = "softwareTypeId")
  @Mapping(target = "softwareNature", source = "softwareNatureId")
  @Mapping(target = "priority", source = "priorityId")
  @Mapping(target = "category", source = "categoryId")
  @Mapping(target = "referat", source = "referatId")
  @Mapping(target = "mbuc", source = "mbucId")
  @Mapping(target = "os", source = "osId")
  @Mapping(target = "status", source = "statusId")
  @Mapping(target = "lane", source = "laneId")
  @Mapping(target = "source", source = "sourceId")
  @Mapping(target = "visibility", source = "visibilityId")
  @Mapping(target = "EKey", source = "eKey")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "timestampCreated", ignore = true)
  @Mapping(target = "timestampUpdated", ignore = true)
  App toEntity(AppRequestDTO dto);

  @InheritConfiguration(name = "toEntity")
  void updateEntityFromDto(AppRequestDTO dto, @MappingTarget App entity);
}
