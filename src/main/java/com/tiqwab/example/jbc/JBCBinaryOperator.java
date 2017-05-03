package com.tiqwab.example.jbc;

import com.tiqwab.example.symbol.Type;

public class JBCBinaryOperator extends JBCNodeBase implements JBCExpr {

    private final String op;
    private final JBCExpr lhs;
    private final JBCExpr rhs;

    public JBCBinaryOperator(final String op, final JBCExpr lhs, final JBCExpr rhs) {
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
        this.type = Type.max(lhs.getType(), rhs.getType()).orElseThrow(
                () -> new IllegalStateException(String.format("Cannot apply operation. lhs: %s, op: %s, rhs: %s", lhs, op, rhs))
        );
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
