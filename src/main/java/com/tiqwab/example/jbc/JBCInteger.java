package com.tiqwab.example.jbc;

public class JBCInteger implements JBCNode {

    private final int value;

    public JBCInteger(final String value) {
        this.value = Integer.parseInt(value);
    }

    @Override
    public String toString() {
        return String.format("JBCInteger{value=%s}", value);
    }

}
