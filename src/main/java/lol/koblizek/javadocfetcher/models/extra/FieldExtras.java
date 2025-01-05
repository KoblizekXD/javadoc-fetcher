package lol.koblizek.javadocfetcher.models.extra;

import com.github.javaparser.ast.body.FieldDeclaration;
import lol.koblizek.javadocfetcher.models.extra.util.Annotations;
import lol.koblizek.javadocfetcher.models.javadoc.AttachedType;
import lol.koblizek.javadocfetcher.util.NodeUtils;

import java.util.List;

public class FieldExtras extends ExtraAttachedInformation<FieldDeclaration> {
    
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
}
