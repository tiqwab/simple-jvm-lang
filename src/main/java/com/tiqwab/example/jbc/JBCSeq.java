package com.tiqwab.example.jbc;

import java.util.Optional;

public class JBCSeq extends JBCNodeBase implements JBCStmt {

    private JBCStmt head;
    private Optional<JBCSeq> tail;

    public JBCSeq(final JBCStmt stmt) {
        this.head = stmt;
        this.tail = Optional.empty();
    }

    public JBCStmt getHead() {
        return this.head;
    }
    public Optional<JBCSeq> getTail() {
        return this.tail;
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
        visitor.visit(this);
    }

}
