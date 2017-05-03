package com.tiqwab.example.symbol;

import org.objectweb.asm.Opcodes;

public enum Type {

    Int(Opcodes.ISTORE), Float(Opcodes.FSTORE), Void(-1);

    private final int storeCode;

    Type(final int storeCode) {
        this.storeCode = storeCode;
    }

    public static Type of(final String typeName) {
        for (Type type : Type.values()) {
            if (type.name().toLowerCase().equals(typeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Illegal name of type: " + typeName);
    }

    public int getStoreCode() {
        return this.storeCode;
    }

}
