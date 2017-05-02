package com.tiqwab.example;

public class Symbol {

    private final int index;

    public Symbol(final int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Symbol symbol = (Symbol) o;

        return index == symbol.index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public String toString() {
        return String.format("Symbo{index=%d}", index);
    }

}
