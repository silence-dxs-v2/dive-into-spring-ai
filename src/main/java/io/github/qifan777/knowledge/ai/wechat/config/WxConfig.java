package io.github.qifan777.knowledge.ai.wechat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
@ConfigurationProperties(prefix = "wx")
public class WxConfig {
    private String url;
    private String wxUrl;
    private List<Map<String, String>> chatroom;

    // Getters and Setters

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWxUrl() {
        return wxUrl;
    }

    public void setWxUrl(String wxUrl) {
        this.wxUrl = wxUrl;
    }

    public List<Map<String, String>> getChatroom() {
        return chatroom;
    }

    public void setChatroom(List<Map<String, String>> chatroom) {
        this.chatroom = chatroom;
    }
}
