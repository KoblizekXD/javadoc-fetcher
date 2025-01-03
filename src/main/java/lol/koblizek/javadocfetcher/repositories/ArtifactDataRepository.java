package lol.koblizek.javadocfetcher.repositories;

import lol.koblizek.javadocfetcher.models.ArtifactData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtifactDataRepository extends JpaRepository<ArtifactData, String> {
}
