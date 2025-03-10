package io.github.qifan777.knowledge.ai.wechat;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.github.qifan777.knowledge.ai.wechat.config.WxConfig;
import io.github.qifan777.knowledge.ai.wechat.controller.LoginController;
import io.github.qifan777.knowledge.ai.wechat.dto.HoroscopeData;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WeatherApi {
    @Value("${wx.wxUrl}")
    private String wxUrl;

    @Autowired
    private WxConfig wxConfig;

    public static String getWeather(String city) {


        // 广州|深圳|东莞
        String[] strings = {"123"};
        StringBuilder stringBuilder = new StringBuilder();
        String url = "http://t.weather.itboy.net/api/weather/city";
        String body = HttpRequest.get(url + "/"+city).execute().body();
        String  cityName = (String) JSONUtil.parseObj(body).getJSONObject("cityInfo").get("city");
        String shidu = (String) JSONUtil.parseObj(body).getJSONObject("data").get("shidu");
        JSONArray jsonArray = JSONUtil.parseObj(body).getJSONObject("data").getJSONArray("forecast");

        if(!jsonArray.isEmpty()){
            JSONObject forecast = JSONUtil.parseObj(jsonArray.get(0));
            String low = String.valueOf(forecast.get("low"));
            String week = String.valueOf(forecast.get("week"));
            String type = String.valueOf(forecast.get("type"));
            String notice = String.valueOf(forecast.get("notice"));
            String ymd = String.valueOf(forecast.get("ymd"));
            String high = String.valueOf(forecast.get("high"));


            stringBuilder.append("\t\t"+cityName+"\n今天是："+ymd+","+week+"\n"+"天气情况："+type+"\n"+"最高温度："+high+"\n"+"最低温度："+low+"\n"+"湿度："+shidu+"\n"+""+notice+"\n");
            stringBuilder.append("-------------------------------------\n");



        }





        return stringBuilder.toString();
    }


//    @Scheduled(cron = "0 13 23 * * ?")
    @PostConstruct
    public void getWeather() {
        // 黄历api
        StringBuilder stringBuilder = new StringBuilder();
        HoroscopeData huangli = HuangliApi.getHuangli();



        List<Map<String, String>> hashMapListIterator =  wxConfig.getChatroom();
        for (Map<String, String> stringStringMap : hashMapListIterator) {



                // 广州
                stringBuilder.append(getWeather("101280101"));
                // 深圳
                stringBuilder.append(getWeather("101280601"));
                //东莞
                stringBuilder.append(getWeather("101281601"));
                stringBuilder.append("\t\t黄历\n");
                stringBuilder.append(huangli.toString());
                HashMap<String, Object> params = new HashMap<>();
                params.put("appId", LoginController.appId);
                params.put("toWxid",stringStringMap.get("id"));
                params.put("content",stringBuilder.toString());
                HttpRequest.post(wxUrl+"/message/postText").header("X-GEWE-TOKEN",LoginApi.getToken()).body(JSONUtil.parse(params).toString()).execute();
                log.info("已经向："+stringStringMap.get("name")+"发送今天天气");




        }






    }

    public static void main(String[] args) {
        System.out.println(getWeather("101280101"));
    }


}
