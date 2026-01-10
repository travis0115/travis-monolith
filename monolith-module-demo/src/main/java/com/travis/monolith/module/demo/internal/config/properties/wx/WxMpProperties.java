package com.travis.monolith.module.demo.internal.config.properties.wx;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author travis
 */

@Component
@Data
@ConfigurationProperties(prefix = "application.external.wx.mp")
public class WxMpProperties {
    /**
     * 设置微信公众号或者小程序等的appid
     */
    private String appId;

    /**
     * 设置微信公众号或者小程序等的appSecret
     */
    private String appSecret;

}
