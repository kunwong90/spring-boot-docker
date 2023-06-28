package com.example.demo.jackson.test;

import com.example.demo.util.JacksonJsonUtils;

public class JsonRawValue {

    private String name;

    /**
     * @JsonRawValue 按原样序列化属性值。
     */
    @com.fasterxml.jackson.annotation.JsonRawValue
    private String json;

    public JsonRawValue(String name, String json) {
        this.name = name;
        this.json = json;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public static void main(String[] args) {
        JsonRawValue jsonRawValue = new JsonRawValue("张三", "{\"age\":30,\"gender\":\"男\"}");
        String json = JacksonJsonUtils.toJson(jsonRawValue);
        System.out.println(json);// 使用 @com.fasterxml.jackson.annotation.JsonRawValue 输出 {"name":"张三","json":{"age":30,"gender":"男"}}
        // 未使用 @com.fasterxml.jackson.annotation.JsonRawValue 输出 {"name":"张三","json":"{\"age\":30,\"gender\":\"男\"}"}
    }
}
