package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.symbol.Type;
import org.objectweb.asm.MethodVisitor;

public class JBCFloat extends JBCExprBase {

    private final float value;

    public JBCFloat(final String value) {
        this.value = Float.parseFloat(value);
        this.type = Type.Float;
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

    // TODO: Check how to apply float type
    @Override
    public void genCode(MethodVisitor mv, Environment env) {
        mv.visitLdcInsn(new Float(this.getValue()));
        Type.widen(mv, this.getType(env), this.getWidenedType());
    }

}