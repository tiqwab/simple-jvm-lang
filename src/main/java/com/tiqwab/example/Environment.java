package com.tiqwab.example;

import com.tiqwab.example.symbol.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Environment {

    private Optional<Environment> parent;
    private Map<String, Symbol> env = new HashMap<>();
    private int index;

    public Environment() {
        this.parent = Optional.empty();
        index = 0;
    }

    public Environment(Environment parent) {
        this.parent = Optional.of(parent);
        this.index = parent.index;
    }

    public void put(final String name, final Symbol symbol) {
        this.env.put(name, symbol);
    }

    public Optional<Symbol> get(final String name) {
        Symbol symbol = env.get(name);
        if (symbol == null) {
            return this.parent.map(p -> p.get(name)).orElse(Optional.empty());
        }
        return Optional.of(symbol);
    }

    public Symbol getOrNew(final String name, final Type type) {
        return this.get(name).orElseGet(() -> this.newSymbol(name, type));
    }

    public boolean exists(final String name) {
        return this.get(name).isPresent();
    }

    public Symbol newSymbol(final String name, final Type varType) {
        if (this.exists(name)) {
            throw new IllegalArgumentException("Alreday exists: " + name);
        }
        final Symbol symbol = new Symbol(index++, varType);
        this.put(name, symbol);
        return symbol;
    }

}
