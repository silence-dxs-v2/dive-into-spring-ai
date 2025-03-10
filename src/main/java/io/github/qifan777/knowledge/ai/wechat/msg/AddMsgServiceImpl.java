package io.github.qifan777.knowledge.ai.wechat.msg;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import io.github.qifan777.knowledge.ai.wechat.LoginApi;
import io.github.qifan777.knowledge.ai.wechat.dto.MsgDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class AddMsgServiceImpl implements MsgConvertService<String>{
    @Value("${wx.wxUrl}")
    private String wxUrl;

    @Override
    public MsgDto<String> convert(JSONObject jsonObject) {
        JSONObject data = jsonObject.getJSONObject("data");
        Integer MsgType = (Integer) data.get("MsgType");
        if(MsgType.intValue()==1){
            MsgDto<String> stringMsgDto = new MsgDto<>();
            String pushContent = data.getString("PushContent");
            if(StrUtil.isNotBlank(pushContent)){
                boolean flag = pushContent.contains("在群聊中@了你")||pushContent.contains("@小林同学");
                if(!flag){
                    log.info("不是在群聊中@ yourself1");
                    return null;
                }
                stringMsgDto.setNickname(pushContent.split("在群聊中@了你")[0]);
                String tempContent = data.getJSONObject("Content").get("string").toString();
                String[] split = tempContent.split("@小林同学");
                if(split.length==1){
                    log.info("不是在群聊中@ yourself2");
                    return null;
                }
                stringMsgDto.setContent(split[1]);
// -> wxid_07ljtmsdsj0x22:
//                @小林 你好
                stringMsgDto.setFromWxid(jsonObject.get("Wxid").toString());
                stringMsgDto.setAppId(jsonObject.get("appid").toString());

                stringMsgDto.setFromUserName(data.getJSONObject("FromUserName").get("string").toString());
                stringMsgDto.setToUserName(data.getJSONObject("ToUserName").get("string").toString());
                return stringMsgDto;

            }

//        stringMsgDto.setContent(data.getJSONObject("Content").get("string").toString());




        }
       return null;
    }

    @Override
    public boolean sendMsg(String content, MsgDto<T> msgDto) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("appId",msgDto.getAppId());
        params.put("toWxid",msgDto.getFromUserName());
        String replace = msgDto.getNickname().replace("在群聊中", "");
        params.put("content","@"+replace+" "+content);
//        if(msgDto.getFromUserName().contains("chatroom")){
//            params.put("ats",);
//        }

        // 发送文本消息
        String token = LoginApi.getToken();
        HttpRequest.post(wxUrl+"/message/postText").header("X-GEWE-TOKEN",token).body(JSONUtil.parse(params).toString()).execute().body();
        return true;
    }
}
