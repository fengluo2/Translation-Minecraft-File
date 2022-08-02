package org.fengluo.tranApi.api;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author Maple
 */

public interface ApiTran {

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
