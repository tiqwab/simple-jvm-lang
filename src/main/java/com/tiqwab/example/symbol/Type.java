package com.tiqwab.example.symbol;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Type {

    Int(Opcodes.ISTORE, Opcodes.ILOAD, Opcodes.IADD, Opcodes.ISUB, Opcodes.IMUL, Opcodes.IDIV),
    Float(Opcodes.FSTORE, Opcodes.FLOAD, Opcodes.FADD, Opcodes.FSUB, Opcodes.FMUL, Opcodes.FDIV),
    Bool(Opcodes.ISTORE, Opcodes.ILOAD, -1, -1, -1, -1),
    Void(-1, -1, -1, -1, -1, -1);

    private final int storeCode;
    private final int loadCode;
    private final int addCode;
    private final int subCode;
    private final int mulCode;
    private final int divCode;

    private static final Map<Pair<Type, Type>, Type> maxMap;
    private static final Map<Pair<Type, Type>, Integer> widenMap;

    Type(final int storeCode, final int loadCode, final int addCode, final int subCode, final int mulCode, final int divCode) {
        this.storeCode = storeCode;
        this.loadCode = loadCode;
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
        maxMap.put(new Pair(Type.Bool, Type.Bool), Type.Bool);

        widenMap = new HashMap<>();
        widenMap.put(new Pair(Type.Int, Type.Float), Opcodes.I2F);
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

    public static void widen(MethodVisitor mv, Type type, Type widenedType) {
        final Integer convertCode = widenMap.get(new Pair(type, widenedType));
        if (convertCode == null) return;
        mv.visitInsn(convertCode);
    }

    public int getStoreCode() {
        return this.storeCode;
    }

    public int getLoadCode() {
        return this.loadCode;
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
