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
     * ip定位
     *
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static String baiduGetCityCode(String ip) throws JSONException, IOException {

        //这里调用百度的ip定位api服务
        JSONObject json = MapCommon.readJsonFromUrl("http://api.map.baidu.com/location/ip?ip=" + ip + "&ak=" + MapCommon.BAI_DU_MAP_AK + "&coor=bd09ll");
        if (Integer.parseInt((String) json.get("status")) == 0) {
            return json.toString();
        }
        return null;
    }
}
