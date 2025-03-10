package io.github.qifan777.knowledge.ai.wechat.dto;

import lombok.Data;

@Data
public class Weather {
    private String city;
    private String shidu;
    private String pm25;
    private String pm10;
    private String quality;
    private String wendu;
    private String notice;
    private String type;

}
