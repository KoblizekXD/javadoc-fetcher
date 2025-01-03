package lol.koblizek.javadocfetcher.controllers;

import lol.koblizek.javadocfetcher.models.ClassJavadocData;
import lol.koblizek.javadocfetcher.models.http.ArtifactQuery;
import lol.koblizek.javadocfetcher.models.http.ArtifactQueryWithoutClass;
import lol.koblizek.javadocfetcher.services.JavadocService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/javadoc")
public class JavadocController {

    private final JavadocService javadocService;

    public JavadocController(JavadocService javadocService) {
        this.javadocService = javadocService;
    }
    
    @PostMapping
    public ResponseEntity<ClassJavadocData> fetchJavadoc(@RequestBody ArtifactQuery artifactQuery) {
        
    }

    /**
     * Returns a set of all classes available in the artifact.
     * The reason for why this method uses POST rather than GET is that
     * the request may create a new database entries
     * @param artifactQuery The artifact query
     * @return A set of all classes available in the artifact
     */
    @PostMapping("/classes")
    public ResponseEntity<Set<String>> getAvailableClasses(@RequestBody ArtifactQueryWithoutClass artifactQuery) {
        Set<String> classes = javadocService.getClasses(artifactQuery);
        if (classes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(classes);
    }
}
