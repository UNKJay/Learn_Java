package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

import java.io.IOException;

public class PatternTermTupleFilter extends AbstractTermTupleFilter {
    /**
     * 构造函数
     *
     * @param input ：Filter的输入，类型为AbstractTermTupleStream
     */
    public PatternTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple termTuple = input.next();
        if (termTuple == null) {            //读入完毕
            return null;
        }

        while (!termTuple.term.getContent().matches(Config.TERM_FILTER_PATTERN)) {      //不符合正则表达式规范
            termTuple = input.next();
            if (termTuple == null) {        //读入完毕
                return null;
            }
        }
        return termTuple;
    }
}
