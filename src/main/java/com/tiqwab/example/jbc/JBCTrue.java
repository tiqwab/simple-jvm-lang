package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.symbol.Type;

/**
 * True is an integer 1 in JavaVM.
 */
public class JBCTrue extends JBCExprBase {

    public JBCTrue() {

    }

    @Override
    public String toString() {
        return String.format("JBCTrue{}");
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Type calcType(Environment env) {
        if (this.type == null) {
            this.type = Type.Bool;
        }
        return this.type;
    }

}
