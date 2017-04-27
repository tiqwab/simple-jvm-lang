package com.tiqwab.example;

import com.tiqwab.example.jbc.JBCGenerationVisitor;
import com.tiqwab.example.jbc.JBCNode;

public class ParserMain {

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser(System.in);
        SimpleNode node = parser.Start();

        System.out.println("Dump...");
        node.dump("");
        System.out.println();

        System.out.println("Visit...");
        node.jjtAccept(new SampleVisitor(), null);
        System.out.println();

        System.out.println("Generate java bytecodes...");
        ASTNodeBase astNode = (ASTNodeBase) node;
        JBCNode jbcNode = astNode.getJbcNode();
        System.out.println(jbcNode.toString());
        JBCGenerationVisitor jbcGenerationVisitor = new JBCGenerationVisitor();
        jbcNode.accept(jbcGenerationVisitor);
        new GeneratedCodeOutputter().output(jbcGenerationVisitor.getGeneratedCode());
    }

}
