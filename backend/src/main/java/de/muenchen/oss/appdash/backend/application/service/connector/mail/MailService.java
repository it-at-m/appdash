package de.muenchen.oss.appdash.backend.application.service.connector.mail;

import de.muenchen.oss.appdash.backend.application.db.model.App;
import de.muenchen.oss.appdash.backend.application.db.model.File;
import de.muenchen.oss.appdash.backend.application.db.model.Scan;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
 * MailService v3.0
 */
@Service
@RequiredArgsConstructor
public class MailService {
  private static final Set<String> PRODUCTIVE_LANES = Set.of("in review", "erledigt");

  private final MailTemplateService templateService;
  private final MailSendService mailSendService;

  public void sendControlEmail(final List<App> apps, final List<String> recipients) {
    if (recipients == null || recipients.isEmpty()) return;

    final String body = templateService.generateBodyForControlEmail(apps);
    final String subject = "Daily App Control Status";

    for (final String recipient : recipients) {
      mailSendService.sendEmailAsync(new Mail(recipient, subject, body));
    }
  }

  public void sendReportEmail(final List<AppReport> appReports, final List<String> recipients) {
    if (recipients == null || recipients.isEmpty()) return;

    final Map<String, List<AppReport>> categorized =
        appReports.stream().collect(Collectors.groupingBy(this::categorizeReport));

    final String body =
        templateService.generateBodyForReportEmail(
            categorized.getOrDefault("productive", List.of()),
            categorized.getOrDefault("reviewing", List.of()),
            categorized.getOrDefault("new", List.of()));
    final String subject = "Weekly Application Scan Report";

    for (final String recipient : recipients) {
      mailSendService.sendEmailAsync(new Mail(recipient, subject, body));
    }
  }

  public void sendTrendDownEmail(
      final App app, final File file, final Scan scan, final List<String> recipients) {
    if (recipients == null || recipients.isEmpty()) return;

    final String body = templateService.generateBodyForTrendDownEmail(app, file, scan);
    final String subject = "ALERT: Downward Trend Detected for " + app.getName();

    for (final String recipient : recipients) {
      mailSendService.sendEmailAsync(new Mail(recipient, subject, body));
    }
  }

  public void sendDoneScanEmail(
      final App app, final File file, final Scan scan, final List<String> recipients) {
    if (recipients == null || recipients.isEmpty()) return;

    final String body = templateService.generateBodyForDoneScanEmail(app, file, scan);
    final String subject = "Scan Completed: " + app.getName();

    for (final String recipient : recipients) {
      mailSendService.sendEmailAsync(new Mail(recipient, subject, body));
    }
  }

  public void sendErrorEmail(final String error, final List<String> recipients) {
    if (recipients == null || recipients.isEmpty()) return;

    final String body = templateService.generateBodyForErrorEmail(error);
    final String subject = "System Error Alert";

    for (final String recipient : recipients) {
      mailSendService.sendEmailAsync(new Mail(recipient, subject, body));
    }
  }

  public void sendRemovalEmail(final App app, final List<String> recipients) {
    if (recipients == null || recipients.isEmpty()) return;

    final String body = templateService.generateBodyForRemovalEmail(app);
    final String subject = "Application Removed: " + app.getName();

    for (final String recipient : recipients) {
      mailSendService.sendEmailAsync(new Mail(recipient, subject, body));
    }
  }

  private String categorizeReport(final AppReport report) {
    if (report.getApp() == null) {
      return "new";
    }
    final String lane =
        report.getApp().getLane() != null
            ? report.getApp().getLane().getName().toLowerCase(Locale.ROOT)
            : "";
    if (PRODUCTIVE_LANES.contains(lane)) {
      return "productive";
    } else if (report.getApp().getTrend() != null) {
      return "reviewing";
    } else {
      return "new";
    }
  }
}
