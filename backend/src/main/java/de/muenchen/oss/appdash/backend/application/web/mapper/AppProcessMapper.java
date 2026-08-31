package de.muenchen.oss.appdash.backend.application.web.mapper;

import de.muenchen.oss.appdash.backend.application.db.model.AppProcess;
import de.muenchen.oss.appdash.backend.common.DateTimeMapper;
import de.muenchen.oss.appdash.backend.common.ReferenceMapper;
import de.muenchen.oss.appdash.backend.openapi.model.AppProcessResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {ReferenceMapper.class, DateTimeMapper.class})
public interface AppProcessMapper {
  @Mapping(target = "osId", source = "os.id")
  @Mapping(target = "trendId", source = "trend.id")
  @Mapping(target = "numberOfUsersId", source = "numberOfUsers.id")
  @Mapping(target = "statusId", source = "status.id")
  @Mapping(target = "laneId", source = "lane.id")
  @Mapping(target = "vivId", source = "viv.id")
  AppProcessResponseDTO toDto(AppProcess entity);
}
