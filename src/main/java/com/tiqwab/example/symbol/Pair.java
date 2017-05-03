package com.tiqwab.example.symbol;

public class Pair<F, S> {

    private final Type fst;
    private final Type snd;

    public Pair(final Type fst, final Type snd) {
        this.fst = fst;
        this.snd = snd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (fst != pair.fst) return false;
        return snd == pair.snd;
    }

    @Override
    public int hashCode() {
        int result = fst != null ? fst.hashCode() : 0;
        result = 31 * result + (snd != null ? snd.hashCode() : 0);
        return result;
    }

}
