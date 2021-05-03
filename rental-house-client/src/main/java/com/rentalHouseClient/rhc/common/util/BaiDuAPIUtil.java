package com.rentalHouseClient.rhc.common.util;

import cn.hutool.json.JSONObject;

/**
 * TODO
 *
 * @author czf
 * @date 2021/3/1 22:09
 */
public class BaiDuAPIUtil {

    public String baiDuApiProvince(String ip){
        //利用第三方接口获取访问者的ip地址，来展示推荐页面
        String data= null;

        data = ToInterface.interfaceUtil("https://api.map.baidu.com/location/ip?ak=ibmaD2623ClgSYPKDMEogaaBXfQYGxXg&ip="+ip+"&coor=bd09ll", "");
        JSONObject json = new JSONObject(data);
        JSONObject paths = json.getJSONObject("content");
        JSONObject jsonObj = paths.getJSONObject("address_detail");

        return jsonObj.getStr("province").replace("省","");
    }
    public String baiDuApiCity(String ip){
        //利用第三方接口获取访问者的ip地址，来展示推荐页面
        String data= null;

        data = ToInterface.interfaceUtil("https://api.map.baidu.com/location/ip?ak=ibmaD2623ClgSYPKDMEogaaBXfQYGxXg&ip="+ip+"&coor=bd09ll", "");
        JSONObject json = new JSONObject(data);
        JSONObject paths = json.getJSONObject("content");
        JSONObject jsonObj = paths.getJSONObject("address_detail");

        return jsonObj.getStr("city").replace("市","");
    }
}
