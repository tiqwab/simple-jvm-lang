package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.Symbol;
import com.tiqwab.example.symbol.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class JBCId extends JBCExprBase {

    private final String name;

    public JBCId(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public Type getType(Environment env) {
        if (this.type == null) {
            final Symbol symbol = env.get(this.name).orElseThrow(
                    () -> new IllegalStateException("Cannot resolve: " + this.name)
            );
            this.type = symbol.getType();
        }
        return this.type;
    }

    @Override
    public String toString() {
        return String.format("JBCId{name=%s}", this.name);
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void genCode(MethodVisitor mv, Environment env) {
        final Symbol symbol = env.get(this.getName()).orElseThrow(
                () -> new IllegalStateException(String.format("Cannot resolve symbol '%s'", this.getName()))
        );
        mv.visitVarInsn(symbol.getType().getLoadCode(), symbol.getIndex());
    }

    @Override
    public void calcType(Environment env) {
        final Symbol symbol = env.get(this.getName()).orElseThrow(
                () -> new IllegalStateException(String.format("Cannot resolve symbol '%s'", this.getName()))
        );
        this.type = symbol.getType();
    }

}
