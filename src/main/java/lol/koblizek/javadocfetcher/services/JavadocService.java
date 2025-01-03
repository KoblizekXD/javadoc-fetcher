package lol.koblizek.javadocfetcher.services;

import lol.koblizek.javadocfetcher.models.ArtifactData;
import lol.koblizek.javadocfetcher.models.http.ArtifactQueryWithoutClass;
import lol.koblizek.javadocfetcher.repositories.ArtifactDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

import java.util.Optional;
import java.util.Set;

@Service
public class JavadocService {

    private final ArtifactDataRepository adRepository;

    public JavadocService(ArtifactDataRepository repository) {
        this.adRepository = repository;
    }
    
    public Set<String> getClasses(ArtifactQueryWithoutClass artifactQuery) {
        Optional<UriComponents> files = artifactQuery.toURI();
        if (files.isPresent()) {
            ArtifactData artifactData = new ArtifactData(artifactQuery.toId(), files.get().toUriString());
            adRepository.save(artifactData);
            return artifactData.getAvailableClasses();
        }
        return Set.of();
    }
}
