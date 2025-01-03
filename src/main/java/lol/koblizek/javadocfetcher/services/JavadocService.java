package lol.koblizek.javadocfetcher.services;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithJavadoc;
import com.github.javaparser.javadoc.Javadoc;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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
        String targetFqn = artifactQuery.classData().replace('$', '.');
        
        try (ZipInputStream zipInputStream = getClassEntry(artifactData, targetEntry)) {
            var cu = StaticJavaParser.parse(zipInputStream);
            ClassJavadocData classJavadocData = getClassJavadocData(cu, targetFqn);
        } catch (IOException e) {
            return null;
        }

        adRepository.save(artifactData);
        return null;
    }
    
    public ClassJavadocData getClassJavadocData(CompilationUnit cu, String targetFqn) {
        Optional<TypeDeclaration> first = cu.findFirst(TypeDeclaration.class, node -> {
            Optional<String> object = ((TypeDeclaration<?>) node).getFullyQualifiedName();
            return object.map(it -> it.equals(targetFqn)).orElse(false);
        });
        if (first.isEmpty()) return null;
        TypeDeclaration<?> typeDeclaration = first.get();
        List<Javadoc> javadocs = typeDeclaration.getChildNodes().stream().filter(it -> !(it instanceof TypeDeclaration<?>)
                        && it instanceof NodeWithJavadoc<?>).map(it -> (NodeWithJavadoc<?>) it)
                .filter(NodeWithJavadoc::hasJavaDocComment)
                .map(it -> it.getJavadoc().get())
                .collect(Collectors.toCollection(ArrayList::new));
        if (typeDeclaration.hasJavaDocComment())
            javadocs.addFirst(typeDeclaration.getJavadoc().get());
        ClassJavadocData classJavadocData = new ClassJavadocData(targetFqn);
        return classJavadocData;
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
