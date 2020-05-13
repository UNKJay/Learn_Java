package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

public class PostingList extends AbstractPostingList {

    /**
     * 添加Posting,要求不能有内容重复的posting
     * @param posting：Posting对象
     */
    @Override
    public void add(AbstractPosting posting) {
        if (!contains(posting)) {       //不填加重复的posting
            this.list.add(posting);
        }
    }

    /**
     * 获得PosingList的字符串表示
     * @return ： PosingList的字符串表示
     */
    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        boolean flag = false;               //辅助判断是否为第一个元素
        for (AbstractPosting posting :
                this.list) {
            if (!flag){
                flag = true;
            } else {
                buf.append(" ---> ");
            }
            buf.append(posting);
        }
        return buf.toString();
    }

    @Override
    public void add(List<AbstractPosting> postings) {
        for (AbstractPosting posting :
                postings) {
            this.add(posting);      //逐个添加
        }
    }

    @Override
    public AbstractPosting get(int index) {
        return this.list.get(index);
    }

    @Override
    public int indexOf(AbstractPosting posting) {
        return this.list.indexOf(posting);
    }

    @Override
    public int indexOf(int docId) {
        for (int i=0; i<list.size(); ++i) {
            if (list.get(i).getDocId() == docId) {      //逐个查找
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(AbstractPosting posting) {
        return this.list.contains(posting);
    }

    @Override
    public void remove(int index) {
        this.list.remove(index);
    }

    @Override
    public void remove(AbstractPosting posting) {
        this.list.remove(posting);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public void sort() {
        Collections.sort(this.list);
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(this.size());
            for (AbstractPosting posting :
                    this.list) {
                posting.writeObject(out);       //依次调用成员的writeObject方法
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        final double PI = 3.14;
        int[][] nums = new int[8][4];
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try {
            int size = (Integer) in.readObject();
            for (int i=0; i<size; ++i) {
                AbstractPosting posting = new Posting();
                posting.readObject(in);                 //依次调用成员的readObject方法
                this.list.add(posting);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
