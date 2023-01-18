package com.example.demo.controller;


import com.example.demo.entity.DishonourRefundDto;
import com.example.demo.util.JacksonJsonUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.FileOutputStream;
import java.util.List;

@Controller
@RequestMapping
public class UploadController {

    @PostMapping(value = "/upload")
    @ResponseBody
    public String upload(DishonourRefundDto request, HttpServletRequest servletRequest, MultipartFile file) {
        System.out.println(JacksonJsonUtils.toJson(request));

        try {
            System.out.println("附件名 = " + file.getOriginalFilename() + " ,附件大小 = " + file.getSize());
            String base64 = DatatypeConverter.printBase64Binary(file.getBytes());
            decoderBase64File(base64, "F:\\test" + System.currentTimeMillis() + ".rar");
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "{\"success\":true}";
    }

    public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }


    @PostMapping(value = "/multiUpload")
    @ResponseBody
    public String multiUpload(DishonourRefundDto request, HttpServletRequest servletRequest, List<MultipartFile> file) {
        System.out.println(JacksonJsonUtils.toJson(request));

        try {
            if (CollectionUtils.isNotEmpty(file)) {
                for (MultipartFile file1 : file) {
                    String fileSuffix = file1.getOriginalFilename().substring(file1.getOriginalFilename().lastIndexOf("."));
                    System.out.println("附件名 = " + file1.getOriginalFilename() + " ,附件大小 = " + file1.getSize());
                    String base64 = DatatypeConverter.printBase64Binary(file1.getBytes());
                    decoderBase64File(base64, "F:\\test" + System.currentTimeMillis() + fileSuffix);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return "{\"success\":true}";
    }
}
