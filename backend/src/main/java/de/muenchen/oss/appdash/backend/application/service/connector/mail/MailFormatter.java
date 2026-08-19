package de.muenchen.oss.appdash.backend.application.service.connector.mail;

import de.muenchen.oss.appdash.backend.application.db.model.App;
import de.muenchen.oss.appdash.backend.application.service.util.DateTimeUtil;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component("mailFormatter")
public class MailFormatter {
  public String getScoreCssClass(final Integer score) {
    if (score == null) return "border-blue";
    if (score > 60) return "border-green-3";
    if (score > 55) return "border-green-2";
    if (score > 50) return "border-green-1";
    if (score > 45) return "border-red-1";
    if (score > 40) return "border-red-2";
    if (score > 0) return "border-red-3";
    return "border-blue";
  }

  public String getTrendIcon(final Integer trend) {
    if (trend == null) return "🆕";
    if (trend == 1) return "🟢 ⬆";
    if (trend == 2) return "🔴 ⬇";
    return "⚪ ➡";
  }

  public String getOsClass(final String osName) {
    return (osName != null && osName.toLowerCase(Locale.ROOT).contains("android"))
        ? "os-android"
        : "os-ios";
  }

  public String formatDate(final Object dateObj) {
    return DateTimeUtil.formatDate(dateObj);
  }

  public String getBundleOrAppstoreId(final App app) {
    if (app == null) return "-";
    return app.getBundleId() != null && !app.getBundleId().isBlank()
        ? app.getBundleId()
        : app.getAppstoreId() != null ? app.getAppstoreId() : "-";
  }
}
