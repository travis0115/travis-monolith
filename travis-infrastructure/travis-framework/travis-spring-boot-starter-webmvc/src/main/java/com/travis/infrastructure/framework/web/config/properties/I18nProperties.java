package com.travis.infrastructure.framework.web.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "travis.application.web.i18n")
@Data
public class I18nProperties {
    private boolean enabled = true;
}