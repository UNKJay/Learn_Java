package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.parse.impl.LengthTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.PatternTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.StopWordTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.TermTupleScanner;

import java.io.*;


public class DocumentBuilder extends AbstractDocumentBuilder {
    @Override
    public AbstractDocument build(int docId, String docPath, AbstractTermTupleStream termTupleStream) {
        AbstractDocument document = new Document(docId,docPath);
        AbstractTermTuple termTuple = null;
        termTuple = termTupleStream.next();
        while (termTuple != null) {             //添加所有词组
            document.addTuple(termTuple);
            termTuple = termTupleStream.next();
        }
        termTupleStream.close();            //关闭流
        return document;
    }

    @Override
    public AbstractDocument build(int docId, String docPath, File file) {
        AbstractDocument document = null;
        AbstractTermTupleStream ts = null;
        try {
            ts = new TermTupleScanner(new BufferedReader(
                    new InputStreamReader(new FileInputStream(file))));

            ts = new LengthTermTupleFilter(ts);                 //长度过滤
            ts = new PatternTermTupleFilter(ts);                //正则表达式过滤
            ts = new StopWordTermTupleFilter(ts);               //停用词过滤
            document = build(docId,docPath,ts);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ts != null) {               //判空
                ts.close();
            }
        }
        return document;
    }
}
