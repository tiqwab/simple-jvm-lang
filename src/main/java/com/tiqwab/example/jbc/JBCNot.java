package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.symbol.Type;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class JBCNot extends JBCExprBase {

    private final JBCExpr expr;

    public JBCNot(final JBCExpr expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        return String.format("JBCNot{expr=%s}", this.expr);
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        this.expr.accept(visitor);
        visitor.visit(this);
    }

    @Override
    public void genCode(MethodVisitor mv, Environment env) {
        this.expr.genCode(mv, env);

        if (expr.getType() != Type.Bool) {
            throw new IllegalStateException("Expect boolean value but :" + expr.getType());
        }
        Label labelTrue = new Label();
        Label labelFalse = new Label();
        mv.visitJumpInsn(Opcodes.IFEQ, labelFalse);
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitJumpInsn(Opcodes.GOTO, labelTrue);
        mv.visitLabel(labelFalse);
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitLabel(labelTrue);
    }

    @Override
    public void calcType(Environment env) {
        this.type = Type.Bool;
    }

}
