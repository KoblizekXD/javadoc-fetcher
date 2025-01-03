package lol.koblizek.javadocfetcher.repositories;

import lol.koblizek.javadocfetcher.models.ClassJavadocData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassJavadocDataRepository extends JpaRepository<ClassJavadocData, String> {
}
