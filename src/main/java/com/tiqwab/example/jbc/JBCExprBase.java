package com.tiqwab.example.jbc;

import com.tiqwab.example.symbol.Type;

public abstract class JBCExprBase extends JBCNodeBase implements JBCExpr {

    protected Type type;

    @Override
    public Type getType() {
        return this.type;
    }

}
