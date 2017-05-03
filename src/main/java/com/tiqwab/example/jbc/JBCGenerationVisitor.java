package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.GeneratedCode;
import com.tiqwab.example.Symbol;
import com.tiqwab.example.symbol.Type;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class JBCGenerationVisitor implements JBCNodeVisitor {

    private ClassWriter classWriter;
    private String generatedClassName;
    private GeneratedCode generatedCode;
    private Environment env;

    private MethodVisitor mv;

    public JBCGenerationVisitor() {
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
        this.env = new Environment();
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

        // Print calculation result
        mv.visitFieldInsn(
                Opcodes.GETSTATIC,
                "java/lang/System",
                "out",
                "Ljava/io/PrintStream;"
        );
        mv.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                "Calculation",
                "calculate",
                "()I",
                false
        );
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "java/io/PrintStream",
                "println",
                "(I)V",
                false
        );
        mv.visitInsn(Opcodes.RETURN);

        mv.visitMaxs(0,0);
        mv.visitEnd();
    }

    public void generateCode(JBCNode node) {
        this.preVisit();
        this.visitConstructor();
        this.visitMain();

        // Start creation of method to calculate
        mv = classWriter.visitMethod(
                Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
                "calculate",
                "()I",
                null,
                null
        );
        mv.visitCode();

        // Generate java byte code to calculate expression
        node.accept(this);

        // Finish creation
        mv.visitInsn(Opcodes.IRETURN);
        mv.visitMaxs(0,0);
        mv.visitEnd();

        this.generatedCode = new GeneratedCode(this.classWriter.toByteArray(), this.generatedClassName);
    }

    @Override
    public void visit(JBCSeq node) {

    }

    @Override
    public void visit(JBCAssign node) {
        node.genCode(mv, env);
    }

    @Override
    public void visit(JBCEval node) {
        node.genCode(mv, env);
    }

    @Override
    public void visit(JBCBinaryOperator node) {
    }

    @Override
    public void visit(JBCInteger node) {
    }

    @Override
    public void visit(JBCFloat node) {
    }

    @Override
    public void visit(JBCId node) {
    }

}
