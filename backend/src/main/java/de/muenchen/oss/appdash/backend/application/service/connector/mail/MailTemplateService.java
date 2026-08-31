package de.muenchen.oss.appdash.backend.application.service.connector.mail;

import de.muenchen.oss.appdash.backend.application.db.model.App;
import de.muenchen.oss.appdash.backend.application.db.model.AppProcess;
import de.muenchen.oss.appdash.backend.application.db.model.Scan;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class MailTemplateService {
  private final SpringTemplateEngine springTemplateEngine;

  public String generateBodyForReportEmail(
      final List<AppReport> productiveApps,
      final List<AppReport> reviewingApps,
      final List<AppReport> newApps) {
    final Context context = new Context();
    context.setVariable("productiveApps", productiveApps);
    context.setVariable("reviewingApps", reviewingApps);
    context.setVariable("newApps", newApps);
    return springTemplateEngine.process("mail/report", context);
  }

  public String generateBodyForControlEmail(final List<App> apps) {
    final Context context = new Context();
    context.setVariable("apps", apps);
    return springTemplateEngine.process("mail/control", context);
  }

  public String generateBodyForTrendDownEmail(
      final App app, final AppProcess appProcess, final Scan scan) {
    final Context context = new Context();
    context.setVariable("app", app);
    context.setVariable("appProcess", appProcess);
    context.setVariable("scan", scan);
    return springTemplateEngine.process("mail/trend-down", context);
  }

  public String generateBodyForDoneScanEmail(
      final App app, final AppProcess appProcess, final Scan scan) {
    final Context context = new Context();
    context.setVariable("app", app);
    context.setVariable("appProcess", appProcess);
    context.setVariable("scan", scan);
    return springTemplateEngine.process("mail/scan-done", context);
  }

  public String generateBodyForErrorEmail(final String error) {
    final Context context = new Context();
    context.setVariable("errorMessage", error);
    return springTemplateEngine.process("mail/error", context);
  }

  public String generateBodyForRemovalEmail(final App app) {
    final Context context = new Context();
    context.setVariable("app", app);
    return springTemplateEngine.process("mail/removal", context);
  }
}
