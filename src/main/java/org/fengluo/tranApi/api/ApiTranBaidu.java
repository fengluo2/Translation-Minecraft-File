package org.fengluo.tranApi.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fengluo.Main;
import org.fengluo.tranApi.HttpGet;
import org.fengluo.tranApi.MD5;

import java.util.HashMap;
import java.util.Map;

public class ApiTranBaidu implements ApiTran {
    private final Logger logger = LogManager.getLogger(getClass().getName());
    private String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    private String appid;
    private String securityKey;

    public ApiTranBaidu(String appId, String securityKey) {
        this.appid = appId;
        this.securityKey = securityKey;
    }


    public String getTransResult(String query, String from, String to) throws JsonProcessingException, InterruptedException {
        Map<String, String> params = buildParams(query, from, to);
        String result = HttpGet.get(TRANS_API_HOST, params);

        ObjectMapper mapper = Main.mapper;
        JsonNode jsonNode = mapper.readTree(result).path("error_code");

        while (!jsonNode.isMissingNode()) {
            Thread.sleep(Main.SLEEP_TIME);
            jsonNode = mapper.readTree(HttpGet.get(TRANS_API_HOST, params)).path("error_code");
            logger.info("Fail:" + result + ",Retrying.");
        }
        return result;
    }


    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey;
        params.put("sign", MD5.md5(src));

        return params;
    }

}
