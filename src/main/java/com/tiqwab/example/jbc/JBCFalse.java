package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import org.objectweb.asm.MethodVisitor;

public class JBCFalse extends JBCExprBase {

    public JBCFalse() {

    }

    @Override
    public String toString() {
        return String.format("JBCFalse{}");
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {

    }

    @Override
    public void genCode(MethodVisitor mv, Environment env) {

    }

}

