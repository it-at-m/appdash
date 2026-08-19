package de.muenchen.oss.appdash.backend.application.service.connector.s3;

import de.muenchen.oss.appdash.backend.application.exception.EntityNotFoundException;
import de.muenchen.oss.appdash.backend.application.exception.ExternalServiceException;
import de.muenchen.oss.refarch.integration.s3.application.port.out.S3OutPort;
import de.muenchen.oss.refarch.integration.s3.domain.exception.S3Exception;
import de.muenchen.oss.refarch.integration.s3.domain.model.FileMetadata;
import de.muenchen.oss.refarch.integration.s3.domain.model.FileReference;
import de.muenchen.oss.refarch.integration.s3.domain.model.ListResult;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/*
 * AbstractS3Service v3.0
 */
@Slf4j
public abstract class AbstractS3Service {
  private final S3OutPort s3OutPort;
  private final S3JsonFilter s3JsonFilter;

  protected AbstractS3Service(final S3OutPort s3OutPort, final S3JsonFilter s3JsonFilter) {
    this.s3OutPort = s3OutPort;
    this.s3JsonFilter = s3JsonFilter;
  }

  protected abstract String getBucket();

  protected abstract String getUploadPath();

  // Upload
  public void uploadFile(final String path, final MultipartFile file) {
    Objects.requireNonNull(file, "file must not be null");
    final String fullPath = buildFullPath(getUploadPath(), path);
    try (InputStream is = file.getInputStream()) {
      putObject(getBucket(), fullPath, is, file.getSize());
    } catch (IOException | S3Exception e) {
      log.error("Failed to upload file to S3: bucket={}, path={}", getBucket(), fullPath, e);
      throw new ExternalServiceException(
          getClass().getSimpleName(), "S3 upload failed: " + e.getMessage(), e);
    }
  }

  public void uploadJson(final String path, final String jsonString) {
    Objects.requireNonNull(jsonString, "jsonString must not be null");
    final String fullPath = buildFullPath(getUploadPath(), path);
    final byte[] bytes = jsonString.getBytes(StandardCharsets.UTF_8);
    try (InputStream is = new ByteArrayInputStream(bytes)) {
      putObject(getBucket(), fullPath, is, bytes.length);
    } catch (IOException | S3Exception e) {
      log.error("Failed to upload JSON to S3: bucket={}, path={}", getBucket(), fullPath, e);
      throw new ExternalServiceException(
          getClass().getSimpleName(), "S3 JSON upload failed: " + e.getMessage(), e);
    }
  }

  // Download
  public InputStream downloadStream(final String path) {
    final String fullPath = buildFullPath(getUploadPath(), path);
    try {
      final InputStream stream = s3OutPort.getFileContent(new FileReference(getBucket(), fullPath));
      return new BufferedInputStream(stream);
    } catch (S3Exception e) {
      log.error("Failed to download file from S3: bucket={}, path={}", getBucket(), fullPath, e);
      throw new EntityNotFoundException("File not found in S3 storage: " + path, e);
    }
  }

  public String downloadAsString(final String path) {
    try (InputStream stream = downloadStream(path)) {
      return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      log.error("Failed to read S3 stream as String for path: {}", path, e);
      throw new ExternalServiceException(
          getClass().getSimpleName(), "Failed to read S3 content: " + e.getMessage(), e);
    }
  }

  // Delete
  public void deleteFile(final String path) {
    final String fullPath = buildFullPath(getUploadPath(), path);
    try {
      s3OutPort.deleteFile(new FileReference(getBucket(), fullPath));
    } catch (S3Exception e) {
      log.error("Failed to delete file from S3: bucket={}, path={}", getBucket(), fullPath, e);
      throw new ExternalServiceException(
          getClass().getSimpleName(), "Storage delete error: " + e.getMessage(), e);
    }
  }

  // List
  public List<String> listFiles(final String prefix, final boolean stripPrefix) {
    final String searchPrefix = buildFullPath(getUploadPath(), prefix);
    final String prefixToStrip = stripPrefix ? getUploadPath() : null;
    final List<String> fileList = new ArrayList<>();
    try {
      final ListResult listResult = s3OutPort.getFilesWithPrefix(getBucket(), searchPrefix, true);
      if (listResult != null && listResult.files() != null) {
        for (final FileMetadata metadata : listResult.files()) {
          final String objectName = metadata.path();
          if (prefixToStrip != null && objectName.startsWith(prefixToStrip)) {
            fileList.add(objectName.substring(prefixToStrip.length()));
          } else {
            fileList.add(objectName);
          }
        }
      }
    } catch (S3Exception e) {
      log.error("Error listing files (bucket={}, prefix={})", getBucket(), searchPrefix, e);
      throw new ExternalServiceException(
          getClass().getSimpleName(), "Error listing files: " + e.getMessage(), e);
    }
    return fileList;
  }

  public String getFilteredJsonString(
      final String path, final Set<String> fieldsToKeep, final Set<String> fieldsToDrop) {
    try (InputStream inputStream = downloadStream(path)) {
      return s3JsonFilter.filterJson(inputStream, fieldsToKeep, fieldsToDrop);
    } catch (IOException e) {
      log.error(
          "Error processing and filtering S3 JSON (bucket={}, path={})", getBucket(), path, e);
      throw new ExternalServiceException(
          getClass().getSimpleName(), "JSON filtering failed: " + e.getMessage(), e);
    }
  }

  private void putObject(
      final String bucketName, final String path, final InputStream inputStream, final long size)
      throws S3Exception {
    final InputStream bufferedStream =
        inputStream instanceof BufferedInputStream || inputStream instanceof ByteArrayInputStream
            ? inputStream
            : new BufferedInputStream(inputStream);
    s3OutPort.saveFile(new FileReference(bucketName, path), bufferedStream, size);
  }

  private String buildFullPath(final String baseUploadPath, final String targetPath) {
    if (baseUploadPath == null || baseUploadPath.isBlank()) {
      return targetPath;
    }
    final String normalizedUploadPath =
        baseUploadPath.endsWith("/") ? baseUploadPath : baseUploadPath + "/";
    final String normalizedPath = targetPath.startsWith("/") ? targetPath.substring(1) : targetPath;
    return normalizedUploadPath + normalizedPath;
  }
}
