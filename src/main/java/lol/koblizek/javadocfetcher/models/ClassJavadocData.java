package lol.koblizek.javadocfetcher.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains data about all javadoc in a given class source file.
 */
@Entity
@Table(name = "class_javadoc_data")
public class ClassJavadocData {
    
    @Id
    private String id; // The fully qualified name of the class
    
    @OneToMany
    @JoinColumn(name = "class_javadoc_data_id")
    private List<ExtendedJavadocData> javadocData;
    
    @ManyToOne
    @JoinColumn(name = "artifact_data_id")
    private ArtifactData artifactData;

    public ArtifactData getArtifactData() {
        return artifactData;
    }

    public void setArtifactData(ArtifactData artifactData) {
        this.artifactData = artifactData;
    }

    public ClassJavadocData(String fqn) {
        this.id = fqn;
        this.javadocData = new ArrayList<>();
    }
    
    public ClassJavadocData() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    
    public void addJavadoc(ExtendedJavadocData comment) throws JsonProcessingException {
        javadocData.add(comment);
    }
}
