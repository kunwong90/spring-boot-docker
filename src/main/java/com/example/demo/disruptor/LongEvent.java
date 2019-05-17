package com.example.demo.disruptor;

/**
 * Firstly we will define the Event that will carry the data.
 * 定义一个事件用于传输数据
 */
public class LongEvent {

    private Long value;

    public void set(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "LongEvent{" +
                "value=" + value +
                '}';
    }


}
