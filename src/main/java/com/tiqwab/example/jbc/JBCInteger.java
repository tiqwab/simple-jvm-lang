package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.symbol.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

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
    public void genCode(MethodVisitor mv, Environment env) {
        final int value = this.getValue();
        // The generated code is determined by the necessary size (byte) of integer.
        if (-128 <= value && value < 128) {
            mv.visitIntInsn(Opcodes.BIPUSH, value);
        } else if (-32768 <= value && value < 32768){
            mv.visitIntInsn(Opcodes.SIPUSH, value);
        } else {
            mv.visitLdcInsn(value);
        }
    }

    @Override
    public Type calcType(Environment env) {
        if (this.type == null) {
            this.type = Type.Int;
        }
        return this.type;
    }

}
