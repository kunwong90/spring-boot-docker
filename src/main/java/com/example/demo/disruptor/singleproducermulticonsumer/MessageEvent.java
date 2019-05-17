package com.example.demo.disruptor.singleproducermulticonsumer;

public class MessageEvent<T> {

    private T value;

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
