package com.travis.monolith.module.demo.internal.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author travis
 * @date 2020-04-19 01:27
 */
@Component
@Data
@ConfigurationProperties(prefix = "application.file.upload")
public class FileUploadProperties {
    /**
     * 匹配请求路径
     */
    private String resourceHandler;

    /**
     * 本地路径
     */
    private String resourceLocation;
}
