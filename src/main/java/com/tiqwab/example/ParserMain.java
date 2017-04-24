package com.tiqwab.example;

public class ParserMain {

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser(System.in);
        SimpleNode node = parser.start();
        node.dump("");
    }
}
