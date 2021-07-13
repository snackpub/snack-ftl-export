package com.snackpub.core.controller;

import com.snackpub.core.constant.SystemConstant;
import com.snackpub.core.util.FreemarkerUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.snackpub.core.constant.SystemConstant.UTF_8;

/**
 * ftl 转换为pdf下载
 *
 * @author snackPub
 * @date 2021/7/13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/export")
public class ExportController extends SnackPubController {

    @SneakyThrows
    @RequestMapping("/exportPDF")
    public void exportPDF(HttpServletResponse response, HttpServletRequest request) {
        ServletOutputStream outputStream = null;
        try {
            outputStream = downloadResponse(response, "snackpub", SystemConstant.PDF_SUFFIX);
            // 加载读取模板内容,注意这里要设置编码，如果读取到的默人编码是gbk，ftl中的中文将乱码
            String templateString = FileUtils.readFileToString(new ClassPathResource("/template-ftl/test.ftl").getFile(), UTF_8);
            // 参数注入，用于freemarker渲染.
            Map<String, Object> parMaps = new HashMap<>();
            parMaps.put("hello", "hello snackpub!");
            parMaps.put("list", Arrays.asList("a", "b", "c"));
            FreemarkerUtil.process(templateString, parMaps, outputStream);
        } finally {
            if (Objects.nonNull(outputStream))
                outputStream.close();
        }
        //emptyResponse(response, "SJ2", SystemConstant.PDF_SUFFIX);

    }
}
