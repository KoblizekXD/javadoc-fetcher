package lol.koblizek.javadocfetcher.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.javadoc.description.JavadocSnippet;
import jakarta.persistence.*;
import lol.koblizek.javadocfetcher.models.javadoc.AttachedType;
import lol.koblizek.javadocfetcher.util.JavadocSnippetDeserializer;
import lol.koblizek.javadocfetcher.util.JavadocSnippetSerializer;
import lol.koblizek.javadocfetcher.util.NodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "javadoc_data")
public class ExtendedJavadocData {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtendedJavadocData.class);

    static {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(JavadocSnippet.class, new JavadocSnippetSerializer());
        simpleModule.addDeserializer(JavadocSnippet.class, new JavadocSnippetDeserializer());
        OBJECT_MAPPER.registerModule(simpleModule);
        OBJECT_MAPPER.registerModule(new Jdk8Module());
    }
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Lob
    @Column(columnDefinition = "TEXT")
    private String javadoc;
    
    @ManyToOne
    @JoinColumn(name = "class_javadoc_data_id")
    @JsonIgnore
    private ClassJavadocData classJavadocData;
    
    @Enumerated(EnumType.STRING)
    private AttachedType attachedType;

    /**
     * The name of the attached element,
     * can be null if {@link #attachedType} is {@link AttachedType#CONSTRUCTOR}.
     * Will return fully qualified name if the attached type is {@link AttachedType#CLASS}
     * , {@link AttachedType#ANNOTATION} or {@link AttachedType#INTERFACE}.
     */
    private String attachedName;

    public ExtendedJavadocData(JavadocComment comment) throws JsonProcessingException {
        this.javadoc = OBJECT_MAPPER.writeValueAsString(comment.parse());
        this.attachedName = NodeUtils.getName(comment.getCommentedNode().orElseThrow());
        this.attachedType = NodeUtils.getAttachedType(comment.getCommentedNode().orElseThrow());
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

    public JsonNode getJavadoc() {
        try {
            return OBJECT_MAPPER.readValue(javadoc, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to parse javadoc", e);
            return null;
        }
    }

    public void setJavadoc(String javadoc) {
        this.javadoc = javadoc;
    }

    public AttachedType getAttachedType() {
        return attachedType;
    }

    public void setAttachedType(AttachedType attachedType) {
        this.attachedType = attachedType;
    }

    public String getAttachedName() {
        return attachedName;
    }

    public void setAttachedName(String attachedName) {
        this.attachedName = attachedName;
    }
}
