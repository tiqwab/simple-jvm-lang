package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.symbol.Type;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class JBCRelOperator extends JBCExprBase {

    private final String op;
    private final JBCExpr lhs;
    private final JBCExpr rhs;

    public JBCRelOperator(final String op, final JBCExpr lhs, final JBCExpr rhs) {
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
        this.type = Type.Bool;
    }

    @Override
    public String toString() {
        return String.format("JBCRelOperator{op=%s, lhs=%s, rhs=%s", this.op, this.lhs, this.rhs);
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void genCode(MethodVisitor mv, Environment env) {
        Type actualType = Type.max(lhs.getType(env), rhs.getType(env)).orElseThrow(
                () -> new IllegalStateException(String.format("Cannot apply operation. lhs: %s, op: %s, rhs: %s", lhs, op, rhs))
        );
        this.lhs.setWidenedType(actualType);
        this.lhs.genCode(mv, env);
        this.rhs.setWidenedType(actualType);
        this.rhs.genCode(mv, env);

        Label labelTrue = new Label();
        Label labelFalse = new Label();
        mv.visitJumpInsn(Opcodes.IF_ICMPLT, labelTrue);
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitJumpInsn(Opcodes.GOTO, labelFalse);
        mv.visitLabel(labelTrue);
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitLabel(labelFalse);
    }
}
