package lol.koblizek.javadocfetcher.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Contains data about all javadoc in a given class source file.
 */
@Entity
@Table(name = "class_javadoc_data")
public class ClassJavadocData {
    
    @Id
    private String id; // The fully qualified name of the class
    
    public ClassJavadocData(String fqn) {
        this.id = fqn;
    }
    
    public ClassJavadocData() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
