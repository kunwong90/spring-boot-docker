package com.example.demo.jackson.test;

import com.example.demo.util.JacksonJsonUtils;

public class JsonValue {

    private int id;


    private String name;

    /**
     * @JsonValue 序列化时只返回注解字段的值。只能有一个属性上使用该注解,超过一个异常
     */
    @com.fasterxml.jackson.annotation.JsonValue
    private String nickName;

    public JsonValue(int id, String name, String nickName) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public static void main(String[] args) {
        JsonValue jsonValue = new JsonValue(1, "zhangsan", "张三");
        String json = JacksonJsonUtils.toJson(jsonValue);
        System.out.println(json);// 输出 "张三"
    }

}
