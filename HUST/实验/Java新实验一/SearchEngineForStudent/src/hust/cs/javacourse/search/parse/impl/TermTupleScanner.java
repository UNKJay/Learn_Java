package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StringSplitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TermTupleScanner extends AbstractTermTupleScanner {

    /**
     * tupleList 缓存一行中的分词结果
     */
    List<AbstractTermTuple> tupleList = new ArrayList<>();

    /**
     * pos 记录当前位置
     */
    int pos = 0;

    /**
     * 构造函数
     * @param input：指定输入流对象，应该关联到一个文本文件
     */
    public TermTupleScanner(BufferedReader input) {
        super(input);
    }

    @Override
    public AbstractTermTuple next() {
        if (tupleList.isEmpty()) {
            String line = null;
            try {
                line = input.readLine();        //读入一行
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line == null) {         //读到文件尾
                return null;
            }
            while (line.trim().length()==0) {       //空行
                try {
                    line = input.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (line == null) {     //读到文件尾
                    return null;
                }
            }

            StringSplitter stringSplitter = new StringSplitter();
            stringSplitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);     //划分
            for (String word :
                    stringSplitter.splitByRegex(line)) {

                TermTuple termTuple = new TermTuple();
                termTuple.curPos = pos++;
                if (Config.IGNORE_CASE) {           //忽略大小写
                    termTuple.term = new Term(word.toLowerCase());
                } else {
                    termTuple.term = new Term(word);
                }

                tupleList.add(termTuple);
            }
        }

        AbstractTermTuple termTuple = tupleList.get(0);     //取出第一个元素
        tupleList.remove(0);
        return termTuple;
    }
}
