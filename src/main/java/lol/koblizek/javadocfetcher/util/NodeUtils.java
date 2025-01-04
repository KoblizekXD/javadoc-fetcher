package lol.koblizek.javadocfetcher.util;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.modules.ModuleDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithName;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import lol.koblizek.javadocfetcher.models.javadoc.AttachedType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NodeUtils {
    
    public static final Logger LOGGER = LoggerFactory.getLogger(NodeUtils.class);
    
    private NodeUtils() {}

    /**
     * Can return either the simple name of the node, fully qualified name or null
     * @param node The node
     * @return The name of the node
     */
    public static String getName(Node node) {
        
        if (node instanceof TypeDeclaration<?> td && td.getFullyQualifiedName().isPresent())
            return td.getFullyQualifiedName().get();
        
        return switch (node) {
            case NodeWithName<?> namedNode -> namedNode.getNameAsString();
            case NodeWithSimpleName<?> simpleNamedNode -> simpleNamedNode.getNameAsString();
            default -> null;
        };
    }
    
    public static AttachedType getAttachedType(Node node) {
        return switch (node) {
            case EnumDeclaration ed -> AttachedType.ENUM;
            case ClassOrInterfaceDeclaration cd -> cd.isInterface() ? AttachedType.INTERFACE : AttachedType.CLASS;
            case AnnotationDeclaration ad -> AttachedType.ANNOTATION;
            case ConstructorDeclaration cd -> AttachedType.CONSTRUCTOR;
            case MethodDeclaration md -> AttachedType.METHOD;
            case FieldDeclaration fd -> AttachedType.FIELD;
            case ModuleDeclaration md -> AttachedType.MODULE;
            case PackageDeclaration pd -> AttachedType.PACKAGE;
            default -> {
                LOGGER.warn("Unknown attached type: {}, returning unknown!", node);
                yield AttachedType.UNKNOWN;
            }
        };
    }
}