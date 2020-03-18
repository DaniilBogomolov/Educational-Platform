package ru.itis.services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private Configuration configuration;


    public Template fromPath(String path) {
        //TODO: change templateLoader
        Template template;
        try {
            template = configuration.getTemplate(path);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return template;
    }
}
