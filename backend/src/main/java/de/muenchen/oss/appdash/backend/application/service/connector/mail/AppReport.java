package de.muenchen.oss.appdash.backend.application.service.connector.mail;

import de.muenchen.oss.appdash.backend.application.db.model.App;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppReport {
  private App app;
  private List<FileReport> fileReports;
}
