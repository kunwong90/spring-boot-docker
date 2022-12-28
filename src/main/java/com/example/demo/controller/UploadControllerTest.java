package com.example.demo.controller;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class UploadControllerTest {

    public static void main(String[] args) {
        HttpPost postMethod = new HttpPost("http://localhost:8080/upload");


        Map<String, String> input = new HashMap<>();
        input.put("key", "123");
        input.put("channel", "OTA");
        input.put("airline", "HU");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            for (Map.Entry<String, String> entry : input.entrySet()) {
                String key = entry.getKey();
                StringBody stringBody = new StringBody(entry.getValue(), ContentType.MULTIPART_FORM_DATA);
                builder.addPart(key, stringBody);
            }

            File file = new File("F:\\aaa.zip");
            builder.addBinaryBody("file", Files.newInputStream(file.toPath()), ContentType.create("multipart/form-data"), file.getName());
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(StandardCharsets.UTF_8);
            postMethod.setEntity(builder.build());
            HttpEntity reqEntity = builder.build();
            postMethod.setEntity(reqEntity);
            CloseableHttpResponse closeableHttpResponse = httpClient.execute(postMethod);

            HttpEntity entity = closeableHttpResponse.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");
                System.out.println(result);
            }
        } catch (Exception e) {

        }


    }
}
