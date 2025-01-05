package lol.koblizek.javadocfetcher.models.extra;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import lol.koblizek.javadocfetcher.models.extra.util.Annotations;
import lol.koblizek.javadocfetcher.models.javadoc.AttachedType;
import lol.koblizek.javadocfetcher.util.NodeUtils;

import java.util.List;

public class ClassExtras extends ExtraAttachedInformation<ClassOrInterfaceDeclaration> {

    @JsonIgnore
    private final AttachedType type;
    
    @JsonUnwrapped
    private Annotations annotations;
    private List<String> modifiers;
    private List<String> implementing;
    private List<String> extending;
    private List<String> typeParameters;

    public ClassExtras(AttachedType type) {
        this.type = type;
    }
    
    @Override
    public AttachedType correspondingType() {
        return type;
    }

    @Override
    public void setup(ClassOrInterfaceDeclaration node) {
        annotations = new Annotations(node);
        modifiers = NodeUtils.getAccessModifiers(node);
        implementing = node.getImplementedTypes().stream().map(NodeWithSimpleName::getNameAsString).toList();
        extending = node.getExtendedTypes().stream().map(NodeWithSimpleName::getNameAsString).toList();
        typeParameters = node.getTypeParameters().stream().map(NodeWithSimpleName::getNameAsString).toList();
    }

    public AttachedType getType() {
        return type;
    }

    public Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotations annotations) {
        this.annotations = annotations;
    }

    public List<String> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<String> modifiers) {
        this.modifiers = modifiers;
    }

    public List<String> getImplementing() {
        return implementing;
    }

    public void setImplementing(List<String> implementing) {
        this.implementing = implementing;
    }

    public List<String> getExtending() {
        return extending;
    }

    public void setExtending(List<String> extending) {
        this.extending = extending;
    }

    public List<String> getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(List<String> typeParameters) {
        this.typeParameters = typeParameters;
    }
}
