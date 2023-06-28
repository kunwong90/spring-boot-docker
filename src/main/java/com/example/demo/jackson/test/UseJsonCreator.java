package com.example.demo.jackson.test;

import com.example.demo.util.JacksonJsonUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UseJsonCreator {

    private String name;
    private String nickName;

    /**
     * @param name
     * @param nName
     * @JsonCreator 反序列化时，调用构造方法或工厂，在反序列化时，可以指定json中的字段与实体字段相匹配。例如：json中字段p，则可以用实体中的c与之相匹配。
     */
    @JsonCreator
    public UseJsonCreator(@JsonProperty("name") String name,
                          @JsonProperty("nName") String nName) {
        this.name = name;
        this.nickName = nName;
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
        String json = "{\"name\":\"zhangsan\",\"nName\":\"张三\"}";

        UseJsonCreator useJsonCreator = JacksonJsonUtils.toBean(json, UseJsonCreator.class);
        System.out.println(useJsonCreator.getName());
        System.out.println(useJsonCreator.getNickName());
    }
}
