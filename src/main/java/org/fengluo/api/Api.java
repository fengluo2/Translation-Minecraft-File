package org.fengluo.api;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @Author Maple
 * @DateTime 2022/8/12-13:24
 * @Description
 */
public interface Api {
    /**
     * 翻译并返回翻译结果方法
     *
     * @param query 翻译内容
     * @param from  源语言
     * @param to    翻译成什么语言
     * @return json字符串
     */
    String getTransResult(String query, String from, String to) throws JsonProcessingException, InterruptedException;
}
