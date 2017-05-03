package com.tiqwab.example.jbc;

import com.tiqwab.example.symbol.Type;

public interface JBCExpr extends JBCNode {

    public Type getWidenedType();
    public void setWidenedType(Type type);

}
