package com.tiqwab.example.symbol;

import org.objectweb.asm.Opcodes;

public enum Type {

    Int(Opcodes.ISTORE, Opcodes.IADD, Opcodes.ISUB, Opcodes.IMUL, Opcodes.IDIV),
    Float(Opcodes.FSTORE, Opcodes.FADD, Opcodes.FSUB, Opcodes.FMUL, Opcodes.FDIV),
    Void(-1, -1, -1, -1, -1);

    private final int storeCode;
    private final int addCode;
    private final int subCode;
    private final int mulCode;
    private final int divCode;

    Type(final int storeCode, final int addCode, final int subCode, final int mulCode, final int divCode) {
        this.storeCode = storeCode;
        this.addCode = addCode;
        this.subCode = subCode;
        this.mulCode = mulCode;
        this.divCode = divCode;
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

    public int getAddCode() {
        return this.addCode;
    }

    public int getSubCode() {
        return this.subCode;
    }

    public int getMulCode() {
        return this.mulCode;
    }

    public int getDivCode() {
        return this.divCode;
    }

}
