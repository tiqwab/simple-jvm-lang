package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import org.objectweb.asm.MethodVisitor;

public class JBCNot extends JBCExprBase {

    private final JBCExpr expr;

    public JBCNot(final JBCExpr expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        return String.format("JBCNot{expr=%s}", this.expr);
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {

    }

    @Override
    public void genCode(MethodVisitor mv, Environment env) {

    }

}
