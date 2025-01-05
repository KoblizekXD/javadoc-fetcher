package lol.koblizek.javadocfetcher.models.extra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javaparser.ast.Node;
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
}
