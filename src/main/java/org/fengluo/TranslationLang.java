package org.fengluo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fengluo.tranApi.api.ApiTran;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TranslationLang {
    private static volatile TranslationLang instance = null;
    private final Logger logger = LogManager.getLogger(getClass().getName());
    private final ObjectMapper mapper = Main.mapper;
    private ApiTran api = null;
    private int sum = 0;
    private int count = 0;

    public static TranslationLang getInstance(ApiTran api) {
        if (instance == null) {
            // 双重检查
            synchronized (TranslationLang.class) {
                if (instance == null) {
                    instance = new TranslationLang();
                    instance.api = api;
                }
            }
        }
        return instance;
    }

    private void sleepTime() throws InterruptedException {
        Thread.sleep(Main.SLEEP_TIME);
    }

    public void translation(String langFile) throws IOException, InterruptedException {
        List<String> strings = new ArrayList<>();
        strings.add(Utils.LANG_STATEMENT);
        File file = new File(langFile);
        List<String> stringItem = FileUtils.readLines(file, StandardCharsets.UTF_8);
        String[] temp;
        for (String string : stringItem) {
            if (!string.startsWith("#") && !string.isBlank()) {
                sum++;
            }
        }
        for (int i = 0; i < stringItem.size(); i++) {
            if (!stringItem.get(i).startsWith("#") && !stringItem.get(i).isBlank()) {
                stringItem.set(i, Utils.replacementInfo(new StringBuilder(stringItem.get(i)), "§"));
                stringItem.set(i, Utils.replacementInfo(new StringBuilder(stringItem.get(i)), "%"));
                temp = stringItem.get(i).split("=");
                if (temp.length > 1) {
                    temp[1] = mapper.readTree(api.getTransResult(temp[1], "auto", "zh")).path("trans_result").path(0).path("dst").textValue();
                    stringItem.set(i, temp[0] + "=" + temp[1]);
                    sleepTime();
                    count++;
                    logger.info("progress:" + count + "/" + sum);
                }
            }
        }
        strings.addAll(stringItem);

        if (file.getParent() == null) {
            file = new File("zh_cn.lang");
        } else {
            file = new File(file.getParent() + "\\zh_cn.lang");
        }
        FileUtils.writeLines(file, String.valueOf(StandardCharsets.UTF_8), strings);
    }
}
