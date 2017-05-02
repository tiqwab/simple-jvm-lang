package com.tiqwab.example.jbc;

import com.tiqwab.example.symbol.Type;

public class JBCAssign extends JBCNodeBase implements JBCStmt {

    private final String name;
    private final JBCExpr expr;

    public JBCAssign(final String name, final JBCExpr expr) {
        this.name = name;
        this.expr = expr;
        this.type = Type.Void;
    }

    @Override
    public String toString() {
        return String.format("JBCAssign{name=%s, expr=%s}", this.name, this.expr);
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        this.expr.accept(visitor);
        visitor.visit(this);
    }

    public String getName() {
        return this.name;
    }

}
