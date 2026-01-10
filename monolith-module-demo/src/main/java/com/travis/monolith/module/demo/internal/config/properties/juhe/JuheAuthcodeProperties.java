package com.travis.monolith.module.demo.internal.config.properties.juhe;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 聚合数据短信配置
 *
 * @author travis
 */

@Component
@Data
@ConfigurationProperties(prefix = "application.external.juhe.authcode")
public class JuheAuthcodeProperties {
    /**
     * appKey
     */
    private String appKey;

    /**
     * tplId
     */
    private String tplId;

}
