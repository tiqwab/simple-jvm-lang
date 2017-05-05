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
        // Check the consistence of the declared type and actual expression's type
        if (varType != this.expr.getType()) {
            throw new IllegalStateException(this.expr.getType() + " cannot be " + varType);
        }
        // Avoid initialize variable twice
        if (env.exists(this.getName())) {
            throw new IllegalStateException("Variable '" + this.getName() + "' is already declared");
        }
        final Symbol symbol = env.newSymbol(this.getName(), varType);
        mv.visitVarInsn(varType.getStoreCode(), symbol.getIndex());
    }

    public String getName() {
        return this.name;
    }

    public Optional<Type> getVarType() {
        return this.varType;
    }

}
