package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import org.objectweb.asm.MethodVisitor;

public class JBCIf extends JBCNodeBase implements JBCStmt {

    private final JBCExpr expr;
    private final JBCStmt stmt;

    public JBCIf(final JBCExpr expr, final JBCStmt stmt) {
        this.expr = expr;
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return String.format("JBCIf{expr=%s, stmt=%s}", this.expr, this.stmt);
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void genCode(MethodVisitor mv, Environment env) {

    }

}
