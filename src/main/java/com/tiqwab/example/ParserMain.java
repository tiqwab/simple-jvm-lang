package com.tiqwab.example;

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
        JavaBytecodeGenerationVisitor codeGenerationVisitor = new JavaBytecodeGenerationVisitor();
        node.jjtAccept(codeGenerationVisitor, null);
        new GeneratedCodeOutputter().output(codeGenerationVisitor.getGeneratedCode());
        System.out.println();

        ASTNodeBase astNode = (ASTNodeBase) node;
        System.out.println(astNode.getJbcNode().toString());
    }

}
