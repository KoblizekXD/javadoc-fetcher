package lol.koblizek.javadocfetcher.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.javaparser.javadoc.description.JavadocSnippet;

import java.io.IOException;

public class JavadocSnippetDeserializer extends JsonDeserializer<JavadocSnippet> {
    @Override
    public JavadocSnippet deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return new JavadocSnippet(p.getText());
    }
}
