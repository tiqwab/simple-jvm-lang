package com.tiqwab.example;

public class SampleVisitor implements ParserVisitor {

    @Override
    public Object visit(SimpleNode node, Object data) {
        System.out.println(node.toString());
        node.childrenAccept(this, data);
        return data;
    }

}
