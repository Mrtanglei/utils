package com.utils.map.amap;

import com.oracle.javafx.jmx.json.JSONException;
import com.utils.map.common.MapCommon;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 高德地图
 *
 * @author tanglei
 * @date 18/9/11
 */
public class AMapUtils {

    /**
     * 高德获取城市信息
     *
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static String guideGetCityCode(String ip) throws JSONException, IOException {
        JSONObject json = MapCommon.readJsonFromUrl("http://restapi.amap.com/v3/ip?ip=" + ip + "&key=" + MapCommon.AMAP_KEY + "");
        //String province = (String) json.get("province");
        // String city = (String) json.get("city");
        String adcode = (String) json.get("adcode");
        return adcode;
    }
}
