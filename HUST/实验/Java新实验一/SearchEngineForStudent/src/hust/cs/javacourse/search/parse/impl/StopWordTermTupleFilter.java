package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StopWords;

import java.io.IOException;
import java.util.Arrays;

public class StopWordTermTupleFilter extends AbstractTermTupleFilter {
    /**
     * 构造函数
     *
     * @param input ：Filter的输入，类型为AbstractTermTupleStream
     */
    public StopWordTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    @Override
    public AbstractTermTuple next()  {
        AbstractTermTuple termTuple = input.next();
        if (termTuple == null) {            //读入完毕
            return null;
        }

        while (Arrays.asList(StopWords.STOP_WORDS).contains(termTuple.term.getContent())) {     //包含停用词
            termTuple = input.next();
            if (termTuple == null) {        //读入完毕
                return null;
            }
        }
        return termTuple;
    }
}
