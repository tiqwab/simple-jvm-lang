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

    @Override
    public String toString() {
        return String.format("JBCBinaryOperator{op=%s, lhs=%s, rhs=%s}", this.op, this.lhs, this.rhs);
    }

    @Override
    public Type getType(Environment env) {
        if (this.type == null) {
            this.type = Type.max(lhs.getType(env), rhs.getType(env)).orElseThrow(
                    () -> new IllegalStateException(String.format("Cannot apply operation. lhs: %s, op: %s, rhs: %s", lhs, op, rhs))
            );
        }
        return this.type;
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        lhs.accept(visitor);
        rhs.accept(visitor);
        visitor.visit(this);
    }

    @Override
    public void genCode(MethodVisitor mv, Environment env) {
        this.lhs.setWidenedType(this.getType(env));
        this.lhs.genCode(mv, env);
        this.rhs.setWidenedType(this.getType(env));
        this.rhs.genCode(mv, env);

        Type type = this.getType(env);
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

}
