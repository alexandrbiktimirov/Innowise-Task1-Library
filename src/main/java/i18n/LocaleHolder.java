package i18n;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocaleHolder {
    private Locale locale = Locale.ENGLISH;

    public Locale getLocale() { return locale; }
    public void setLocale(Locale locale) { this.locale = locale; }
}