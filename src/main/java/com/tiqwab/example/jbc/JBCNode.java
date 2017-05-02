package com.tiqwab.example.jbc;

import com.tiqwab.example.symbol.Type;

public interface JBCNode {

    public void accept(JBCNodeVisitor visitor);
    public Type getType();
    public void setType(Type type);

}
