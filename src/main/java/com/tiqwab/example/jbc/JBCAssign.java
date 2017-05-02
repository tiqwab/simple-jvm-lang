package com.tiqwab.example.jbc;

import com.tiqwab.example.symbol.Type;

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

    public String getName() {
        return this.name;
    }

}
