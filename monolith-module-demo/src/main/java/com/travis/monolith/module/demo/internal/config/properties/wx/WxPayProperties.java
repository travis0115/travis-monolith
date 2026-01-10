package com.travis.monolith.module.demo.internal.config.properties.wx;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author travis
 */

@Component
@Data
@ConfigurationProperties(prefix = "application.external.wx.pay")
public class WxPayProperties {
    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 微信支付商户密钥
     */
    private String mchKey;

    /**
     * apiclient_cert.p12文件路径
     */
    private String keyPath;

}
