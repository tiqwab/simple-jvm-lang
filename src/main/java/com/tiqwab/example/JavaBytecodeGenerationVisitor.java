package com.tiqwab.example;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Visitor class for AST to generate java byte codes.
 * This class depends on 'org.ow2.asm' libraries.
 */
public class JavaBytecodeGenerationVisitor implements ParserVisitor {

    private ClassWriter classWriter;
    private String generatedClassName;
    private GeneratedCode generatedCode;

    public JavaBytecodeGenerationVisitor() {
        this.generatedClassName = "Calculation";
    }

    public GeneratedCode getGeneratedCode() {
        return new GeneratedCode(this.generatedCode);
    }

    private void preVisit() {
        this.classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        this.classWriter.visit(
                Opcodes.V1_8,
                Opcodes.ACC_PUBLIC | Opcodes.ACC_SUPER,
                generatedClassName,
                null,
                "java/lang/Object",
                null
        );
    }

    /**
     * Create a default constructor.
     */
    private void visitConstructor() {
        MethodVisitor mv = classWriter.visitMethod(
                Opcodes.ACC_PUBLIC,
                "<init>",
                "()V",
                null,
                null
        );
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(
                Opcodes.INVOKESPECIAL,
                "java/lang/Object",
                "<init>",
                "()V",
                false
        );
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0,0);
        mv.visitEnd();
    }

    /**
     * Create a 'main' method. This is for demonstration.
     */
    private void visitMain() {
        MethodVisitor mv = classWriter.visitMethod(
                Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
                "main",
                "([Ljava/lang/String;)V",
                null,
                null
        );
        mv.visitCode();
        mv.visitFieldInsn(
                Opcodes.GETSTATIC,
                "java/lang/System",
                "out",
                "Ljava/io/PrintStream;"
        );
        mv.visitLdcInsn("hello ASM");
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "java/io/PrintStream",
                "println",
                "(Ljava/lang/String;)V",
                false
        );
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0,0);
        mv.visitEnd();
    }

    @Override
    public Object visit(SimpleNode node, Object data) {
        throw new IllegalStateException("this node should not be visited.");
    }

    /**
     * Visit a node representing the starting rule.
     * @param node
     * @param data
     * @return
     */
    @Override
    public Object visit(ASTStart node, Object data) {
        this.preVisit();
        this.visitConstructor();
        this.visitMain();
        this.generatedCode = new GeneratedCode(this.classWriter.toByteArray(), this.generatedClassName);
        return data;
    }

    @Override
    public Object visit(ASTExpression node, Object data) {
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTTerm node, Object data) {
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTFactor node, Object data) {
        node.childrenAccept(this, data);
        return data;
    }

}
