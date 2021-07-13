package com.snackpub.core.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

public class SnackPubController {

    /**
     * ============================     REQUEST    =================================================
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 获取request
     *
     * @return HttpServletRequest
     */
    public HttpServletRequest getRequest() {
        return this.request;
    }

    /**
     * 文件下载响应流通用配置
     *
     * @param fileName 文件名称
     * @param suffix   文件后缀
     * @return outputStream
     */
    @SneakyThrows
    public ServletOutputStream downloadResponse(HttpServletResponse response, String fileName, String suffix) {
        response.setCharacterEncoding("UTF-8");
        // 设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("application/x-download");
        fileName = fileName + "." + suffix;
        fileName = URLEncoder.encode(fileName, "UTF-8");
        // 设置文件头：最后一个参数是设置下载文件名
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        return response.getOutputStream();
    }

    /**
     * 没有文件下载时返回空文件，防止页面显示没反应
     *
     * @param fileName 文件名称
     * @param suffix   文件后缀
     */
    @SneakyThrows
    public void emptyResponse(HttpServletResponse response, String fileName, String suffix) {
        ServletOutputStream outputStream = downloadResponse(response, fileName, suffix);
        outputStream.write(0);
        outputStream.close();
    }
}
