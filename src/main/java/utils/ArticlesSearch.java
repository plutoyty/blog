package utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import static utils.JdbcUtil.con1;

public class ArticlesSearch {
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public static final String INDEXPATH = "D://indexFile//dbIndex";

    public static boolean createIndex() {
        try {
            Directory dir = FSDirectory.open(Paths.get(INDEXPATH)); // 使用了nio，存储索引的路径
            Analyzer analyzer = new StandardAnalyzer(); // 无参构造函数
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer); // 新的IndexWriter配置类
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE); // 创建模式打开
            //iwc.setRAMBufferSizeMB(256.0); // 设置内存缓存的大小，提高索引效率，不过如果修改过大的值，需要修改JVM的内存值
            IndexWriter writer = new IndexWriter(dir, iwc); // 创建IndexWriter
            Connection con = con1;
            PreparedStatement ps;
            String query = "select * from article";
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Document doc = new Document();
                String id = rs.getString("pk_id");
                String title = rs.getString("idx_title");
                String sort = rs.getString("idx_sort");
                String content = rs.getString("idx_content");
                String tsg = rs.getString("idx_tag");
                doc.add(new TextField("id", id, Field.Store.YES));
                doc.add(new TextField("tag", tsg, Field.Store.YES));
                doc.add(new TextField("title", title, Field.Store.YES));
                doc.add(new TextField("sort", sort, Field.Store.YES)); // 做analyze
                doc.add(new TextField("content", content, Field.Store.YES));
                writer.addDocument(doc);
            }
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    private static int TOP_NUM = 100; // 显示记录数

    public static List<String> searchData(String queryStr) {
        List<String> list = new ArrayList<>();
        Directory dir = null;
        try {
            dir = FSDirectory.open(Paths.get(INDEXPATH));
            IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher searcher = new IndexSearcher(reader);
            Analyzer analyzer = new StandardAnalyzer();
            //clauses
            BooleanClause.Occur[] clauses = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};
            String[] fields = {"title", "content"};
            Query multiFieldQuery = MultiFieldQueryParser.parse(queryStr, fields, clauses, analyzer);
            TopDocs hits = searcher.search(multiFieldQuery, TOP_NUM);
            System.out.println("本次搜索共找到" + hits.totalHits + "条数据");
            // 格式化器
//            Formatter formatter = new SimpleHTMLFormatter("<em>", "</em>");
//            QueryScorer scorer = new QueryScorer(query);
//            // 准备高亮工具
//            Highlighter highlighter = new Highlighter(formatter, scorer);
            for (ScoreDoc scoreDoc : hits.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc); // 根据文档打分得到文档的内容
                list.add(doc.get("id"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static List<String> searchData(String queryStr, String fieldString) {
        List<String> list = new ArrayList<>();
        Directory dir = null;
        try {
            dir = FSDirectory.open(Paths.get(INDEXPATH));
            IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher searcher = new IndexSearcher(reader);
            Analyzer analyzer = new StandardAnalyzer();
            QueryParser parser = new QueryParser(fieldString, analyzer);
            parser.setDefaultOperator(QueryParser.AND_OPERATOR);
            Query query = parser.parse(queryStr);
            TopDocs hits = searcher.search(query, 100); // 查找操作
            System.out.println("本次搜索共找到" + hits.totalHits + "条数据");
            for (ScoreDoc scoreDoc : hits.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc); // 根据文档打分得到文档的内容
                list.add(doc.get("id"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        createIndex();
        System.out.println(searchData("设计", "tag"));
    }
}
