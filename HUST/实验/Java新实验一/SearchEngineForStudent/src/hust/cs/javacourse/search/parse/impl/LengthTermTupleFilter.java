package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

import java.io.IOException;

public class LengthTermTupleFilter extends AbstractTermTupleFilter {
    /**
     * 构造函数
     *
     * @param input ：Filter的输入，类型为AbstractTermTupleStream
     */
    public LengthTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    @Override
    public AbstractTermTuple next() {

        AbstractTermTuple termTuple = input.next();
        if (termTuple == null) {        //读入完毕
            return null;
        }

        while (termTuple.term.getContent().length() > Config.TERM_FILTER_MAXLENGTH ||
        termTuple.term.getContent().length()<Config.TERM_FILTER_MINLENGTH) {        //长度不符合要求
            termTuple = input.next();
            if (termTuple == null) {    //读入完毕
                return null;
            }
        }

        return termTuple;
    }
}
