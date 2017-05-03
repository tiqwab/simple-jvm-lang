package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.symbol.Type;
import org.objectweb.asm.MethodVisitor;

public interface JBCNode {

    public void accept(JBCNodeVisitor visitor);
    public void genCode(MethodVisitor mv, Environment env);
    public Type getType(Environment env);

}
