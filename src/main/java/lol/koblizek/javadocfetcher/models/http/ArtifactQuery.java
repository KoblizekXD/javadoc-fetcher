package lol.koblizek.javadocfetcher.models.http;

import org.springframework.web.util.InvalidUrlException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

public record ArtifactQuery(
        String repository,
        String groupId,
        String artifactId,
        String version,
        String classData
) {
    /**
     * @return URI to the files corresponding to the artifact id and version
     */
    public Optional<UriComponents> toURI() {
        try {
            String groupPath = groupId.replace(".", "/");
            return Optional.of(UriComponentsBuilder.fromUriString(repository)
                    .path("/")
                    .path(groupPath)
                    .path("/")
                    .path(artifactId)
                    .path("/")
                    .path(version).build());
        } catch (InvalidUrlException e) {
            return Optional.empty();
        }
    }

    /**
     * @return URI to the source file corresponding to the artifact id and version
     */
    public Optional<UriComponents> toSourceURI() {
        try {
            String groupPath = groupId.replace(".", "/");
            return Optional.of(UriComponentsBuilder.fromUriString(repository)
                    .path("/")
                    .path(groupPath)
                    .path("/")
                    .path(artifactId)
                    .path("/")
                    .path(version)
                    .path("/")
                    .path(artifactId + "-" + version + "-sources.jar")
                    .build());
        } catch (InvalidUrlException e) {
            return Optional.empty();
        }
    }
    
    public String toId() {
        return groupId + ":" + artifactId + ":" + version;
    }
}
