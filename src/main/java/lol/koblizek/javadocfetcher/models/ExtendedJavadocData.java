package lol.koblizek.javadocfetcher.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.javadoc.description.JavadocSnippet;
import jakarta.persistence.*;
import lol.koblizek.javadocfetcher.util.JavadocSnippetDeserializer;
import lol.koblizek.javadocfetcher.util.JavadocSnippetSerializer;

@Entity
@Table(name = "javadoc_data")
public class ExtendedJavadocData {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    static {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(JavadocSnippet.class, new JavadocSnippetSerializer());
        simpleModule.addDeserializer(JavadocSnippet.class, new JavadocSnippetDeserializer());
        OBJECT_MAPPER.registerModule(simpleModule);
    }
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String javadoc;
    
    @ManyToOne
    @JoinColumn(name = "class_javadoc_data_id")
    private ClassJavadocData classJavadocData;

    public ExtendedJavadocData(JavadocComment comment) throws JsonProcessingException {
        this.javadoc = OBJECT_MAPPER.writeValueAsString(comment.parse());
    }
    
    public ExtendedJavadocData() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public ClassJavadocData getClassJavadocData() {
        return classJavadocData;
    }

    public void setClassJavadocData(ClassJavadocData classJavadocData) {
        this.classJavadocData = classJavadocData;
    }

    public String getJavadoc() {
        return javadoc;
    }

    public void setJavadoc(String javadoc) {
        this.javadoc = javadoc;
    }
}
