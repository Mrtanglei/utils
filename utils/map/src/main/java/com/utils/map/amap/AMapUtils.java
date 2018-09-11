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
     * ip定位
     *
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static String guideGetCityCode(String ip) throws JSONException, IOException {
        JSONObject json = MapCommon.readJsonFromUrl("http://restapi.amap.com/v3/ip?ip=" + ip + "&key=" + MapCommon.AMAP_KEY + "");
        if (Integer.parseInt((String) json.get("status")) == 1) {
            return json.toString();
        }
        return null;
    }
}
