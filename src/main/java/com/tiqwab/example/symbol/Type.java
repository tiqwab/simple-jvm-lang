package com.tiqwab.example.symbol;

public enum Type {

    Int, Float, Void;

    public static Type of(final String typeName) {
        for (Type type : Type.values()) {
            if (type.name().toLowerCase().equals(typeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Illegal name of type: " + typeName);
    }

}
