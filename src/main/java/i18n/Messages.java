package i18n;

import org.springframework.context.MessageSource;

import java.util.OptionalInt;
import java.util.OptionalLong;

public class Messages {
    private final MessageSource messageSource;
    private final LocaleHolder localeHolder;

    public Messages(MessageSource messageSource, LocaleHolder localeHolder) {
        this.messageSource = messageSource;
        this.localeHolder = localeHolder;
    }

    public String get(String key) {
        return messageSource.getMessage(key, null, localeHolder.getLocale());
    }

    public OptionalInt parseIntOrPrint(String text) {
        try {
            return OptionalInt.of(Integer.parseInt(text));
        } catch (NumberFormatException e) {
            System.out.println(get("common.invalid.format"));
            return OptionalInt.empty();
        }
    }

    public OptionalLong parseLongOrPrint(String text) {
        try {
            return OptionalLong.of(Long.parseLong(text));
        } catch (NumberFormatException e) {
            System.out.println(get("common.invalid.format"));
            return OptionalLong.empty();
        }
    }
}