package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.*;
import hust.cs.javacourse.search.util.FileUtil;

import java.io.*;
import java.util.*;

/**
 * AbstractIndex的具体实现类
 */
public class Index extends AbstractIndex {

    /**
     * 缺省构造函数
     */
    public Index() {
    }

    /**
     * 返回索引的字符串表示
     *
     * @return 索引的字符串表示
     */
    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();

        buf.append("docId  to docPath :\n");
        Iterator<Map.Entry<Integer,String>> it1 = this.docIdToDocPathMapping.entrySet().iterator();
        while (it1.hasNext()){          //使用迭代器迭代Map中的元素
            Map.Entry<Integer,String> entry = it1.next();
            buf.append(entry.getKey() + "   ---->   " +
                    entry.getValue()).append("\n");
        }

        buf.append("Term to PostingList :\n");
        Iterator<Map.Entry<AbstractTerm,AbstractPostingList>> it2 = termToPostingListMapping.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry<AbstractTerm,AbstractPostingList> entry = it2.next();
            buf.append(entry.getKey() + " ---> " +
                entry.getValue()).append("\n");
        }
        return buf.toString();
    }

    /**
     * 添加文档到索引，更新索引内部的HashMap
     *
     * @param document ：文档的AbstractDocument子类型表示
     */
    @Override
    public void addDocument(AbstractDocument document) {

        this.docIdToDocPathMapping.put(document.getDocId(),document.getDocPath());           //docId--->docPath

        for (AbstractTermTuple termTuple :
                document.getTuples()) {
            if (!termToPostingListMapping.containsKey(termTuple.term)) {            //term不存在
                AbstractPosting posting = new Posting();
                posting.setDocId(document.getDocId());
                posting.setFreq(termTuple.freq);
                List<Integer> position = new ArrayList<>();
                position.add(termTuple.curPos);
                posting.setPositions(position);
                termToPostingListMapping.put(termTuple.term,new PostingList());     //put
                termToPostingListMapping.get(termTuple.term).add(posting);
            } else {
                int index = termToPostingListMapping.get(termTuple.term).indexOf(document.getDocId());
                if (index != -1) {      //已经构造好对应的posting，只添加curPos进入和freq+1即可
                    termToPostingListMapping.get(termTuple.term).get(index).getPositions().add(termTuple.curPos);
                    int freq = termToPostingListMapping.get(termTuple.term).get(index).getFreq();
                    termToPostingListMapping.get(termTuple.term).get(index).setFreq(freq+1);
                } else {                //构造posting加入postingList
                    AbstractPosting posting = new Posting();
                    posting.setDocId(document.getDocId());
                    posting.setFreq(termTuple.freq);
                    List<Integer> position = new ArrayList<>();
                    position.add(termTuple.curPos);
                    posting.setPositions(position);
                    termToPostingListMapping.get(termTuple.term).add(posting);
                }
            }
        }
        this.optimize();            //添加好后重新排序
    }

    /**
     * <pre>
     * 从索引文件里加载已经构建好的索引.内部调用FileSerializable接口方法readObject即可
     * @param file ：索引文件
     * </pre>
     */
    @Override
    public void load(File file) {
        if (file != null){
            try {
                this.readObject(new ObjectInputStream(new FileInputStream(file)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <pre>
     * 将在内存里构建好的索引写入到文件. 内部调用FileSerializable接口方法writeObject即可
     * @param file ：写入的目标索引文件
     * </pre>
     */
    @Override
    public void save(File file) {
        if (file != null) {
            try {
                this.writeObject(new ObjectOutputStream(new FileOutputStream(file)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 返回指定单词的PostingList
     *
     * @param term : 指定的单词
     * @return ：指定单词的PostingList;如果索引字典没有该单词，则返回null
     */
    @Override
    public AbstractPostingList search(AbstractTerm term) {
        return termToPostingListMapping.get(term);
    }

    /**
     * 返回索引的字典.字典为索引里所有单词的并集
     *
     * @return ：索引中Term列表
     */
    @Override
    public Set<AbstractTerm> getDictionary() {
        return termToPostingListMapping.keySet();
    }

    /**
     * <pre>
     * 对索引进行优化，包括：
     *      对索引里每个单词的PostingList按docId从小到大排序
     *      同时对每个Posting里的positions从小到大排序
     * 在内存中把索引构建完后执行该方法
     * </pre>
     */
    @Override
    public void optimize() {
        for (Map.Entry<AbstractTerm, AbstractPostingList> entry:
            termToPostingListMapping.entrySet()){

            for (int i=0; i<entry.getValue().size(); ++i) {
                entry.getValue().get(i).sort();             // 先对positions进行排序
            }
            entry.getValue().sort();            //再按照docId进行排序
        }
    }

    /**
     * 根据docId获得对应文档的完全路径名
     *
     * @param docId ：文档id
     * @return : 对应文档的完全路径名
     */
    @Override
    public String getDocName(int docId) {
        return this.docIdToDocPathMapping.get(docId);
    }

    /**
     * 写到二进制文件
     *
     * @param out :输出流对象
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(this.docIdToDocPathMapping.size());         //先序列化两个映射的大小
            out.writeObject(this.termToPostingListMapping.size());
            for (Map.Entry<Integer, String> entry :
                    this.docIdToDocPathMapping.entrySet()) {            //逐个序列化两个映射中的元素
                out.writeObject(entry.getKey());
                out.writeObject(entry.getValue());
            }

            for (Map.Entry<AbstractTerm, AbstractPostingList> entry:
                    this.termToPostingListMapping.entrySet()) {
                entry.getKey().writeObject(out);                    //调用元素的writeObject方法
                entry.getValue().writeObject(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 从二进制文件读
     *
     * @param in ：输入流对象
     */
    @Override
    public void readObject(ObjectInputStream in) {
        int docSize = 0;
        int termSize = 0;
        try {
            docSize = (Integer) in.readObject();            //与序列化的顺序一致即可
            termSize = (Integer) in .readObject();

            for (int i=0; i<docSize; ++i) {
                Integer docId = (Integer) in.readObject();
                String docPath = (String) in.readObject();
                this.docIdToDocPathMapping.put(docId,docPath);
            }

            for (int i=0; i<termSize; ++i) {
                AbstractTerm term = new Term();
                term.readObject(in);
                AbstractPostingList postingList = new PostingList();
                postingList.readObject(in);
                this.termToPostingListMapping.put(term,postingList);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入普通文本文件
     * @param filePath 写入的文件路径
     */
    public void writeText(String filePath) {
        FileUtil.write(this.toString(),filePath);
    }
}
