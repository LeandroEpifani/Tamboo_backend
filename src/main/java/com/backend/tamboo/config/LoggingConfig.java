package com.backend.tamboo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class LoggingConfig {

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        // Log della query string (es: ?param=123)
        loggingFilter.setIncludeQueryString(true);
        // Log del corpo della richiesta (payload JSON ecc.)
        loggingFilter.setIncludePayload(true);
        // Limite massimo di caratteri da loggare per evitare output smisurati
        loggingFilter.setMaxPayloadLength(10000);
        // Se vuoi loggare anche gli header, metti true
        loggingFilter.setIncludeHeaders(false);
        // Prefisso personalizzato per i log
        loggingFilter.setAfterMessagePrefix("REQUEST DATA: ");
        return loggingFilter;
    }
}
