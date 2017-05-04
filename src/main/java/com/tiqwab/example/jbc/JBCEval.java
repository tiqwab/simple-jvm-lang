package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import org.objectweb.asm.MethodVisitor;

public class JBCEval extends JBCNodeBase implements JBCStmt {

    private final JBCExpr expr;

    public JBCEval(final JBCExpr expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        return String.format("JBCEval{expr=%s}", this.expr.toString());
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        expr.accept(visitor);
        visitor.visit(this);
    }

    @Override
    public void genCode(MethodVisitor mv, Environment env) {
        this.expr.genCode(mv, env);
    }

}
