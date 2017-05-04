package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.symbol.Type;

public abstract class JBCNodeBase implements JBCNode {

    protected Type type;

    @Override
    public Type getType(Environment env) {
        return this.type;
    }

}
