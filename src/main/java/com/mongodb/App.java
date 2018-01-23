package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.bson.Document;
import spark.Spark;

import java.io.StringWriter;
import java.util.Objects;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(App.class, "/");

        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("course");
        MongoCollection<Document> collection = database.getCollection("hello");
        collection.drop();

        collection.insertOne(new Document("name", "MongoDB"));

        Spark.get("/", (request, response) -> {
            StringWriter writer = new StringWriter();
            try {
                Template template = configuration.getTemplate("hello.ftl");

                /*Map<String, Object> map = new HashMap<String, Object>();
                map.put("fruits", Arrays.asList("apple", "orange", "banana", "peach"));*/

                Document document = collection.find().first();
                template.process(document, writer);

            } catch (Exception e) {
                writer.append(e.getMessage());
            }

            return writer;
        });

        Spark.post("/fruit", (request, response) -> {
            String fruit = request.queryParams("fruit");
            if (Objects.nonNull(fruit)) return "Your fruit is ".concat(fruit); else return "Choose fruit";
        });


        Spark.get("/echo/:thing", (request, response) -> request.params(":thing"));
    }
}
