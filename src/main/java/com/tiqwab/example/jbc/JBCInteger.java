package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.symbol.Type;

public class JBCInteger extends JBCExprBase {

    private final int value;

    public JBCInteger(final String value) {
        this.value = Integer.parseInt(value);
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

    @Override
    public Type calcType(Environment env) {
        if (this.type == null) {
            this.type = Type.Int;
        }
        return this.type;
    }

}
