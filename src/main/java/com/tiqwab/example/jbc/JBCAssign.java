package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.Symbol;
import com.tiqwab.example.symbol.Type;
import org.objectweb.asm.MethodVisitor;

import java.util.Optional;

public class JBCAssign extends JBCNodeBase implements JBCStmt {

    private final String name;
    private final Optional<Type> varType;
    private final JBCExpr expr;

    public JBCAssign(final String name, final JBCExpr expr) {
        this(name, null, expr);
    }

    public JBCAssign(final String name, final String typeModifier, final JBCExpr expr) {
        this.name = name;
        this.expr = expr;
        this.varType = typeModifier != null ? Optional.of(Type.of(typeModifier)) : Optional.empty();
        this.type = Type.Void;
    }

    @Override
    public String toString() {
        return String.format("JBCAssign{name=%s, varType=%s, expr=%s}", this.name, this.varType, this.expr);
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        this.expr.accept(visitor);
        visitor.visit(this);
    }

    @Override
    public void genCode(MethodVisitor mv, Environment env) {
        this.expr.genCode(mv, env);

        Type varType = this.getVarType().orElseThrow(() -> new IllegalArgumentException("Type modifier does not appear?"));
        if (varType != this.expr.getType(env)) {
            throw new IllegalStateException(this.expr.getType(env) + " cannot be " + varType);
        }
        final Symbol symbol = env.getOrNew(this.getName(), varType);
        mv.visitVarInsn(varType.getStoreCode(), symbol.getIndex());
    }

    public String getName() {
        return this.name;
    }

    public Optional<Type> getVarType() {
        return this.varType;
    }

}
