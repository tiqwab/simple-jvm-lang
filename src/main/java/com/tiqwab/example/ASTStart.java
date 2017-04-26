package com.tiqwab.example;

public class ASTStart extends ASTNodeBase {

    public ASTStart(int id) {
        super(id);
    }

    public ASTStart(Parser p, int id) {
        super(p, id);
    }

    public Object jjtAccept(ParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

}
