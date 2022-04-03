
package dev.spider.es.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Slf4j
public class JsonUtil {
    private static ObjectMapper m = new ObjectMapper();

    static {
        m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        m.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        m.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        m.registerModule(new JavaTimeModule());
    }

    public static <T> String toJsonString(T obj) {
        String json = null;
        if (Objects.nonNull(obj)) {
            try {
                json = m.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                log.warn(e.getMessage(), e);
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        return json;
    }

    public static <T> T parseObj(String json, Class<T> clazz) {
        return parse(json, clazz, null);
    }

    public static <T> T parseObj(String json, TypeReference<T> type) {
        return parse(json, null, type);
    }

    private static <T> T parse(String json, Class<T> clazz, TypeReference<T> type) {
        T obj = null;
        if (!StringUtils.isBlank(json)) {
            try {
                if (Objects.nonNull(clazz)) {
                    obj = m.readValue(json, clazz);
                } else {
                    obj = m.readValue(json, type);
                }
            } catch (IOException e) {
                log.warn(e.getMessage(), e);
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        return obj;
    }

}
