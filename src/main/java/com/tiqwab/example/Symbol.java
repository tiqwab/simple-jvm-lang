package com.tiqwab.example;

import com.tiqwab.example.symbol.Type;

public class Symbol {

    private final int index;
    private final Type type;

    public Symbol(final int index, final Type type) {
        this.index = index;
        this.type = type;
    }

    public int getIndex() {
        return this.index;
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Symbol symbol = (Symbol) o;

        if (index != symbol.index) return false;
        return type == symbol.type;
    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("Symbo{index=%d}", index);
    }

}
