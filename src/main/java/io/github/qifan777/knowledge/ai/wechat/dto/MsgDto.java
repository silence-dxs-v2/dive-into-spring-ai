package io.github.qifan777.knowledge.ai.wechat.dto;

import lombok.Data;

@Data
public class MsgDto<T> {
    // 消息发送人的wxid
    private String FromUserName;
    // 所属微信的wxid
    private String fromWxid;
    // 消息接收人的wxid
    private String ToUserName;
    // 消息内容
    private T Content;
    // 消息发送人的昵称
    private String  nickname;
    // appId
    private String appId;
    // 消息类型 1为文本 47为表情包
    private String MsgType;
}
