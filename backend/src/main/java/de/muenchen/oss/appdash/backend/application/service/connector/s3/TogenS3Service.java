package de.muenchen.oss.appdash.backend.application.service.connector.s3;

import de.muenchen.oss.refarch.integration.s3.application.port.out.S3OutPort;
import org.springframework.stereotype.Service;

@Service
public class TogenS3Service extends AbstractS3Service {
  private final TogenS3Properties properties;

  public TogenS3Service(
      final S3OutPort s3OutPort,
      final TogenS3Properties properties,
      final S3JsonFilter s3JsonFilter) {
    super(s3OutPort, s3JsonFilter);
    this.properties = properties;
  }

  @Override
  protected String getBucket() {
    return properties.bucket();
  }

  @Override
  protected String getUploadPath() {
    return properties.uploadPath();
  }
}
