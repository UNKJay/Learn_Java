package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;

import java.util.Map;

public class Hit extends AbstractHit {

    /**
     * 默认构造函数
     */
    public Hit() {
    }

    /**
     * 构造函数
     *
     * @param docId   : 文档id
     * @param docPath : 文档绝对路径
     */
    public Hit(int docId, String docPath) {
        super(docId, docPath);
    }

    /**
     * 构造函数
     *
     * @param docId              ：文档id
     * @param docPath            ：文档绝对路径
     * @param termPostingMapping ：命中的三元组列表
     */
    public Hit(int docId, String docPath, Map<AbstractTerm, AbstractPosting> termPostingMapping) {
        super(docId, docPath, termPostingMapping);
    }

    @Override
    public int getDocId() {
        return this.docId;
    }

    @Override
    public String getDocPath() {
        return this.docPath;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public double getScore() {
        return this.score;
    }

    @Override
    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public Map<AbstractTerm, AbstractPosting> getTermPostingMapping() {
        return this.termPostingMapping;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("------Hit-----\n").
                append("docId: ").append(this.docId).append("\ndocPath: ").append(this.docPath).append("\ncontent: ").
                append(this.content).append("\nscore: ").append(-this.score).append("\n") ;

        for (Map.Entry<AbstractTerm, AbstractPosting> entry :
                this.termPostingMapping.entrySet()) {
            buf.append(entry.getKey()).append("--->").append(entry.getValue()).append("\n");
        }
        return buf.toString();
    }

    @Override
    public int compareTo(AbstractHit o) {
        return (int) (this.score - o.getScore());
    }       //转换为int类型
}
