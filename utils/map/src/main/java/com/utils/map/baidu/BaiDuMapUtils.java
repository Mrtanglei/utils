package com.utils.map.baidu;

import com.oracle.javafx.jmx.json.JSONException;
import com.utils.map.common.MapCommon;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 百度地图
 *
 * @author tanglei
 * @date 18/9/11
 */
public class BaiDuMapUtils {

    /**
     * 百度获取城市信息
     *
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static String baiduGetCityCode(String ip) throws JSONException, IOException {

        //这里调用百度的ip定位api服务 详见 http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm
        JSONObject json = MapCommon.readJsonFromUrl("http://api.map.baidu.com/location/ip?ip=" + ip + "&ak=" + MapCommon.BAI_DU_MAP_AK + "&coor=bd09ll");
        JSONObject detail = (JSONObject) ((JSONObject) json.get("content")).get("address_detail");
        return (String) detail.get("province") + (String) detail.get("city") + (String) detail.get("district");
    }
}
