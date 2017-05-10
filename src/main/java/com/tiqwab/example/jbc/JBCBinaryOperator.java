package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.Symbol;
import com.tiqwab.example.symbol.Type;
import org.objectweb.asm.MethodVisitor;

public class JBCBinaryOperator extends JBCExprBase {

    private final String op;
    private final JBCExpr lhs;
    private final JBCExpr rhs;

    public JBCBinaryOperator(final String op, final JBCExpr lhs, final JBCExpr rhs) {
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public String getOp() {
        return this.op;
    }
    public JBCExpr getLhs() {
        return this.lhs;
    }
    public JBCExpr getRhs() {
        return this.rhs;
    }

    @Override
    public String toString() {
        return String.format("JBCBinaryOperator{op=%s, lhs=%s, rhs=%s}", this.op, this.lhs, this.rhs);
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void genCode(MethodVisitor mv, Environment env) {
        Type type = this.getType();

        this.lhs.genCode(mv, env);
        Type.widen(mv, this.lhs.getType(), this.getType());

        this.rhs.genCode(mv, env);
        Type.widen(mv, this.rhs.getType(), this.getType());

        if (this.getOp().equals("+")) {
            mv.visitInsn(type.getAddCode());
        } else if (this.getOp().equals("-")) {
            mv.visitInsn(type.getSubCode());
        } else if (this.getOp().equals("*")) {
            mv.visitInsn(type.getMulCode());
        } else if (this.getOp().equals("/")) {
            mv.visitInsn(type.getDivCode());
        } else {
            throw new IllegalArgumentException("unknown op: " + this.getOp());
        }
    }

    @Override
    public Type calcType(Environment env) {
        if (this.type == null) {
            this.type = Type.max(lhs.calcType(env), rhs.calcType(env)).orElseThrow(
                    () -> new IllegalStateException(String.format("Cannot apply operation. lhs: %s, op: %s, rhs: %s", lhs, op, rhs))
            );
        }
        return this.type;
    }

}
