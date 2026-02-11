package config;

import i18n.LocaleHolder;
import i18n.Messages;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class I18nConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("classpath:messages");

        return messageSource;
    }

    @Bean
    public Messages messages(MessageSource messageSource, LocaleHolder localeHolder){
        return new Messages(messageSource, localeHolder);
    }

    @Bean
    public LocaleHolder localeHolder() {
        return new LocaleHolder();
    }
}