package com.tiqwab.example.symbol;

import org.objectweb.asm.Opcodes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Type {

    Int(Opcodes.ISTORE, Opcodes.IADD, Opcodes.ISUB, Opcodes.IMUL, Opcodes.IDIV),
    Float(Opcodes.FSTORE, Opcodes.FADD, Opcodes.FSUB, Opcodes.FMUL, Opcodes.FDIV),
    Void(-1, -1, -1, -1, -1);

    private final int storeCode;
    private final int addCode;
    private final int subCode;
    private final int mulCode;
    private final int divCode;

    private static final Map<Pair<Type, Type>, Type> maxMap;

    Type(final int storeCode, final int addCode, final int subCode, final int mulCode, final int divCode) {
        this.storeCode = storeCode;
        this.addCode = addCode;
        this.subCode = subCode;
        this.mulCode = mulCode;
        this.divCode = divCode;
    }

    static {
        maxMap = new HashMap<>();
        maxMap.put(new Pair(Type.Int, Type.Int), Type.Int);
        maxMap.put(new Pair(Type.Float, Type.Float), Type.Float);
        maxMap.put(new Pair(Type.Int, Type.Float), Type.Float);
        maxMap.put(new Pair(Type.Float, Type.Int), Type.Float);
    }

    public static Type of(final String typeName) {
        for (Type type : Type.values()) {
            if (type.name().toLowerCase().equals(typeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Illegal name of type: " + typeName);
    }

    public static Optional<Type> max(Type x, Type y) {
        Type type = Type.maxMap.get(new Pair(x, y));
        if (type == null) {
            return Optional.empty();
        }
        return Optional.of(type);
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
