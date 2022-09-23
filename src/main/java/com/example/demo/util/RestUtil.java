package com.example.demo.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class RestUtil {
    public static final String REQUEST_METHOD_POST = "POST";
    public static final String REQUEST_METHOD_GET = "GET";

    public static final String REQUEST_METHOD_DELETE = "DELETE";

    /**
     * 此方法描述的是：设置response head
     *
     * @param response HttpServletResponse
     */
    private static void setHead(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
    }

    /**
     * rest 输出
     *
     * @param response
     * @param param
     * @throws IOException
     */

    public static void write(HttpServletResponse response, String param) throws IOException {
        setHead(response);
        response.getWriter().print(new String(param.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
    }

    /**
     * rest 输入
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static String getData(HttpServletRequest request) throws IOException {
        String method = request.getMethod();
        String ret;
        if (method.equalsIgnoreCase(REQUEST_METHOD_GET) || method.equalsIgnoreCase(REQUEST_METHOD_DELETE)) {
            ret = request.getQueryString();
        } else {
            ret = getBodyData(request);
        }
        if (ret == null) {
            return null;
        }

        return URLDecoder.decode(ret, "UTF-8");
    }

    private static String getBodyData(HttpServletRequest request) throws IOException {
        StringBuilder data = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            data.append(line);
        }
        return data.toString();
    }

}
