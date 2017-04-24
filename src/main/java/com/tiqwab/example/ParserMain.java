package com.tiqwab.example;

public class ParserMain {

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser(System.in);
        SimpleNode node = parser.start();

        System.out.println("Dump...");
        node.dump("");
        System.out.println();

        System.out.println("Visit...");
        node.jjtAccept(new SampleVisitor(), null);
    }
}
