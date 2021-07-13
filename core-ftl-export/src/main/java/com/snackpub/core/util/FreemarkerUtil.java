package com.snackpub.core.util;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

/**
 * freemarker 生产PDF工具类
 *
 * @author snackpub
 * @date 2021/7/8
 */
public final class FreemarkerUtil {

    public static String process(String template, Map<String, Object> model) throws IOException, TemplateException {
        if (template == null) {
            return null;
        }
        Configuration configuration = null;
        ApplicationContext applicationContext = SpringUtil.getContext();
        if (applicationContext != null) {
            FreeMarkerConfigurer freeMarkerConfigurer = SpringUtil.getBean("freeMarkerConfigurer", FreeMarkerConfigurer.class);
            if (freeMarkerConfigurer != null) {
                configuration = freeMarkerConfigurer.getConfiguration();
            }
        }
        if (configuration == null) {
            configuration = new Configuration(Configuration.VERSION_2_3_0);
            configuration.setTagSyntax(0);
            configuration.setEncoding(Locale.CHINA, "UTF-8");
        }
        StringWriter out = new StringWriter();
        new Template("template", new StringReader(template), configuration).process(model, out);
        return out.toString();
    }


    public static void process(String template, Map<String, Object> model, OutputStream outputStream) throws IOException, TemplateException, DocumentException {
        String process = process(template, model);
        ITextRenderer iTextRenderer = new ITextRenderer();
        iTextRenderer.setDocumentFromString(process);
        ITextFontResolver fontResolver = iTextRenderer.getFontResolver();
        // 字体：中文雅黑
        String frontPath = new ClassPathResource("/template-ftl/msyh.ttf").getFile().getAbsolutePath();
        fontResolver.addFont(frontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        // 语言：简体中文
        String frontPath2 = new ClassPathResource("/template-ftl/simsun.ttc").getFile().getAbsolutePath();
        fontResolver.addFont(frontPath2, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        iTextRenderer.layout();
        iTextRenderer.createPDF(outputStream);
        outputStream.flush();
    }

}