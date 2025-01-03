package lol.koblizek.javadocfetcher.controllers;

import lol.koblizek.javadocfetcher.models.http.ArtifactQuery;
import lol.koblizek.javadocfetcher.services.JavadocService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

    /**
     * Returns a set of all classes available in the artifact.
     * The reason for why this method uses POST rather than GET is that
     * the request may create a new database entries
     * @param id The id of the artifact
     * @return A set of all classes available in the artifact
     */
    @PostMapping("/classes")
    public ResponseEntity<Set<String>> getAvailableClasses(@RequestParam String id) {
        return ResponseEntity.ok().build();
    }
}