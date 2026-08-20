package de.muenchen.oss.appdash.backend.application.service.connector.mail;

import de.muenchen.oss.appdash.backend.application.db.model.Process;
import de.muenchen.oss.appdash.backend.application.db.model.Scan;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProcessReport {
  private Process process;
  private List<Scan> scans;
}
