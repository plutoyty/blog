

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import java.io.IOException;
import java.nio.file.Paths;


/**
 * Created by dell on 2015/8/11.
 */
public class DataBaseSearcher {

    public static final String INDEXPATH = "D://indexFile//dbIndex";
    private static int TOP_NUM = 100; // 显示记录数

    public static void searchData(String queryStr) {
        Directory dir = null;
        try {
            dir = FSDirectory.open(Paths.get(INDEXPATH));
            IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher searcher = new IndexSearcher(reader);
            Analyzer analyzer = new StandardAnalyzer();
            String fieldString = "sort";
            QueryParser parser = new QueryParser(fieldString, analyzer);
            parser.setDefaultOperator(QueryParser.AND_OPERATOR);
            Query query = parser.parse(queryStr);
            TopDocs hits = searcher.search(query,10); // 查找操作
            System.out.println("本次搜索共找到" + hits.totalHits + "条数据");
            for(ScoreDoc scoreDoc : hits.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc); // 根据文档打分得到文档的内容
                System.out.println(doc.get("id")); // 找到文件后，输出路径
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        searchData("暑假学习");
    }

}