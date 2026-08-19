package de.muenchen.oss.appdash.backend.application.service.connector.mail;

import de.muenchen.oss.appdash.backend.application.db.model.File;
import de.muenchen.oss.appdash.backend.application.db.model.Scan;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileReport {
  private File file;
  private List<Scan> scans;
}
