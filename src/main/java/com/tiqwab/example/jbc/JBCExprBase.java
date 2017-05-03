package com.tiqwab.example.jbc;

import com.tiqwab.example.symbol.Type;

public abstract class JBCExprBase extends JBCNodeBase implements JBCExpr {

    private Type widenType;

    @Override
    public Type getWidenedType() {
        return this.widenType;
    }

    @Override
    public void setWidenedType(Type type) {
        this.widenType = type;
    }

}
