package lol.koblizek.javadocfetcher.models.extra;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.github.javaparser.ast.body.FieldDeclaration;
import lol.koblizek.javadocfetcher.models.extra.util.Annotations;
import lol.koblizek.javadocfetcher.models.javadoc.AttachedType;
import lol.koblizek.javadocfetcher.util.NodeUtils;

import java.util.List;

public class FieldExtras extends ExtraAttachedInformation<FieldDeclaration> {

    @JsonUnwrapped
    private Annotations annotations;
    private List<String> modifiers;
    private String type;
    
    @Override
    public AttachedType correspondingType() {
        return AttachedType.FIELD;
    }

    @Override
    public void setup(FieldDeclaration node) {
        annotations = new Annotations(node);
        modifiers = NodeUtils.getAccessModifiers(node);
        type = node.getElementType().asString();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
