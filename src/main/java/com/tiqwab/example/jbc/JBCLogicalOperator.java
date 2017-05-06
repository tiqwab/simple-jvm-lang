package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.symbol.Type;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class JBCLogicalOperator extends JBCExprBase {

    private final String op;
    private final JBCExpr lhs;
    private final JBCExpr rhs;

    public JBCLogicalOperator(final String op, final JBCExpr lhs, final JBCExpr rhs) {
        this.op = op;
        this.lhs = lhs;
        this.rhs= rhs;
    }

    @Override
    public String toString() {
        return String.format("JBCLogicalOperator{op=%s, lhs=%s, rhs=%s}", this.op, this.lhs, this.rhs);
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        this.lhs.accept(visitor);
        this.rhs.accept(visitor);
        visitor.visit(this);
    }

    @Override
    public void genCode(MethodVisitor mv, Environment env) {
        Label labelTrue = new Label();
        Label labelFalse = new Label();

        if (this.lhs.getType() != Type.Bool) {
            throw new IllegalStateException("Expect bool, but: " + this.lhs.getType());
        }
        this.lhs.genCode(mv, env);
        mv.visitJumpInsn(Opcodes.IFEQ, labelFalse);

        if (this.rhs.getType() != Type.Bool) {
            throw new IllegalStateException("Expect bool, but: " + this.rhs.getType());
        }
        this.rhs.genCode(mv, env);
        mv.visitJumpInsn(Opcodes.IFEQ, labelFalse);

        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitJumpInsn(Opcodes.GOTO, labelTrue);
        mv.visitLabel(labelFalse);
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitLabel(labelTrue);
    }

    @Override
    public void calcType(Environment env) {
        this.type = Type.Bool;
    }
}
