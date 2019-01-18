package com.example.demo.util;

import java.util.UUID;

public final class UUIDUtil {

    public static final String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
