package com.tiqwab.example.jbc;

public class JBCFloat implements JBCExpr {

    private final float value;

    public JBCFloat(final String value) {
        this.value = Float.parseFloat(value);
    }

    public float getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.format("JBCFloat{value=%s}", value);
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        visitor.visit(this);
    }

}
