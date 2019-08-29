package org.titan.argus.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.Collection;

/**
 * @Title: JsonUtil
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/28
 */
public class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String encode(Object o) {
        try {
            return MAPPER.writeValueAsString(o);
        } catch (IOException e) {
            return "{}";
        }
    }


    public static <T> T decode(String json, Class<T> clz) {

        try {
            return MAPPER.readValue(json, clz);
        } catch (IOException e) {
            throw new RuntimeException("json反序列化失败！", e);
        }
    }

    public static <T> Collection<T> decodeCollection(String j) {

        try {
            return MAPPER.readValue(j, new TypeReference<Collection<T>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("json 反序列化失败！", e);
        }
    }

}
