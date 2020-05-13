package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.impl.Index;
import hust.cs.javacourse.search.index.impl.PostingList;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;
import hust.cs.javacourse.search.util.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class IndexSearcher extends AbstractIndexSearcher {
    @Override
    public void open(String indexFile) {
        this.index = new Index();                   //重新创建新索引结构
        this.index.load(new File(indexFile));       //载入文件
    }

    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {

        if (Config.IGNORE_CASE) {               //忽略大小写
            queryTerm.setContent(queryTerm.getContent().toLowerCase());
        }

        AbstractPostingList postingList = index.search(queryTerm);      //查找对应的postingList
        if (postingList == null) {              //None
            return new Hit[0];
        }

        List<AbstractHit> hitList = new ArrayList<>();
        for (int i=0; i<postingList.size(); ++i) {
            AbstractPosting posting = postingList.get(i);
            AbstractHit hit = new Hit(posting.getDocId(),index.getDocName(posting.getDocId()));
            hit.getTermPostingMapping().put(queryTerm,posting);
            hit.setScore(sorter.score(hit));                        //设置score
            hitList.add(hit);
        }

        sorter.sort(hitList);                                   //排序
        AbstractHit[] hitArray = new AbstractHit[hitList.size()];       //转换
        return hitList.toArray(hitArray);
    }

    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {

        AbstractPostingList postingList1 = index.search(queryTerm1);
        AbstractPostingList postingList2 = index.search(queryTerm2);

        List<AbstractHit> hitList = new ArrayList<>();

        if (combine == LogicalCombination.AND) {            //与
            if (postingList1 == null || postingList2 == null) {         //两者其中一个为空就不存在
                return new Hit[0];
            }

            for (int i=0; i<postingList1.size(); ++i) {
                int docId = postingList1.get(i).getDocId();
                int indexOf2 = postingList2.indexOf(docId);            //根据docId查找交集
                if (indexOf2 != -1) {
                    AbstractHit hit = new Hit(docId,index.getDocName(docId));
                    hit.getTermPostingMapping().put(queryTerm1,postingList1.get(i));
                    hit.getTermPostingMapping().put(queryTerm2,postingList2.get(indexOf2));
                    hit.setScore(sorter.score(hit));
                    hitList.add(hit);
                }
            }

        } else if (combine == LogicalCombination.OR){

            //两个有一个为空则转换为对另一个单独的搜索
            if (postingList1 == null) {
                return search(queryTerm2,sorter);
            }

            if (postingList2 == null) {
                return search(queryTerm1,sorter);
            }

            for (int i=0; i<postingList1.size(); ++i) {
                int docId = postingList1.get(i).getDocId();
                int indexOf2 = postingList2.indexOf(docId);            //根据docId查找交集
                if (indexOf2 == -1) {                                   //直接添加
                    AbstractHit hit = new Hit(docId,index.getDocName(docId));
                    hit.getTermPostingMapping().put(queryTerm1,postingList1.get(i));
                    hit.setScore(sorter.score(hit));
                    hitList.add(hit);
                } else {
                    AbstractHit hit = new Hit(docId,index.getDocName(docId));
                    hit.getTermPostingMapping().put(queryTerm1,postingList1.get(i));
                    hit.getTermPostingMapping().put(queryTerm2,postingList2.get(indexOf2));
                    hit.setScore(sorter.score(hit));
                    hitList.add(hit);
                }
            }

            for (int i=0; i<postingList2.size(); ++i) {
                int docId = postingList2.get(i).getDocId();
                int indexOf1 = postingList1.indexOf(docId);            //根据docId查找交集
                if (indexOf1 == -1) {                                   //只查找尚未找出的
                    AbstractHit hit = new Hit(docId,index.getDocName(docId));
                    hit.getTermPostingMapping().put(queryTerm2,postingList2.get(i));
                    hit.setScore(sorter.score(hit));
                    hitList.add(hit);
                }
            }
        }

        sorter.sort(hitList);                                       //排序
        AbstractHit[] hitArray = new AbstractHit[hitList.size()];   //转换
        return hitList.toArray(hitArray);
    }
}
