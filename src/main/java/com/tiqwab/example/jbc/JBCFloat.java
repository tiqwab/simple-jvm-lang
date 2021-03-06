package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.symbol.Type;

public class JBCFloat extends JBCExprBase {

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

    @Override
    public Type calcType(Environment env) {
        if (this.type == null) {
            this.type = Type.Float;
        }
        return this.type;
    }

}
