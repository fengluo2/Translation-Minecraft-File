package org.fengluo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fengluo.api.Api;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.StringJoiner;

public class TranslationJSON {
    private static volatile TranslationJSON instance = null;
    private final Logger logger = LogManager.getLogger(getClass().getName());
    private ObjectMapper mapper = new ObjectMapper();

    private Api api = null;
    private StringJoiner stringJoiner = null;
    private int sum = 0;
    private int count = 0;

    private TranslationJSON() {
    }


    public static TranslationJSON getInstance(Api api) {
        if (instance == null) {
            // 双重检查
            synchronized (TranslationJSON.class) {
                if (instance == null) {
                    instance = new TranslationJSON();
                    instance.api = api;
                }
            }
        }
        return instance;
    }

    public void translation(String jsonFile) throws InterruptedException, IOException, NoSuchAlgorithmException {
        File file = new File(jsonFile);
        String file1 = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        file1 = Utils.replacementInfo(new StringBuilder(file1), "§");
        ObjectMapper mapper = Main.mapper;
        JsonNode jsonNode = mapper.readTree(file1);
        JsonNode lines = jsonNode.path("questLines:9");
        JsonNode db = jsonNode.path("questDatabase:9");
        sum = lines.size() + db.size();
        translation(lines);
        translation(db);
        StringBuilder stringBuilder = new StringBuilder();
        String[] strs = file.getPath().split("\\.");
        for (int i = 0; i < strs.length; i++) {
            if (i == strs.length - 1) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                stringBuilder.append("-zh_cn.").append(strs[i]);
            } else {
                stringBuilder.append(strs[i]).append(".");
            }
        }
        file = new File(stringBuilder.toString());
        FileUtils.writeStringToFile(file, Utils.JSON_STATEMENT + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode), StandardCharsets.UTF_8);
    }

    private void translation(JsonNode jsonNode) throws JsonProcessingException, InterruptedException {
        Iterator<Map.Entry<String, JsonNode>> iteratorLines = jsonNode.fields();
        JsonNode jsonNode1, jsonNode2 = null;
        ObjectNode objectNode = null;
        String name, desc = null;
        while (iteratorLines.hasNext()) {
            Map.Entry<String, JsonNode> mapTemp = iteratorLines.next();
            jsonNode1 = mapTemp.getValue().path("properties:10").path("betterquesting:10");
            objectNode = (ObjectNode) mapTemp.getValue().path("properties:10").path("betterquesting:10");
            name = jsonNode1.get("name:8").textValue();
            desc = jsonNode1.get("desc:8").textValue();
            //name = mapper.readTree(api.getTransResult(name, "auto", "zh")).path("trans_result").path(0).path("dst").textValue();
            name = api.getTransResult(name, "", "");
            sleepTime();
            if (!desc.isBlank()) {
                /*jsonNode2 = mapper.readTree(api.getTransResult(desc, "auto", "zh")).path("trans_result");
                stringJoiner = new StringJoiner("\n\n");
                for (JsonNode j : jsonNode2) {
                    stringJoiner.add(j.get("dst").textValue());
                }
                desc = stringJoiner.toString();*/
                desc = api.getTransResult(desc, "", "");
                sleepTime();
            }
            objectNode.put("name:8", name);
            objectNode.put("desc:8", desc);
            count++;
            logger.info("progress:" + count + "/" + sum);
        }
    }

    private void sleepTime() throws InterruptedException {
        Thread.sleep(500L + (long) (Math.random() * (1000L - 500L + 1L)));
        //Thread.sleep(Main.SLEEP_TIME);
    }
}
