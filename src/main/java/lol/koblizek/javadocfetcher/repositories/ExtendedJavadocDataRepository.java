package lol.koblizek.javadocfetcher.repositories;

import lol.koblizek.javadocfetcher.models.ExtendedJavadocData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtendedJavadocDataRepository extends JpaRepository<ExtendedJavadocData, Long> {
}
