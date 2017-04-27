package com.tiqwab.example;

import com.tiqwab.example.jbc.JBCNode;

public class ASTNodeBase extends SimpleNode {

    private JBCNode jbcNode;

    public ASTNodeBase(Parser p, int i) {
        super(p, i);
    }

    public ASTNodeBase(int i) {
        super(i);
    }

    public JBCNode getJbcNode() {
        return this.jbcNode;
    }

    public void setJbcNode(JBCNode jbcNode) {
        this.jbcNode = jbcNode;
    }

}
