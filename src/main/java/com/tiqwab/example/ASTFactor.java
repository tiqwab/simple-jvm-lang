package com.tiqwab.example;

public class ASTFactor extends ASTNodeBase {

    public ASTFactor(int id) {
        super(id);
    }

    public ASTFactor(Parser p, int id) {
        super(p, id);
    }

    public Object jjtAccept(ParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

}
