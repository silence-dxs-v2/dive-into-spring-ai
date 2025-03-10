package io.github.qifan777.knowledge.ai.wechat;

import com.alibaba.fastjson2.JSONObject;

/**
 * 收藏夹模块
 */
public class FavorApi {

    /**
     * 同步收藏夹
     */
    public static JSONObject sync(String appId, String syncKey) {
        JSONObject param = new JSONObject();
        param.put("appId", appId);
        param.put("syncKey", syncKey);
        return OkhttpUtil.postJSON("/favor/sync", param);
    }

    /**
     * 获取收藏夹内容
     */
    public static JSONObject getContent(String appId, Integer favId) {
        JSONObject param = new JSONObject();
        param.put("appId", appId);
        param.put("favId", favId);
        return OkhttpUtil.postJSON("/favor/getContent", param);
    }

    /**
     * 删除收藏夹
     */
    public static JSONObject delete(String appId, Integer favId) {
        JSONObject param = new JSONObject();
        param.put("appId", appId);
        param.put("favId", favId);
        return OkhttpUtil.postJSON("/favor/delete", param);
    }

}
