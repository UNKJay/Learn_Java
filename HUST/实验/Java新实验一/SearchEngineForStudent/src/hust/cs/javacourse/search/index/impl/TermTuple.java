package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.AbstractTermTuple;

public class TermTuple extends AbstractTermTuple {

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TermTuple) {
            return this.curPos == ((TermTuple) obj).curPos
                    && this.term.equals(((TermTuple) obj).term);        //递归调用equals
        }
        return false;
    }

    @Override
    public String toString() {
        return "<term:" + this.term + ",freq: " + this.freq + ",curPos: " + this.curPos + ">";
    }
}
