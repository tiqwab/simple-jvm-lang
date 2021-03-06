package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.GeneratedCode;
import com.tiqwab.example.Symbol;
import com.tiqwab.example.symbol.Type;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
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
        this.env = null;
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
    public void visit(JBCBlock node) {
        final Environment prev = this.env;
        this.env = new Environment(this.env);

        JBCStmt stmt = node.getStmt();
        stmt.accept(this);
        this.env = prev;
    }

    @Override
    public void visit(JBCSeq node) {
        node.getHead().accept(this);
        node.getTail().ifPresent(seq -> seq.accept(this));
    }

    @Override
    public void visit(JBCAssign node) {
        JBCExpr expr = node.getExpr();
        expr.accept(this);

        Type varType = node.getVarType().orElseGet(() -> {
            return env.get(node.getName())
                    .orElseThrow(() -> new IllegalArgumentException("Type modifier does not appear?"))
                    .getType();
        });

        // Check the consistence of the declared type and actual expression's type
        if (varType != expr.calcType(env)) {
            throw new IllegalStateException(expr.calcType(env) + " cannot be " + varType);
        }
        // Avoid initialize variable twice
        if (node.getVarType().isPresent() && env.exists(node.getName())) {
            throw new IllegalStateException("Variable '" + node.getName() + "' is already declared");
        }

        final Symbol symbol = env.getOrNew(node.getName(), varType);
        mv.visitVarInsn(varType.getStoreCode(), symbol.getIndex());
    }

    @Override
    public void visit(JBCIf node) {
        JBCExpr expr = node.getExpr();
        if (expr.calcType(env) != Type.Bool) {
            throw new IllegalStateException("Expect boolean value but :" + expr.calcType(env));
        }
        expr.accept(this);

        Label labelTrue = new Label();
        Label labelFalse = new Label();
        mv.visitJumpInsn(Opcodes.IFEQ, labelFalse);
        node.getStmtTrue().accept(this);
        mv.visitJumpInsn(Opcodes.GOTO, labelTrue);
        mv.visitLabel(labelFalse);
        node.getStmtFalse().accept(this);
        mv.visitLabel(labelTrue);
    }

    @Override
    public void visit(JBCWhile node) {
        JBCExpr expr = node.getExpr();
        if (expr.calcType(env) != Type.Bool) {
            throw new IllegalStateException("Expect boolean value but :" + expr.calcType(env));
        }

        Label labelStart = new Label();
        mv.visitLabel(labelStart);
        expr.accept(this);

        Label labelBreak = new Label();
        mv.visitJumpInsn(Opcodes.IFEQ, labelBreak);
        node.getStmt().accept(this);
        mv.visitJumpInsn(Opcodes.GOTO, labelStart);
        mv.visitLabel(labelBreak);
    }

    @Override
    public void visit(JBCEval node) {
        node.getExpr().accept(this);
    }

    @Override
    public void visit(JBCBinaryOperator node) {
        JBCExpr lhs = node.getLhs();
        JBCExpr rhs = node.getRhs();
        Type type = node.calcType(env);

        lhs.accept(this);
        Type.widen(mv, lhs.calcType(env), type);

        rhs.accept(this);
        Type.widen(mv, rhs.calcType(env), type);

        if (node.getOp().equals("+")) {
            mv.visitInsn(type.getAddCode());
        } else if (node.getOp().equals("-")) {
            mv.visitInsn(type.getSubCode());
        } else if (node.getOp().equals("*")) {
            mv.visitInsn(type.getMulCode());
        } else if (node.getOp().equals("/")) {
            mv.visitInsn(type.getDivCode());
        } else {
            throw new IllegalArgumentException("unknown op: " + node.getOp());
        }
    }

    @Override
    public void visit(JBCRelOperator node) {
        JBCExpr lhs = node.getLhs();
        JBCExpr rhs = node.getRhs();

        Type widenedType = Type.max(lhs.calcType(env), rhs.calcType(env)).orElseThrow(
                () -> new IllegalStateException(String.format("Cannot apply operation. lhs: %s, op: %s, rhs: %s", lhs, node.getOp(), rhs))
        );

        lhs.accept(this);
        Type.widen(mv, lhs.calcType(env), widenedType);

        rhs.accept(this);
        Type.widen(mv, rhs.calcType(env), widenedType);

        // FIXME
        if (node.getOp().equals("==")) {
            widenedType.genEQCode(mv);
        } else if (node.getOp().equals("<")) {
            widenedType.genLTCode(mv);
        } else {
            throw new IllegalStateException("Illegal op: " + node.getOp());
        }
    }

    @Override
    public void visit(JBCLogicalOperator node) {
        JBCExpr lhs = node.getLhs();
        JBCExpr rhs = node.getRhs();

        Label labelTrue = new Label();
        Label labelFalse = new Label();

        if (lhs.calcType(env) != Type.Bool) {
            throw new IllegalStateException("Expect bool, but: " + lhs.calcType(env));
        }
        lhs.accept(this);
        mv.visitJumpInsn(Opcodes.IFEQ, labelFalse);

        if (rhs.calcType(env) != Type.Bool) {
            throw new IllegalStateException("Expect bool, but: " + rhs.calcType(env));
        }
        rhs.accept(this);
        mv.visitJumpInsn(Opcodes.IFEQ, labelFalse);

        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitJumpInsn(Opcodes.GOTO, labelTrue);
        mv.visitLabel(labelFalse);
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitLabel(labelTrue);
    }

    @Override
    public void visit(JBCInteger node) {
        node.calcType(env);

        final int value = node.getValue();
        // The generated code is determined by the necessary size (byte) of integer.
        if (-128 <= value && value < 128) {
            mv.visitIntInsn(Opcodes.BIPUSH, value);
        } else if (-32768 <= value && value < 32768){
            mv.visitIntInsn(Opcodes.SIPUSH, value);
        } else {
            mv.visitLdcInsn(value);
        }
    }

    @Override
    public void visit(JBCFloat node) {
        node.calcType(env);
        mv.visitLdcInsn(new Float(node.getValue()));
    }

    @Override
    public void visit(JBCId node) {
        node.calcType(env);
        final Symbol symbol = env.get(node.getName()).orElseThrow(
                () -> new IllegalStateException(String.format("Cannot resolve symbol '%s'", node.getName()))
        );
        mv.visitVarInsn(symbol.getType().getLoadCode(), symbol.getIndex());
    }

    @Override
    public void visit(JBCTrue node) {
        node.calcType(env);
        mv.visitIntInsn(Opcodes.BIPUSH, 1);
    }

    @Override
    public void visit(JBCFalse node) {
        node.calcType(env);
        mv.visitIntInsn(Opcodes.BIPUSH, 0);
    }

    @Override
    public void visit(JBCNot node) {
        node.calcType(env);

        JBCExpr expr = node.getExpr();
        if (expr.calcType(env) != Type.Bool) {
            throw new IllegalStateException("Expect boolean value but :" + expr.calcType(env));
        }
        expr.accept(this);

        Label labelTrue = new Label();
        Label labelFalse = new Label();
        mv.visitJumpInsn(Opcodes.IFEQ, labelFalse);
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitJumpInsn(Opcodes.GOTO, labelTrue);
        mv.visitLabel(labelFalse);
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitLabel(labelTrue);
    }

}
