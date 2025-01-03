package lol.koblizek.javadocfetcher;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import lol.koblizek.javadocfetcher.services.JavadocService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;

@SpringBootTest
class JavadocFetcherApplicationTests {
    
    @Autowired
    private JavadocService javadocService;
    
    @Test
    void testJavadocParsing() throws MalformedURLException {
        try (InputStream inputStream = URI.create("https://raw.githubusercontent.com/google/guava/refs/heads/master/guava/src/com/google/common/eventbus/Dispatcher.java")
                     .toURL().openStream()) {
            CompilationUnit cu = StaticJavaParser.parse(inputStream);
            javadocService.getClassJavadocData(cu, "com.google.common.eventbus.Dispatcher");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
