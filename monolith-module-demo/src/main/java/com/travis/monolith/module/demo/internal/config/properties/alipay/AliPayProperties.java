package com.travis.monolith.module.demo.internal.config.properties.alipay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author travis
 */

@Component
@Data
@ConfigurationProperties(prefix = "application.external.alipay")
public class AliPayProperties {
    /**
     * 应用Id
     */
    private String appId;

    /**
     * 应用私钥
     */
    private String appPrivateKey;


    /**
     * 支付宝公钥
     */
    private String alipayPublicKey;

}
