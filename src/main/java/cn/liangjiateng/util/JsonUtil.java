package cn.liangjiateng.util;

import cn.liangjiateng.common.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public final class JsonUtil {

    /**
     * string 转list
     *
     * @param jsonStr
     * @param cls
     * @return
     * @throws IOException
     */
    public static <T> List<T> string2List(String jsonStr, Class<?> cls) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, cls);
        List<T> list = objectMapper.readValue(jsonStr, javaType);
        return list;
    }

    public static <T> String list2Str(List<T> list, Class<?> cla) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, cla);
        String res = objectMapper.writeValueAsString(list);
        return res;
    }

    /**
     * 获得子串
     *
     * @param key
     * @param json
     * @return
     * @throws IOException
     */
    public static String get(String key, String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        return jsonNode.get(key).toString();
    }

    /**
     * 获取json Data的简便方法，会检查结果，结果不正确抛出异常
     *
     * @param json
     * @return
     * @throws IOException
     * @throws ServiceException
     */
    public static String getDataAndCheck(String json) throws IOException, ServiceException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        int code = Integer.parseInt(jsonNode.get("code").toString());
        String message = jsonNode.get("message").asText();
        //Todo: 获取的message有乱码问题
        if (code == 200)
            return jsonNode.get("data").toString();
        else {
            throw new ServiceException(code, message);
        }
    }

    /**
     * string 转java bean
     *
     * @param jsonStr
     * @param cls
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T string2Bean(String jsonStr, Class<?> cls) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return (T) objectMapper.readValue(jsonStr, cls);
    }

    /**
     * bean 转java
     *
     * @param bean
     * @param <T>
     * @return
     * @throws JsonProcessingException
     */
    public static <T> String bean2String(T bean) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(bean);
    }

}