package com.mongodb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Spark;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(App.class, "/");

        Spark.get("/", (request, response) -> {
            StringWriter writer = new StringWriter();
            try {
                Template template = configuration.getTemplate("hello.ftl");

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", "Andrii");

                template.process(map, writer);
            } catch (Exception e) {
                writer.append(e.getMessage());
            }

            return writer;
        });
    }
}
