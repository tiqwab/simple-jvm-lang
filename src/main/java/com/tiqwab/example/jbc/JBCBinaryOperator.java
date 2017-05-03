package com.tiqwab.example.jbc;

import com.tiqwab.example.symbol.Type;

public class JBCBinaryOperator extends JBCExprBase {

    private final String op;
    private final JBCExpr lhs;
    private final JBCExpr rhs;

    public JBCBinaryOperator(final String op, final JBCExpr lhs, final JBCExpr rhs) {
        this.op = op;
        this.type = Type.max(lhs.getType(), rhs.getType()).orElseThrow(
                () -> new IllegalStateException(String.format("Cannot apply operation. lhs: %s, op: %s, rhs: %s", lhs, op, rhs))
        );
        this.lhs = lhs;
        this.lhs.setWidenedType(this.type);
        this.rhs = rhs;
        this.rhs.setWidenedType(this.type);
    }

    public String getOp() {
        return this.op;
    }

    @Override
    public String toString() {
        return String.format("JBCBinaryOperator{op=%s, lhs=%s, rhs=%s}", this.op, this.lhs, this.rhs);
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        lhs.accept(visitor);
        rhs.accept(visitor);
        visitor.visit(this);
    }

}
