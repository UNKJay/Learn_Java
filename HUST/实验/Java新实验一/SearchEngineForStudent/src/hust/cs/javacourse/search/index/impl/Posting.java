package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

public class Posting extends AbstractPosting {

    /**
     * 缺省构造函数
     */
    public Posting() {
    }

    public Posting(int docId, int freq, List<Integer> positions) {
        super(docId, freq, positions);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Posting){
            return this.docId == ((Posting) obj).docId
                    && this.freq == ((Posting) obj).freq
                    && this.positions.size() == ((Posting) obj).positions.size()        //提高比较速度
                    && this.positions.containsAll(((Posting) obj).positions)
                    && ((Posting) obj).positions.containsAll(this.positions);
        }
        return false;
    }

    @Override
    public String toString() {
        return "{docId: " + this.docId + ",freq: " + this.freq + ",positions: " + this.positions + "}";
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
    public int getFreq() {
        return this.freq;
    }

    @Override
    public void setFreq(int freq) {
        this.freq = freq;
    }

    @Override
    public List<Integer> getPositions() {
        return this.positions;
    }

    @Override
    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }

    @Override
    public int compareTo(AbstractPosting o) {
        return this.docId - o.getDocId();
    }

    @Override
    public void sort() {
        Collections.sort(this.positions);
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(this.docId);
            out.writeObject(this.freq);

            //List 不实现FileSerializable接口，首先序列化size
            out.writeObject(this.positions.size());
            for (Integer i :
                    this.positions) {
                out.writeObject(i);         //逐个序列化
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try {
            this.docId = (Integer) in.readObject();     //与序列化顺序一致
            this.freq = (Integer) in.readObject();
            int size = (Integer) in.readObject();
            for (int i=0; i<size; ++i) {
                this.positions.add((Integer) in.readObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
