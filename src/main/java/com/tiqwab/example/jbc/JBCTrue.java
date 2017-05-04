package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import org.objectweb.asm.MethodVisitor;

public class JBCTrue extends JBCExprBase {

    public JBCTrue() {

    }

    @Override
    public String toString() {
        return String.format("JBCTrue{}");
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {

    }

    @Override
    public void genCode(MethodVisitor mv, Environment env) {

    }

}
