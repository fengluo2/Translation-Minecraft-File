package org.fengluo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fengluo.tranApi.api.ApiTranBaidu;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final Logger logger = LogManager.getLogger("Main");
    public static Long SLEEP_TIME = 1000L;
    public static ObjectMapper mapper = new ObjectMapper();
    /**
     * 三个参数分别是用户id、用户密钥、翻译间隔
     */
    private static String APP_ID = "20210717000890399";
    private static String SECURITY_KEY = "PIFIIOGqOaI7NEvAQgo4";

    @SuppressWarnings({"AlibabaUndefineMagicConstant", "AlibabaCollectionInitShouldAssignCapacity"})
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException {
        Map<String, String> map = new HashMap<>();
        String[] strings = null;
        String value = "";
        for (String arg : args) {
            strings = arg.split(":");
            for (int i = 1; i < strings.length; i++) {
                value += (strings[i]);
                if (i != strings.length - 1) {
                    value += ":";
                }
            }
            map.put(strings[0], value);
        }
        if (map.get("id") != null) {
            APP_ID = (map.get("id"));
        }
        if (map.get("key") != null) {
            SECURITY_KEY = map.get("key");
        }
        if (map.get("speed") != null) {
            SLEEP_TIME = Long.valueOf(map.get("speed"));
        }
        if (map.get("file") != null) {
            logger.info("Start Translation");
            if (map.get("file").endsWith(".json")) {
                TranslationJSON.getInstance(new ApiTranBaidu(APP_ID, SECURITY_KEY)).translation(map.get("file"));
            } else if (map.get("file").endsWith(".lang")) {
                TranslationLang.getInstance(new ApiTranBaidu(APP_ID, SECURITY_KEY)).translation(map.get("file"));
            }
        } else {
            logger.error("Please enter a file parameter.Examples{file:C://en_us.json}");
            return;
        }
        logger.info("Translation Finish");
    }
}