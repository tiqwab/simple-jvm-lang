package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.symbol.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * False is an integer 0 in JavaVM.
 */
public class JBCFalse extends JBCExprBase {

    public JBCFalse() {

    }

    @Override
    public String toString() {
        return String.format("JBCFalse{}");
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void genCode(MethodVisitor mv, Environment env) {
        mv.visitIntInsn(Opcodes.BIPUSH, 0);
    }

    @Override
    public Type calcType(Environment env) {
        if (this.type == null) {
            this.type = Type.Bool;
        }
        return this.type;
    }

}

