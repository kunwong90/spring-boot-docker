package com.example.demo.service;

import java.util.List;
import java.util.concurrent.Future;

public interface IStudentService {

    /**
     * 保存方法
     */
    void save();

    Future<List<String>> findAll();
}
