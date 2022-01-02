package com.boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//表情配置文件
@ConfigurationProperties(prefix = "cloud.emoji")
public class EmojiProperties {

    private Map<String,String> emojiMap=new ConcurrentHashMap<>();

    public Map<String, String> getEmojiMap() {
        return emojiMap;
    }

    public void setEmojiMap(Map<String, String> emojiMap) {
        this.emojiMap = emojiMap;
    }

    @Override
    public String toString() {
        return "EmojiProperties{" +
                "emojiMap=" + emojiMap +
                '}';
    }
}
