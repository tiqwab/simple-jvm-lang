package com.tiqwab.example.jbc;

import com.tiqwab.example.symbol.Type;

public class JBCInteger extends JBCNodeBase implements JBCExpr {

    private final int value;

    public JBCInteger(final String value) {
        this.value = Integer.parseInt(value);
        this.type = Type.Int;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.format("JBCInteger{value=%s}", value);
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        visitor.visit(this);
    }

}
