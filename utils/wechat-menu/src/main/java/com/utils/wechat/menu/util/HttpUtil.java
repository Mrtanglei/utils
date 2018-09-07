package com.utils.wechat.menu.util;

import com.utils.wechat.menu.domain.WeChatButton;
import com.utils.wechat.menu.domain.WeChatResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tanglei
 * @date 18/9/7 下午4:23
 */
public class HttpUtil {

    private static volatile PoolingHttpClientConnectionManager connectionManager;

    /**
     * 将请求结果封装成JsonObject
     *
     * @param httpMethd
     * @return
     * @throws IOException
     */
    public static JSONObject getJSonResponse(HttpUriRequest httpMethd) throws IOException {
        JSONObject jsonObject;
        CloseableHttpClient httpClient = getClient();
        try (CloseableHttpResponse httpResponse = httpClient.execute(httpMethd, HttpClientContext.create())) {
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                httpMethd.abort();
            }
            String response = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            jsonObject = new JSONObject(response);
        }
        return jsonObject;
    }

    /**
     * 微信接口返回的信息
     *
     * @param httpMethd
     * @return
     * @throws IOException
     */
    public static WeChatResponse getWeChatResponse(HttpUriRequest httpMethd) throws IOException {
        JSONObject jsonObject = getJSonResponse(httpMethd);
        WeChatResponse weChatResponse = new WeChatResponse();
        weChatResponse.setErrcode(jsonObject.getInt("errcode"));
        weChatResponse.setErrmsg(jsonObject.getString("errmsg"));
        return weChatResponse;
    }

    /**
     * 获取自定义菜单栏信息
     *
     * @param httpMethd
     * @return
     * @throws IOException
     */
    public static List<WeChatButton> getWeChatMenuResponse(HttpUriRequest httpMethd) throws IOException {
        JSONObject jsonObject = getJSonResponse(httpMethd);
        if (jsonObject != null) {
            JSONObject menuJsonObject = jsonObject.getJSONObject("menu");
            if (menuJsonObject != null && menuJsonObject.getJSONArray("button") != null && menuJsonObject.getJSONArray("button").length() > 0) {
                JSONArray jsonArray = menuJsonObject.getJSONArray("button");
                List<WeChatButton> buttons = new ArrayList<>();
                setWeChatButton(jsonArray, buttons);
                return buttons;
            }
        }
        return null;
    }

    private static void setWeChatButton(JSONArray jsonArray, List<WeChatButton> buttons) {
        if (jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject buttonJson = (JSONObject) jsonArray.get(i);
                WeChatButton button = getWeChatButtonFromJSON(buttonJson);
                if (buttonJson.has("sub_button")) {
                    List<WeChatButton> subButtons = new ArrayList<>();
                    setWeChatButton(buttonJson.getJSONArray("sub_button"), subButtons);
                    button.setSub_button(subButtons);
                }
                buttons.add(button);
            }
        }
    }

    private static WeChatButton getWeChatButtonFromJSON(JSONObject buttonJson) {
        WeChatButton button = new WeChatButton();
        if(buttonJson.has("key"))
            button.setKey(buttonJson.getString("key"));
        if(buttonJson.has("media_id"))
            button.setMedia_id(buttonJson.getString("media_id"));
        if(buttonJson.has("name"))
            button.setName(buttonJson.getString("name"));
        if(buttonJson.has("type"))
            button.setType(buttonJson.getString("type"));
        if(buttonJson.has("url"))
            button.setUrl(buttonJson.getString("url"));
        return button;
    }

    private static CloseableHttpClient getClient() {
        return HttpClients.custom().setConnectionManager(getPoolingConnectionManager()).build();
    }

    private static PoolingHttpClientConnectionManager getPoolingConnectionManager() {
        if (connectionManager == null) {
            synchronized (HttpUtil.class) {
                if (connectionManager == null) {
                    connectionManager = new PoolingHttpClientConnectionManager();
                    connectionManager.setMaxTotal(21);
                    connectionManager.setDefaultMaxPerRoute(3);
                }

            }
        }
        return connectionManager;
    }
}
