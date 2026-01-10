package com.travis.monolith.module.demo.internal.config.properties.aliyun;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 阿里云短信配置
 *
 * @author travis
 */

@Component
@Data
@ConfigurationProperties(prefix = "application.external.aliyun.idcard")
public class AliyunIdcardProperties {
    /**
     * appKey
     */
    private String appKey;

    /**
     * appSecret
     */
    private String appSecret;

    /**
     * appCode
     */
    private String appCode;


}
