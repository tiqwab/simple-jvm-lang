package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.symbol.Type;

public class JBCLogicalOperator extends JBCExprBase {

    private final String op;
    private final JBCExpr lhs;
    private final JBCExpr rhs;

    public JBCLogicalOperator(final String op, final JBCExpr lhs, final JBCExpr rhs) {
        this.op = op;
        this.lhs = lhs;
        this.rhs= rhs;
    }

    public JBCExpr getLhs() {
        return this.lhs;
    }

    public JBCExpr getRhs() {
        return this.rhs;
    }

    @Override
    public String toString() {
        return String.format("JBCLogicalOperator{op=%s, lhs=%s, rhs=%s}", this.op, this.lhs, this.rhs);
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Type calcType(Environment env) {
        if (this.type == null) {
            this.type = Type.Bool;
        }
        return this.type;
    }
}
