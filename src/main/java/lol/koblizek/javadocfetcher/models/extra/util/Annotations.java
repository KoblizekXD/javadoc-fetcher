package lol.koblizek.javadocfetcher.models.extra.util;

import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Annotations {
    
    private final List<AnnotationData> annotationData;
    
    public Annotations(NodeWithAnnotations<?> node) {
        annotationData = new ArrayList<>();
        for (AnnotationExpr annotation : node.getAnnotations()) {
            switch (annotation) {
                case MarkerAnnotationExpr mae -> 
                        annotationData.add(new AnnotationData(mae.getNameAsString(), Map.of()));
                case SingleMemberAnnotationExpr smae -> 
                        annotationData.add(new AnnotationData(smae.getNameAsString(), Map.of("value", smae.getMemberValue().toString())));
                case NormalAnnotationExpr nae ->
                        annotationData.add(new AnnotationData(nae.getNameAsString(), nae.getPairs().stream()
                                .collect(HashMap::new, (map, pair) -> map.put(pair.getNameAsString(), pair.getValue().toString()), Map::putAll)));
                default -> throw new IllegalStateException("Unexpected value: " + annotation);
            }
        }
    }

    public List<AnnotationData> getAnnotationData() {
        return annotationData;
    }
    
    public record AnnotationData(String name, Map<String, String> value) {
    }
}
