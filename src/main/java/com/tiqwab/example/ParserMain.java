package com.tiqwab.example;

import com.tiqwab.example.jbc.JBCGenerationVisitor;
import com.tiqwab.example.jbc.JBCNode;

public class ParserMain {

    public static void main(String[] args) throws Exception {
        System.out.println();

        Parser parser = new Parser(System.in);
        SimpleNode node = parser.Start();

        ASTNodeBase astNode = (ASTNodeBase) node;
        JBCNode jbcNode = astNode.getJbcNode();
        System.out.println(jbcNode.toString());
        JBCGenerationVisitor jbcGenerationVisitor = new JBCGenerationVisitor();
        jbcGenerationVisitor.generateCode(jbcNode);
        new GeneratedCodeOutputter().output(jbcGenerationVisitor.getGeneratedCode());
    }

}
