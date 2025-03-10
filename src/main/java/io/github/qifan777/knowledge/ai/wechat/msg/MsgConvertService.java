package io.github.qifan777.knowledge.ai.wechat.msg;

import com.alibaba.fastjson2.JSONObject;
import io.github.qifan777.knowledge.ai.wechat.dto.MsgDto;

public interface MsgConvertService<T> {
    MsgDto<T> convert(JSONObject jsonObject);

    boolean sendMsg(String content, MsgDto<org.apache.poi.ss.formula.functions.T> msgDto);
}
