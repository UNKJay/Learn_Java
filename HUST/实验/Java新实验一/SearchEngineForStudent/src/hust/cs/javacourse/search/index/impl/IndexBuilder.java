package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;
import hust.cs.javacourse.search.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class IndexBuilder extends AbstractIndexBuilder {

    public IndexBuilder(AbstractDocumentBuilder docBuilder) {
        super(docBuilder);
    }

    @Override
    public AbstractIndex buildIndex(String rootDirectory) {
        AbstractIndex index = new Index();
        List<String> filePaths = FileUtil.list(rootDirectory);
        filePaths.sort(String::compareTo);                                     //顺序排序方便debug
        for (String filePath :
                filePaths) {
            AbstractDocument document = null;
            document = this.docBuilder.build(docId,filePath,new File(filePath));        //构建document
            index.addDocument(document);                        // 将document加入
            this.docId++;                                   //docId++
        }
        return index;
    }
}
