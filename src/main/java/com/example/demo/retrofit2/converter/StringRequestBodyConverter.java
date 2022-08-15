package com.example.demo.retrofit2.converter;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class StringRequestBodyConverter implements Converter<String, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=UTF-8");

    @Nullable
    @Override
    public RequestBody convert(String value) throws IOException {
        return RequestBody.create(MEDIA_TYPE, value.getBytes(StandardCharsets.UTF_8));
    }
}
