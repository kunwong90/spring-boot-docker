package com.example.demo.jackson.test;


import com.example.demo.util.JacksonJsonUtils;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @JsonPropertyOrder 指定实体字段序列化的顺序。
 */
@JsonPropertyOrder({"name", "nickName", "id"})
public class OrderProperty {

    private int id;
    private String name;
    private String nickName;

    public OrderProperty(int id, String name, String nickName) {
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
        OrderProperty orderProperty = new OrderProperty(1, "zhangsan", "张三");
        String json = JacksonJsonUtils.toJson(orderProperty);
        System.out.println(json);// 未使用 @JsonPropertyOrder 输出 {"id":1,"name":"zhangsan","nickName":"张三"}
        // 使用 @JsonPropertyOrder 输出 {"name":"zhangsan","nickName":"张三","id":1}
    }
}
