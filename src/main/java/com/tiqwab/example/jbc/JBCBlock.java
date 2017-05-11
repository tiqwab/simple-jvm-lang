package com.tiqwab.example.jbc;

public class JBCBlock extends JBCNodeBase implements JBCStmt {

    private final JBCStmt stmt;

    public JBCBlock(final JBCStmt stmt) {
        this.stmt = stmt;
    }

    public JBCStmt getStmt() {
        return this.stmt;
    }

    @Override
    public String toString() {
        return String.format("JBCBlock{stmt=%s}", this.stmt);
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        visitor.visit(this);
    }

}
