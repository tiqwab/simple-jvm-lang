package com.tiqwab.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    public Optional<Symbol> get(final String name) {
        Symbol symbol = env.get(name);
        if (symbol == null) {
            return Optional.empty();
        }
        return Optional.of(symbol);
    }

    public Symbol getOrNew(final String name) {
        Symbol symbol = env.get(name);
        if (symbol == null) {
            symbol = new Symbol(index++);
            this.env.put(name, symbol);
        }
        return symbol;
    }

}
