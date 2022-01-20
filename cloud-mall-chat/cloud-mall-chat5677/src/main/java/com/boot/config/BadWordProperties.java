package com.boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * 配置违规词
 */
@Configuration
@ConfigurationProperties(prefix = "bad",ignoreUnknownFields = false)
@EnableConfigurationProperties(BadWordProperties.class)
@PropertySource(value = "classpath:badWord.properties",encoding = "GBK") //一定要指定编码
@Data
public class BadWordProperties {

    private List<String> badwords; //违规词列表



}
