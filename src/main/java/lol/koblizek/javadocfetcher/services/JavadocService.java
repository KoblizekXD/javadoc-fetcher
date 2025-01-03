package lol.koblizek.javadocfetcher.services;

import lol.koblizek.javadocfetcher.models.ArtifactData;
import lol.koblizek.javadocfetcher.models.ClassJavadocData;
import lol.koblizek.javadocfetcher.models.http.ArtifactQuery;
import lol.koblizek.javadocfetcher.models.http.ArtifactQueryWithoutClass;
import lol.koblizek.javadocfetcher.repositories.ArtifactDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
    
    public ClassJavadocData lookupJavadoc(ArtifactQuery artifactQuery) {
        ArtifactData artifactData = adRepository.findById(artifactQuery.toId()).or(() -> {
            Optional<UriComponents> files = artifactQuery.toURI();
            return files.map(uriComponents -> new ArtifactData(artifactQuery.toId(), uriComponents.toUriString()));
        }).orElse(null);
        
        if (artifactData == null) return null;

        String[] simpleName = artifactQuery.classData().substring(artifactQuery.classData().lastIndexOf('.') + 1)
                .split("\\$");
        String targetEntry = artifactQuery.classData().substring(0, artifactQuery.classData().lastIndexOf('.') + simpleName[0].length() + 1)
                .replace('.', '/');
        
        try (ZipInputStream zipInputStream = getClassEntry(artifactData, targetEntry)) {
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        adRepository.save(artifactData);
    }
    
    private ZipInputStream getClassEntry(ArtifactData data, String binaryClassName) {

        URI sourceFileUri = UriComponentsBuilder.fromUriString(data.getUri())
                .path("/")
                .path(data.getArtifactId() + "-" + data.getVersion() + "-sources.jar")
                .build().toUri();

        try (var is = new ZipInputStream(sourceFileUri.toURL().openStream())) {
            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
                if (entry.getName().equals(binaryClassName + ".java")) {
                    return is;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
