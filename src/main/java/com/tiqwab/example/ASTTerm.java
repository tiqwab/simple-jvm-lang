package com.tiqwab.example;

public class ASTTerm extends ASTNodeBase {

    public ASTTerm(int id) {
        super(id);
    }

    public ASTTerm(Parser p, int id) {
        super(p, id);
    }

    public Object jjtAccept(ParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

}
