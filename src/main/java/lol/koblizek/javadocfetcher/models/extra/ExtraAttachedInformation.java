package lol.koblizek.javadocfetcher.models.extra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import lol.koblizek.javadocfetcher.models.ExtendedJavadocData;
import lol.koblizek.javadocfetcher.models.javadoc.AttachedType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public abstract class ExtraAttachedInformation<T extends Node> {
    
    static final Logger LOGGER = LoggerFactory.getLogger(ExtraAttachedInformation.class);
    
    public abstract AttachedType correspondingType();
    public abstract void setup(T node);

    public Optional<String> serialize() {
        try {
            return Optional.ofNullable(ExtendedJavadocData.OBJECT_MAPPER.writeValueAsString(this));
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to serialize extra attached information!", e);
            return Optional.empty();
        }
    }
    
    public static String nodeAsString(Node node) {
        switch (node) {
            case MethodDeclaration md -> {
                MethodExtras methodExtras = new MethodExtras();
                methodExtras.setup(md);
                return methodExtras.serialize().orElse(null);
            }
            case FieldDeclaration fd -> {
                FieldExtras fieldExtras = new FieldExtras();
                fieldExtras.setup(fd);
                return fieldExtras.serialize().orElse(null);
            }
            case ClassOrInterfaceDeclaration cd -> {
                ClassExtras classExtras = new ClassExtras(cd.isInterface() ? AttachedType.INTERFACE : AttachedType.CLASS);
                classExtras.setup(cd);
                return classExtras.serialize().orElse(null);
            }
            default -> {
                LOGGER.error("Unknown node type: {}", node.getClass());
                return null;
            }
        }
    }
}
