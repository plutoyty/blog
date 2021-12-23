
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import utils.JdbcUtil;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by dell on 2015/8/10.
 * 对数据库数据做索引
 */
public class DataBaseIndex {

    public static final String INDEXPATH = "D://indexFile//dbIndex";

    public static boolean createIndex() {
        try {
            Directory dir = FSDirectory.open(Paths.get(INDEXPATH)); // 使用了nio，存储索引的路径
            Analyzer analyzer = new StandardAnalyzer(); // 无参构造函数
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer); // 新的IndexWriter配置类
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE); // 创建模式打开
            //iwc.setRAMBufferSizeMB(256.0); // 设置内存缓存的大小，提高索引效率，不过如果修改过大的值，需要修改JVM的内存值
            IndexWriter writer = new IndexWriter(dir, iwc); // 创建IndexWriter
            Connection con = JdbcUtil.getDataSource().getConnection();
            Statement stmt = con.createStatement();
            String query = "select * from article";
            System.out.println("query==="+query);
            ResultSet rs=stmt.executeQuery(query);
            while(rs.next()) {
                Document doc = new Document();
                String title = rs.getString("idx_title");
                System.out.println("title--------------->"+title);
                String sort = rs.getString("idx_sort");
                System.out.println("sort--------------->"+sort);
                String content = rs.getString("idx_content");
                System.out.println("content---------->"+content);
                doc.add(new TextField("title" ,title,Field.Store.YES));
                doc.add(new StringField("sort",sort,Field.Store.YES)); // 做analyze
                doc.add(new StringField("content",content,Field.Store.YES));
                writer.addDocument(doc);
            }
            writer.close();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public static void main(String[] args) {
        createIndex();
    }

}