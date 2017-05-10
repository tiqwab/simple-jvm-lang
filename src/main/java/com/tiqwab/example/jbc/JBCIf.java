package com.tiqwab.example.jbc;

public class JBCIf extends JBCNodeBase implements JBCStmt {

    private final JBCExpr expr;
    private final JBCStmt stmtTrue;
    private final JBCStmt stmtFalse;

    public JBCIf(final JBCExpr expr, final JBCStmt stmtTrue, final JBCStmt stmtFalse) {
        this.expr = expr;
        this.stmtTrue = stmtTrue;
        this.stmtFalse = stmtFalse;
    }

    public JBCExpr getExpr() {
        return this.expr;
    }

    public JBCStmt getStmtTrue() {
        return this.stmtTrue;
    }

    public JBCStmt getStmtFalse() {
        return this.stmtFalse;
    }

    @Override
    public String toString() {
        return String.format("JBCIf{expr=%s, stmtTrue=%s, stmtFalse=%s}", this.expr, this.stmtTrue, this.stmtFalse);
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        visitor.visit(this);
    }

}
