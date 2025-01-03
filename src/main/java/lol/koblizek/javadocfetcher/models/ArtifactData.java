package lol.koblizek.javadocfetcher.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Contains data about an artifact with specific version.
 */
@Entity
@Table(name = "artifact_data")
public class ArtifactData {

    @Id
    private String id; // e.g. "org.springframework:spring-core:5.3.9"
    
    private String uri; // The URI leading to the artifact's files on the repository
    
    @OneToMany
    private Set<ClassJavadocData> classJavadocData;

    public ArtifactData(String id, String uri) {
        this.id = id;
        this.uri = uri;
        this.classJavadocData = new HashSet<>();
    }
    
    public ArtifactData() {
    }
    
    public void addClassData(ClassJavadocData... classJavadocData) {
        this.classJavadocData.addAll(List.of(classJavadocData));
    }
    
    public String getId() {
        return id;
    }
    
    public String getGroupId() {
        return id.split(":")[0];
    }
    
    public String getArtifactId() {
        return id.split(":")[1];
    }
    
    public String getVersion() {
        return id.split(":")[2];
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Set<String> getAvailableClasses() {
        URI sourceFileUri = UriComponentsBuilder.fromUriString(getUri())
                .path("/")
                .path(getArtifactId() + "-" + getVersion() + ".jar")
                .build().toUri();

        try (var is = new ZipInputStream(sourceFileUri.toURL().openStream())) {
            Set<String> classes = new HashSet<>();
            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
                if (entry.getName().endsWith(".class")) {
                    classes.add(entry.getName().replace(".class", "").replace("/", "."));
                }
            }
            return classes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
