package de.muenchen.oss.appdash.backend.application.service.connector.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "appcenter.s3.togen")
public record TogenS3Properties(String bucket, @DefaultValue("") String uploadPath) {}
