package utils;

import bean.Article;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Test0 {
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    /**
     * 为指定目录下的文件创建索引,包括其下的所有子孙目录下的文件
     *
     * @param ：创建好的索引保存目录
     * @throws IOException
     */
    public void indexCreate(List<Article> articles) throws IOException {
//        /** 如果传入的路径不是目录或者目录不存在，则放弃*/
//        if (!targetFileDir.isDirectory() || !targetFileDir.exists()) {
//            return;
//        }

        /** 创建 Lucene 文档列表，用于保存多个 Docuemnt*/
        List<Document> docList = new ArrayList<Document>();
        /**Lucene 文档对象(Document)，文件系统中的一个文件就是一个 Docuemnt对象
         * 一个 Lucene Docuemnt 对象可以存放多个 Field（域）
         *  Lucene Docuemnt 相当于 Mysql 数据库表的一行记录
         *  Docuemnt 中 Field 相当于 Mysql 数据库表的字段*/
        for (Article article : articles) {
            Document luceneDocument = new Document();

            /**
             * TextField 继承于 org.apache.lucene.document.Field
             * TextField(String name, String value, Store store)--文本域
             *  name：域名，相当于 Mysql 数据库表的字段名
             *  value：域值，相当于 Mysql 数据库表的字段值
             *  store：是否存储，yes 表存储，no 为不存储
             *
             * TextField：表示文本域、默认会分词、会创建索引、第三个参数 Store.YES 表示会存储
             * 同理还有 StoredField、StringField、FeatureField、BinaryDocValuesField 等等
             * 都来自于超级接口：org.apache.lucene.index.IndexableField
             */
            String context = article.getContent();
            TextField titleFiled = new TextField("title", article.getTitle(), Field.Store.NO);
            TextField contextFiled = new TextField("context", context, Field.Store.NO);
            TextField articleIdFiled = new TextField("id", String.valueOf(article.getId()), Field.Store.YES);
            /**如果是 Srore.NO，则不会存储，就意味着后期获取 fileSize 值的时候，值会为null
             * 虽然 Srore.NO 不会存在域的值，但是 TextField本身会分词、会创建索引
             * 所以后期仍然可以根据 fileSize 域进行检索：queryParser.parse("fileContext:" + queryWord);
             * 只是获取 fileSize 存储的值为 null：document.get("fileSize"));
             * 索引是索引，存储的 fileSize 内容是另一回事
             * */
//        TextField sizeFiled = new TextField("fileSize", fileSize.toString(), Field.Store.YES);

            /**将所有的域都存入 Lucene 文档中*/
            luceneDocument.add(titleFiled);
            luceneDocument.add(contextFiled);
            luceneDocument.add(articleIdFiled);

            /**将文档存入文档集合中，之后再同统一进行存储*/
            docList.add(luceneDocument);
        }
        /** 创建分词器
         * StandardAnalyzer：标准分词器，对英文分词效果很好，对中文是单字分词，即一个汉字作为一个词，所以对中文支持不足
         * 市面上有很多好用的中文分词器，如 IKAnalyzer 就是其中一个
         */
        Analyzer analyzer = new StandardAnalyzer();
        writeLock.lock();
        File indexSaveDir = new File("luceneIndex");
        /** 指定之后 创建好的 索引和 Lucene 文档存储的目录
         * 如果目录不存在，则会自动创建*/
        Path path = Paths.get(indexSaveDir.toURI());

        /** FSDirectory：表示文件系统目录，即会存储在计算机本地磁盘，继承于
         * org.apache.lucene.store.BaseDirectory
         * 同理还有：org.apache.lucene.store.RAMDirectory：存储在内存中
         */
        Directory directory = FSDirectory.open(path);
        /** 创建 索引写配置对象，传入分词器*/
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
//        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        /**创建 索引写对象，用于正式写入索引和文档数据*/
        IndexWriter indexWriter = new IndexWriter(directory, config);
        indexWriter.deleteAll();
        /**将 Lucene 文档加入到 写索引 对象中*/
        for (int i = 0; i < docList.size(); i++) {
            indexWriter.addDocument(docList.get(i));
            /**如果目标文档数量较多，可以分批次刷新一下*/
            if ((i + 1) % 50 == 0) {
                indexWriter.flush();
            }
        }
        /**最后再 刷新流，然后提交、关闭流*/
        indexWriter.flush();
        indexWriter.commit();
        indexWriter.close();
        writeLock.unlock();
    }


    public List<Integer> indexSearch(String queryWord) throws Exception {
        readLock.lock();
        File indexDir = new File("luceneIndex");
        List<Integer> articleIds = new ArrayList<>();
        if (indexDir == null || queryWord == null || "".equals(queryWord)) {
            return articleIds;
        }
        /** 创建分词器
         * 1）创建索引 与 查询索引 所用的分词器必须一致
         */
        Analyzer analyzer = new StandardAnalyzer();

//        /**创建查询对象(QueryParser)：QueryParser(String f, Analyzer a)
//         *  第一个参数：默认搜索域，与创建索引时的域名称必须相同
//         *  第二个参数：分词器
//         * 默认搜索域作用：
//         *  如果搜索语法parse(String query)中指定了域名，则从指定域中搜索
//         *  如果搜索语法parse(String query)中只指定了查询关键字，则从默认搜索域中进行搜索

//        QueryParser queryParser = new QueryParser("content", analyzer);
//        Query query = queryParser.parse("title:" + queryWord);
        //** parse 表示解析查询语法，查询语法为："域名:搜索的关键字"
        //         *  parse("fileName:web")：则从fileName域中进行检索 web 字符串
        //         * 如果为 parse("web")：则从默认搜索域 fileContext 中进行检索
        //         * 1)查询不区分大小写
        //         * 2)因为使用的是 StandardAnalyzer(标准分词器)，所以对英文效果很好，如果此时检索中文，基本是行不通的
        //         */
//        Query query = queryParser.parse("fileContext:" + queryWord);
        // MUST 表示and，MUST_NOT 表示not ，SHOULD表示or
        BooleanClause.Occur[] clauses = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};
        // MultiFieldQueryParser表示多个域解析， 同时可以解析含空格的字符串，如果我们搜索"上海 中国"
        String[] fields = {"title", "content", "decription"};
        Query multiFieldQuery = MultiFieldQueryParser.parse(queryWord, fields, clauses, analyzer);
        /** 与创建 索引 和 Lucene 文档 时一样，指定 索引和文档 的目录
         * 即指定查询的索引库
         */
        Path path = Paths.get(indexDir.toURI());
        Directory dir = FSDirectory.open(path);

        /*** 创建 索引库读 对象
         * DirectoryReader 继承于org.apache.lucene.index.IndexReader
         * */
        DirectoryReader directoryReader = DirectoryReader.open(dir);

        /** 根据 索引对象创建 索引搜索对象
         **/
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

        // 5、根据searcher搜索并且返回TopDocs
        TopDocs topdocs = indexSearcher.search(multiFieldQuery, 100); // 搜索前100条结果

        System.out.println("查询结果总数：：：=====" + topdocs.totalHits);

        /**从搜索结果对象中获取结果集
         * 如果没有查询到值，则 ScoreDoc[] 数组大小为 0
         * */
        ScoreDoc[] scoreDocs = topdocs.scoreDocs;

        ScoreDoc loopScoreDoc = null;
        for (int i = 0; i < scoreDocs.length; i++) {

            System.out.println("=======================" + (i + 1) + "=====================================");
            loopScoreDoc = scoreDocs[i];

            /**获取 文档 id 值
             * 这是 Lucene 存储时自动为每个文档分配的值，相当于 Mysql 的主键 id
             * */
            int docID = loopScoreDoc.doc;

            /**通过文档ID从硬盘中读取出对应的文档*/
            Document document = directoryReader.document(docID);
            //
//            /**get方法 获取对应域名的值
//             * 如域名 key 值不存在，返回 null*/
//            System.out.println("doc id：" + docID);
//            System.out.println("fileName:" + document.get("fileName"));
//            System.out.println("fileSize:" + document.get("fileSize"));
//            /**防止内容太多影响阅读，只取前20个字*/
//            System.out.println("fileContext:" + document.get("fileContext").substring(0, 50) + "......");
            articleIds.add(Integer.parseInt(document.get("articleId")));
        }
        readLock.unlock();
        return articleIds;
    }
}