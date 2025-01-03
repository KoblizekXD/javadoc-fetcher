package lol.koblizek.javadocfetcher.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "artifact_data")
public class ArtifactData {

    @Id
    private String id;

    public ArtifactData(String id) {
        this.id = id;
    }
    
    public ArtifactData() {
    }
    
    
}
