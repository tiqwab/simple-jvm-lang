package com.tiqwab.example;

import java.util.HashMap;
import java.util.Map;

public class Environment {

    private Map<String, Symbol> env = new HashMap<>();
    private int index;

    // TODO: Is it correct to always initialize index as zero? (maybe not)
    public Environment() {
        index = 0;
    }

    public void put(final String name, final Symbol symbol) {
        this.env.put(name, symbol);
    }

    public Symbol get(final String name) {
        return this.env.getOrDefault(name, new Symbol(index++));
    }

}
