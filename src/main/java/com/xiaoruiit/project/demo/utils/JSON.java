package com.xiaoruiit.project.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * jackJson 达到 fastJson使用
 * @author hanxiaorui
 * @date 2022/2/17
 */
public class JSON {

    public static String toJSONString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
