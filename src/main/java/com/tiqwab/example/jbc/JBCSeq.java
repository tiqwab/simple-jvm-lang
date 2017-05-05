package com.tiqwab.example.jbc;

import com.tiqwab.example.Environment;
import com.tiqwab.example.symbol.Type;
import org.objectweb.asm.MethodVisitor;

import java.util.Optional;

public class JBCSeq extends JBCNodeBase implements JBCNode {

    private JBCStmt head;
    private Optional<JBCSeq> tail;

    public JBCSeq(final JBCStmt stmt) {
        this.head = stmt;
        this.tail = Optional.empty();
    }

    public JBCSeq getTail() {
        return this.tail.orElseThrow(() -> new IllegalStateException("Tail is null"));
    }

    public void setTail(JBCSeq seq) {
        if (seq == null) {
            this.tail = Optional.empty();
        }
        this.tail = Optional.of(seq);
    }

    @Override
    public String toString() {
        return String.format("JBCSeq{head=%s, tail=%s}", this.head, this.tail);
    }

    @Override
    public void accept(JBCNodeVisitor visitor) {
        this.head.accept(visitor);
        this.tail.ifPresent(seq -> seq.accept(visitor));
        visitor.visit(this);
    }

    @Override
    public void genCode(MethodVisitor mv, Environment env) {

    }

}
