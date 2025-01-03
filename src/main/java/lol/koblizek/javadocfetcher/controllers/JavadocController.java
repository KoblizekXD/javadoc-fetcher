package lol.koblizek.javadocfetcher.controllers;

import lol.koblizek.javadocfetcher.models.http.ArtifactQuery;
import lol.koblizek.javadocfetcher.services.JavadocService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/javadoc")
public class JavadocController {

    private final JavadocService javadocService;

    public JavadocController(JavadocService javadocService) {
        this.javadocService = javadocService;
    }
    
    @PostMapping
    public void fetchJavadoc(@RequestBody ArtifactQuery artifactQuery) {
        
    }
}
