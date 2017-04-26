package com.tiqwab.example;

public class ASTExpression extends ASTNodeBase {

    public ASTExpression(int id) {
        super(id);
    }

    public ASTExpression(Parser p, int id) {
        super(p, id);
    }

    public Object jjtAccept(ParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

}
