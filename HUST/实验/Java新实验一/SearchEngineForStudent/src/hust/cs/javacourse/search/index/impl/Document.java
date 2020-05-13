package hust.cs.javacourse.search.index.impl;


import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractTermTuple;

import java.util.List;

public class Document extends AbstractDocument {

    /**
     * 缺省构造函数
     */
    public Document() {
    }

    /**
     * 构造函数
     * @param docId：文档id
     * @param docPath：文档绝对路径
     */
    public Document(int docId, String docPath) {
        super(docId, docPath);
    }


    public Document(int docId, String docPath, List<AbstractTermTuple> tuples) {
        super(docId, docPath, tuples);
    }


    @Override
    public int getDocId() {
        return this.docId;
    }

    @Override
    public void setDocId(int docId) {
        this.docId = docId;
    }

    @Override
    public String getDocPath() {
        return this.docPath;
    }

    @Override
    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    @Override
    public List<AbstractTermTuple> getTuples() {
        return this.tuples;
    }

    @Override
    public void addTuple(AbstractTermTuple tuple) {
        if (!this.contains(tuple)){
            this.tuples.add(tuple);
        }
    }

    @Override
    public boolean contains(AbstractTermTuple tuple) {
        return this.tuples.contains(tuple);
    }

    @Override
    public AbstractTermTuple getTuple(int index) {
        return this.tuples.get(index);
    }

    @Override
    public int getTupleSize() {
        return this.tuples.size();
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (AbstractTermTuple tuple :
                this.tuples) {
            buf.append(tuple);
        }
        return "{docId: " + this.docId + ",docPath: " + this.docPath + ",tuples:\n" + buf.toString() + "}\n";
    }
}
