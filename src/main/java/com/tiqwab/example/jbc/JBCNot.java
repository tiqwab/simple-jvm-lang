package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.symbol.Type;

public class JBCNot extends JBCExprBase {

    private final JBCExpr expr;

    public JBCNot(final JBCExpr expr) {
        this.expr = expr;
    }

    public JBCExpr getExpr() {
        return this.expr;
    }

    @Override
    public String toString() {
        return String.format("JBCNot{expr=%s}", this.expr);
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
