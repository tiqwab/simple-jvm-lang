package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.symbol.Type;

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
    public Type calcType(Environment env) {
        if (this.type == null) {
            this.type = Type.max(lhs.calcType(env), rhs.calcType(env)).orElseThrow(
                    () -> new IllegalStateException(String.format("Cannot apply operation. lhs: %s, op: %s, rhs: %s", lhs, op, rhs))
            );
        }
        return this.type;
    }

}
