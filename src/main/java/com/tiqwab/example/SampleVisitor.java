package com.tiqwab.example;

public class SampleVisitor implements ParserVisitor {

    @Override
    public Object visit(SimpleNode node, Object data) {
        System.out.println(node.toString());
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTStart node, Object data) {
        System.out.println("Start!");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTStatement node, Object data) {
        System.out.println("Statement:");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTExpression node, Object data) {
        System.out.println("Expression!");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTTerm node, Object data) {
        System.out.println("Term!");
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTFactor node, Object data) {
        System.out.println("Factor!");
        node.childrenAccept(this, data);
        return data;
    }

}
