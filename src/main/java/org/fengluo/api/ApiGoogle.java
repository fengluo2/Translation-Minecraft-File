package org.fengluo.api;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fengluo.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @Author Maple
 * @DateTime 2022/8/12-13:26
 * @Description
 */
public class ApiGoogle implements Api {
    private static final Logger logger = LogManager.getLogger("ApiGoogle");


    public static void main(String[] args) {
        int i = 0;
        try {
            while (true) {
                i++;
                //System.out.println(i + ":" + translate("en", "zh-CN", "Hello"));
                System.out.println(i + ":" + translate("en", "zh-CN", "The main purpose for this device is to turn §6Activated Certus Quartz Crystal§r into §6Charged Certus Quartz Crystal§r.\n\nThis device can be powered with RF via energy conduits or use AE energy from a network via ME conduits or cables.\n\nIt accepts power from two sides. Which two sides? It\u0027s pretty easy to guess.\n\n\n\n\n\n\n\n\nOk, ok. It\u0027s the top and bottom.\n\n"));
                break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getTransResult(String query, String from, String to) {
        String temp = null;
        while (temp == null) {
            try {
                temp = translate("en", "zh-CN", query);
            } catch (Exception e) {
                temp = null;
                logger.error((e));
            }
        }
        return temp;
    }

    public static String translate(String langFrom, String langTo,
                                   String word) throws Exception {
        String url = "https://translate.googleapis.com/translate_a/single?" +
                "client=gtx&" +
                "sl=" + langFrom +
                "&tl=" + langTo +
                "&dt=t&q=" + URLEncoder.encode(word, StandardCharsets.UTF_8);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return parseResult(response.toString());
    }

    private static String parseResult(String inputJson) throws Exception {
        JsonNode objectNode1 = Main.mapper.readTree(inputJson);
        JsonNode objectNode2 = objectNode1.get(0);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < objectNode2.size(); i++) {
            result.append((objectNode2.get(i)).get(0).textValue());
        }
        return result.toString();
    }
}