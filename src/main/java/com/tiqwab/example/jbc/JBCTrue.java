package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.symbol.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

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
    public void genCode(MethodVisitor mv, Environment env) {
        mv.visitIntInsn(Opcodes.BIPUSH, 1);
    }

    @Override
    public Type calcType(Environment env) {
        if (this.type == null) {
            this.type = Type.Bool;
        }
        return this.type;
    }

}
