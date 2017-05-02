package com.tiqwab.example.jbc;

import com.tiqwab.example.symbol.Type;

public abstract class JBCNodeBase implements JBCNode {

    protected Type type;

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

}
