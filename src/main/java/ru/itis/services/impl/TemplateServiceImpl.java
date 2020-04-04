package ru.itis.services.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.services.TemplateService;

import java.io.IOException;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private Configuration configuration;


    public Template fromPath(String path) {
        Template template;
        try {
            template = configuration.getTemplate(path);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return template;
    }
}
