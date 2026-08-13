package de.muenchen.oss.appdash.backend.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.jspecify.annotations.NonNull;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

@Component
public class ReferenceMapper {
  @PersistenceContext private EntityManager entityManager;

  public <T> T map(@NonNull final Long id, @TargetType final Class<T> type) {
    return entityManager.getReference(type, id);
  }
}
