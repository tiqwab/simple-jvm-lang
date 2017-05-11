package com.tiqwab.example.jbc;

public class JBCWhile extends JBCNodeBase implements JBCStmt {

    private final JBCExpr expr;
    private final JBCStmt stmt;

    public JBCWhile(final JBCExpr expr, final JBCStmt stmt) {
        this.expr = expr;
        this.stmt = stmt;
    }

    public JBCExpr getExpr() {
        return this.expr;
    }

    public JBCStmt getStmt() {
        return this.stmt;
    }

    @Override
    public String toString() {
        return String.format("JBCWhile{expr=%s, stmt=%s}", this.expr, this.stmt);
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        visitor.visit(this);
    }

}
