package lol.koblizek.javadocfetcher.models.extra;

import com.github.javaparser.ast.body.MethodDeclaration;
import lol.koblizek.javadocfetcher.models.extra.util.Annotations;
import lol.koblizek.javadocfetcher.models.javadoc.AttachedType;
import lol.koblizek.javadocfetcher.util.NodeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodExtras extends ExtraAttachedInformation<MethodDeclaration> {
    
    private Annotations annotations;
    private String returnType;
    private Map<String, String> parameters;
    private List<String> modifiers;
    
    @Override
    public AttachedType correspondingType() {
        return AttachedType.METHOD;
    }

    @Override
    public void setup(MethodDeclaration node) {
        annotations = new Annotations(node);
        returnType = node.getTypeAsString();
        parameters = node.getParameters().stream()
            .collect(HashMap::new, (m, p) -> m.put(p.getNameAsString(), p.getTypeAsString()), Map::putAll);
        modifiers = NodeUtils.getAccessModifiers(node);
    }

    public Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotations annotations) {
        this.annotations = annotations;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public List<String> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<String> modifiers) {
        this.modifiers = modifiers;
    }
}
