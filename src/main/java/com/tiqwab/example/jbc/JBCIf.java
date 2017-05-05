package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.symbol.Type;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class JBCIf extends JBCNodeBase implements JBCStmt {

    private final JBCExpr expr;
    private final JBCStmt stmtTrue;
    private final JBCStmt stmtFalse;

    public JBCIf(final JBCExpr expr, final JBCStmt stmtTrue, final JBCStmt stmtFalse) {
        this.expr = expr;
        this.stmtTrue = stmtTrue;
        this.stmtFalse = stmtFalse;
    }

    @Override
    public String toString() {
        return String.format("JBCIf{expr=%s, stmtTrue=%s, stmtFalse=%s}", this.expr, this.stmtTrue, this.stmtFalse);
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void genCode(MethodVisitor mv, Environment env) {
        this.expr.genCode(mv, env);

        if (this.expr.getType(env) != Type.Bool) {
            throw new IllegalStateException("Expect boolean value but :" + expr.getType(env));
        }

        Label labelTrue = new Label();
        Label labelFalse = new Label();
        mv.visitJumpInsn(Opcodes.IFEQ, labelFalse);
        this.stmtTrue.genCode(mv, env);
        mv.visitJumpInsn(Opcodes.GOTO, labelTrue);
        mv.visitLabel(labelFalse);
        this.stmtFalse.genCode(mv, env);
        mv.visitLabel(labelTrue);
    }

}
