package com.tiqwab.example.jbc;

public class JBCEval extends JBCNodeBase implements JBCStmt {

    private final JBCExpr expr;

    public JBCEval(final JBCExpr expr) {
        this.expr = expr;
    }

    public JBCExpr getExpr() {
        return this.expr;
    }

    @Override
    public String toString() {
        return String.format("JBCEval{expr=%s}", this.expr.toString());
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        visitor.visit(this);
    }

}
