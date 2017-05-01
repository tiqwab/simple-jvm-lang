package com.tiqwab.example.jbc;

public class JBCId implements JBCExpr {

    private final String name;

    public JBCId(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format("JBCId{name=%s}", this.name);
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        visitor.visit(this);
    }

}
