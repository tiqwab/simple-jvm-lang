package com.tiqwab.example.jbc;

public interface JBCNodeVisitor {

    public void visit(JBCSeq node);
    public void visit(JBCAssign node);
    public void visit(JBCEval node);
    public void visit(JBCBinaryOperator node);
    public void visit(JBCInteger node);
    public void visit(JBCFloat node);
    public void visit(JBCId node);
    public void visit(JBCTrue node);
    public void visit(JBCFalse node);
    public void visit(JBCNot node);

}
