package io.github.qifan777.knowledge.ai.wechat;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.github.qifan777.knowledge.ai.wechat.dto.HoroscopeData;


public class HuangliApi {

    public static HoroscopeData getHuangli() {
        String body = HttpRequest.get("https://api.suyanw.cn/api/huangli.php").execute().body();
        JSONObject data =  JSONUtil.parseObj(body).getJSONObject("data");
        HoroscopeData horoscopeData = new HoroscopeData();
        horoscopeData.setDate((String) data.get("时间"));
        horoscopeData.setSolarTerm((String) data.get("节气"));
        horoscopeData.setTimePeriod((String) data.get("时辰"));
        horoscopeData.setLunarDate((String) data.get("农历"));
        horoscopeData.setHeavenlyStemsAndEarthlyBranches((String) data.get("天干地支"));
        HoroscopeData.Constellation constellation = new HoroscopeData.Constellation();
        constellation.setDailyQuote((String) data.getJSONObject("星座运势").get("今日一言"));
        constellation.setOverallFortune((Integer)data.getJSONObject("星座运势").get ("综合运势"));
        constellation.setLuckyColor((String) data.getJSONObject("星座运势").get("幸运颜色"));
        constellation.setCompatibleSign( (String)data.getJSONObject("星座运势").get("速配星座"));
        constellation.setLuckyNumber( (Integer) data.getJSONObject("星座运势").get("幸运数字"));
        horoscopeData.setConstellation(constellation);
        return horoscopeData;
    }
}
