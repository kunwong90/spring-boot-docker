package com.example.demo.jackson.test;

import com.example.demo.util.JacksonJsonUtils;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;
import java.util.Map;

public class MapToJson {

    /**
     * 使用 @JsonAnySetter 必须要有默认构造方法
     */
    public MapToJson() {

    }


    public MapToJson(String name) {
        this.name = name;
    }


    private String name;
    private final Map<String, Object> properties = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     * @JsonAnyGetter 将一个map集合中的key/value，序列化成为json
     */
    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * @param key
     * @param value
     * @JsonAnySetter 反序列化时，将定义以外的元素通过key/value形式保存到map中。
     */
    @JsonAnySetter
    public void setProperties(String key, Object value) {
        this.properties.put(key, value);
    }

    public static void main(String[] args) throws JsonProcessingException {
        MapToJson getJson = new MapToJson("张三");
        getJson.setProperties("key1", "属性1");
        getJson.setProperties("key2", 2);
        String jsonStr = JacksonJsonUtils.toJson(getJson);
        System.out.println("@JsonAnyGetter序列化Map转json：" + jsonStr);//{"name":"张三","key1":"属性1","key2":2}

        String json = "{\"name\":\"张三\",\"s\":\"属性1\",\"p\":2}";
        MapToJson setJson = JacksonJsonUtils.toBean(json, MapToJson.class);
        System.out.println("@JsonAnySetter反序列化Json转实体：" + setJson.getProperties());//{p=2, s=属性1}


    }
}
