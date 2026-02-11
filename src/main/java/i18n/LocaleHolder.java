package i18n;

import java.util.Locale;

public class LocaleHolder {
    private Locale locale = Locale.ENGLISH;

    public Locale getLocale() { return locale; }
    public void setLocale(Locale locale) { this.locale = locale; }
}