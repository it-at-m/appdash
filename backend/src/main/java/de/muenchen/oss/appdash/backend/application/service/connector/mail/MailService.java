package de.muenchen.oss.appdash.backend.application.service.connector.mail;

import de.muenchen.oss.appdash.backend.application.db.model.App;
import de.muenchen.oss.appdash.backend.application.db.model.AppProcess;
import de.muenchen.oss.appdash.backend.application.db.model.Scan;
import de.muenchen.oss.appdash.backend.application.db.model.TypeValue;
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
  private static final Set<String> PRODUCTIVE_LANES =
      Set.of(StateEnum.IN_REVIEW.getValue(), StateEnum.ERLEDIGT.getValue());

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
            categorized.getOrDefault(StateEnum.ERLEDIGT.getValue(), List.of()),
            categorized.getOrDefault(StateEnum.IN_REVIEW.getValue(), List.of()),
            categorized.getOrDefault(StateEnum.NEU.getValue(), List.of()));
    final String subject = "Weekly Application Scan Report";

    for (final String recipient : recipients) {
      mailSendService.sendEmailAsync(new Mail(recipient, subject, body));
    }
  }

  public void sendTrendDownEmail(
      final App app, final AppProcess appProcess, final Scan scan, final List<String> recipients) {
    if (recipients == null || recipients.isEmpty()) return;

    final String body = templateService.generateBodyForTrendDownEmail(app, appProcess, scan);
    final String subject = "ALERT: Downward Trend Detected for " + app.getName();

    for (final String recipient : recipients) {
      mailSendService.sendEmailAsync(new Mail(recipient, subject, body));
    }
  }

  public void sendDoneScanEmail(
      final App app, final AppProcess appProcess, final Scan scan, final List<String> recipients) {
    if (recipients == null || recipients.isEmpty()) return;

    final String body = templateService.generateBodyForDoneScanEmail(app, appProcess, scan);
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

  public String categorizeReport(final AppReport report) {
    if (report == null || report.getApp() == null) {
      return StateEnum.ERLEDIGT.getValue();
    }

    final List<AppProcessReport> reports = report.getAppProcessReports();
    if (reports == null) {
      return StateEnum.NEU.getValue();
    }

    for (final AppProcessReport reportItem : reports) {
      if (isProductive(reportItem)) {
        return StateEnum.ERLEDIGT.getValue();
      }
    }

    for (final AppProcessReport reportItem : reports) {
      if (isReviewing(reportItem)) {
        return StateEnum.IN_REVIEW.getValue();
      }
    }

    return StateEnum.NEU.getValue();
  }

  private boolean isProductive(final AppProcessReport reportItem) {
    if (reportItem == null) {
      return false;
    }

    final AppProcess appProcess = reportItem.getAppProcess();
    if (appProcess == null) {
      return false;
    }

    final TypeValue lane = appProcess.getLane();
    if (lane == null || lane.getName() == null) {
      return false;
    }

    final String laneName = lane.getName().toLowerCase(Locale.ROOT);
    return PRODUCTIVE_LANES.contains(laneName);
  }

  private boolean isReviewing(final AppProcessReport reportItem) {
    if (reportItem == null) {
      return false;
    }

    final AppProcess appProcess = reportItem.getAppProcess();
    return appProcess != null && appProcess.getTrend() != null;
  }
}
