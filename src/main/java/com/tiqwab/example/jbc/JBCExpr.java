package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;

public interface JBCExpr extends JBCNode {

    /**
     * Calculate type of the expression.
     * <p>
     * This can be calculated in the parsing step if there is a symbol table.
     * The current implementation does not use it in the parsing, so have to calculate type before the generation of byte codes.
     * @param env
     */
    void calcType(Environment env);

}
