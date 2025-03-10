package io.github.qifan777.knowledge.ai.wechat.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import io.github.qifan777.knowledge.ai.wechat.ChatListenerService;
import io.github.qifan777.knowledge.ai.wechat.LoginApi;
import io.github.qifan777.knowledge.ai.wechat.OkhttpUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {
    public static   String appId = "wx_rhmLQxtYRF8wqVMtIc0I2";
    private String uuid = "";
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    @Resource
    private ChatListenerService chatListenerService;
    @Value("${wx.url}")
    private String wxUrl;

    @GetMapping("qr")
    public JSONObject getQrImg(){
        // 获取token
        String tokenStr = LoginApi.getToken();;
        OkhttpUtil.setToken(tokenStr);
        // 获取二维码返回给前端
        JSONObject qr = LoginApi.getQr(StrUtil.isBlank(appId)? null:appId);
        JSONObject data = qr.getJSONObject("data");
        String appIdStr = (String) data.get("appId");
         uuid = (String) data.get("uuid");
        if(StrUtil.isNotBlank(appIdStr)){
            appId = appIdStr;
            log.info("当前的获取验证码接口返回：{}",qr.toJSONString());
            return qr;
        }
        throw new RuntimeException("获取二维码失败");
    }


    @PostMapping("callBack")
    public void callBack(@RequestBody JSONObject msg){
       log.info("当前的回调信息为：{}",msg.toJSONString());
        chatListenerService.listen(msg);

    }

    @GetMapping("setCallBackUrl")
    public void setCallBackUrl(){
        // 启动一个定时线程5秒一次检查登录
//        scheduler.scheduleAtFixedRate(()->{
//
//        }, 0, 5, TimeUnit.SECONDS);
        JSONObject jsonObject = LoginApi.checkQr(appId, uuid, "");
        log.info("当前的登录信息为：{}",jsonObject.toJSONString());
        // 设置回调地址
        String url = wxUrl+"/login/callBack";
         LoginApi.setCallback(OkhttpUtil.token,url);
         log.info("当前的回调地址设置成功:{}",url);
    }
}
