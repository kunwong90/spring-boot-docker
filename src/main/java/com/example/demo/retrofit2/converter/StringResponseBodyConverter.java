package com.example.demo.retrofit2.converter;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import javax.annotation.Nullable;
import java.io.IOException;

public class StringResponseBodyConverter implements Converter<ResponseBody, String> {
    @Nullable
    @Override
    public String convert(ResponseBody value) throws IOException {
        return value.string();
    }
}
