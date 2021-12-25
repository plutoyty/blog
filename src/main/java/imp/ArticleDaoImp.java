package imp;

import bean.Article;
import bean.Comment;
import bean.User;
import dao.ArticleDao;
import service.UserService;
import utils.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleDaoImp implements ArticleDao {

    private Connection con;

    private ArticleDaoImp() {
        try {
            con = JdbcUtil.getDataSource().getConnection();
            System.out.println(con);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static ArticleDao instance;

    public static final ArticleDao getInstance() {
        if (instance == null) {
            try {
                instance = new ArticleDaoImp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public boolean savaArticle(Article article) {
        String sql = "insert into article (idx_title,idx_content,idx_time,idx_author,idx_tag,idx_sort,idx_original)values(?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, article.getTitle());
            ps.setString(2, article.getContent());
            ps.setString(3, article.getTime());
            ps.setString(4, article.getAuthor());
            ps.setString(5, article.getTag());
            ps.setString(6, article.getSort());
            ps.setString(7, String.valueOf(article.isOriginal()));
            boolean f = ps.execute();
//            JdbcUtil.close(con,ps);
            return f;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean saveCheckArticle(Article article) {
        String sql = "insert into checkarticle (idx_title,idx_content,idx_time,idx_author,idx_tag,idx_sort,idx_original)values(?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, article.getTitle());
            ps.setString(2, article.getContent());
            ps.setString(3, article.getTime());
            ps.setString(4, article.getAuthor());
            ps.setString(5, article.getTag());
            ps.setString(6, article.getSort());
            ps.setString(7, String.valueOf(article.isOriginal()));
            boolean f = ps.execute();
//            JdbcUtil.close(con,ps);
            return f;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Article> getAllArticle(String email) {
        String sql = "select * from article where idx_author=?";
        List<Article> articles = new ArrayList<>();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Article article = new Article();
                article.setTime(rs.getString("idx_time"));
                article.setTitle(rs.getString("idx_title"));
                article.setId(Integer.valueOf(rs.getString("pk_id")));
                article.setOriginal(rs.getString("idx_original").equals("true"));
                article.setSort(rs.getString("idx_sort"));
                article.setContent(rs.getString("idx_content"));
                article.setTag(rs.getString("idx_tag"));
                article.setAuthor(rs.getString("idx_author"));
                article.setCollect(Integer.parseInt(rs.getString("idx_collect")));
                article.setRead(Integer.parseInt(rs.getString("idx_read")));
                article.setLike(Integer.parseInt(rs.getString("idx_like")));
                articles.add(article);
            }
            return articles;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Article getArticle(String id) {
        String sql = "select * from article where pk_id=?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            Article article = new Article();
            while (rs.next()) {
                article.setTime(rs.getString("idx_time"));
                article.setTitle(rs.getString("idx_title"));
                article.setId(Integer.valueOf(rs.getString("pk_id")));
                article.setOriginal(rs.getString("idx_original").equals("true"));
                article.setSort(rs.getString("idx_sort"));
                article.setContent(rs.getString("idx_content"));
                article.setTag(rs.getString("idx_tag"));
                article.setAuthor(rs.getString("idx_author"));
                article.setRead(Integer.parseInt(rs.getString("idx_read")));
                article.setCollect(Integer.parseInt(rs.getString("idx_collect")));
                article.setLike(Integer.parseInt(rs.getString("idx_like")));
            }
            return article;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void addRead(String id) {
        Article article = getArticle(id);
        String sql = "update article set idx_read=? where pk_id=?";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(1 + article.getRead()));
            ps.setString(2, id);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void addLike(String id) {
        Article article = getArticle(id);
        String sql = "update article set idx_like=? where pk_id=?";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(1 + article.getLike()));
            ps.setString(2, id);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void cancelLike(String id) {
        Article article = getArticle(id);
        String sql = "update article set idx_like=? where pk_id=?";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(article.getLike() - 1));
            ps.setString(2, id);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void addCollect(String id, String user_id) {
        Article article = getArticle(id);
        String sql = "update article set idx_collect=? where pk_id=?";
        String sql1 = "insert into collect (idx_collect,idx_user_id)values(?,?)";
        PreparedStatement ps;
        PreparedStatement ps1;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(1 + article.getCollect()));
            ps.setString(2, id);
            ps.execute();
            ps1 = con.prepareStatement(sql1);
            ps1.setString(1, id);
            ps1.setString(2, user_id);
            ps1.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void cancelCollect(String id, String user_id) {
        Article article = getArticle(id);
        String sql = "update article set idx_collect=? where pk_id=?";
        String sql1 = "delete from collect where idx_user_id = ? and idx_collect = ?;";
        PreparedStatement ps;
        PreparedStatement ps1;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(article.getCollect() - 1));
            ps.setString(2, id);
            ps.execute();
            ps1 = con.prepareStatement(sql1);
            ps1.setString(1, user_id);
            ps1.setString(2, id);
            ps1.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<String> getCollect(String id) {
        String sql1 = "select * from collect where idx_user_id=?";
        PreparedStatement ps;
        List<String> collects = new ArrayList<>();
        try {
            ps = con.prepareStatement(sql1);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                collects.add(rs.getString("idx_collect"));
            }
            return collects;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public String getLast() {
        String sql = "select * from article order by pk_id desc limit 1";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("pk_id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean editArticle(Article article) {
        String id = String.valueOf(article.getId());
        String sql = "update article set idx_tag=?,idx_title=?,idx_content=?,idx_original=?,idx_sort=? where pk_id=?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(2, article.getTitle());
            ps.setString(3, article.getContent());
            ps.setString(1, article.getTag());
            ps.setString(6, String.valueOf(article.getId()));
            ps.setString(5, article.getSort());
            ps.setString(4, String.valueOf(article.isOriginal()));
            boolean f = ps.execute();
            return f;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public void delete(String id) {
        String sql = "delete from article where pk_id = ?;";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Article> getAllArticle() {
        String sql = "select * from article";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Article> list = new ArrayList<>();
            while (rs.next()) {
                Article article = new Article();
                article.setTime(rs.getString("idx_time"));
                article.setTitle(rs.getString("idx_title"));
                article.setId(Integer.valueOf(rs.getString("pk_id")));
                article.setOriginal(rs.getString("idx_original").equals("true"));
                article.setSort(rs.getString("idx_sort"));
                article.setContent(rs.getString("idx_content"));
                article.setTag(rs.getString("idx_tag"));
                article.setAuthor(rs.getString("idx_author"));
                article.setCollect(Integer.parseInt(rs.getString("idx_collect")));
                article.setRead(Integer.parseInt(rs.getString("idx_read")));
                article.setLike(Integer.parseInt(rs.getString("idx_like")));
                list.add(article);
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void sendComment(String vlu, String id, String articleId) {
        String sql = "insert into artclecomment (idx_article_id,idx_from_id,idx_comment)values(?,?,?)";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, articleId);
            ps.setString(2, id);
            ps.setString(3, vlu);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Comment> getComment(String id) {
        String sql = "select * from artclecomment where idx_article_id=?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            List<Comment> list = new ArrayList<>();
            while (rs.next()) {
                Comment comment = new Comment();
                String userId = rs.getString("idx_from_id");
                User user = UserService.getData(userId);
                comment.setUser(user);
                comment.setComment(rs.getString("idx_comment"));
                list.add(comment);
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void concern(String id, String fan) {
        String sql = "insert into concern (idx_fan,idx_user)values(?,?)";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fan);
            ps.setString(2, id);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void cancleconcern(String userid, String fan) {
        String sql = "delete from concern where idx_fan=? and idx_user=?";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fan);
            ps.setString(2, userid);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Article> getAllArticle_Collect() {
        String sql = "SELECT * FROM article  order by idx_like DESC LIMIT 0,10";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Article> list = new ArrayList<>();
            while (rs.next()) {
                Article article = new Article();
                article.setTime(rs.getString("idx_time"));
                article.setTitle(rs.getString("idx_title"));
                article.setId(Integer.valueOf(rs.getString("pk_id")));
                article.setOriginal(rs.getString("idx_original").equals("true"));
                article.setSort(rs.getString("idx_sort"));
                article.setContent(rs.getString("idx_content"));
                article.setTag(rs.getString("idx_tag"));
                article.setAuthor(rs.getString("idx_author"));
                article.setCollect(Integer.parseInt(rs.getString("idx_collect")));
                article.setRead(Integer.parseInt(rs.getString("idx_read")));
                article.setLike(Integer.parseInt(rs.getString("idx_like")));
                list.add(article);
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Article> getActivePage(String author, String page, String count) {
        String sql = "SELECT *  FROM article where idx_author=? LIMIT " + page + "," + count + " ";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, author);
            ResultSet rs = ps.executeQuery();
            List<Article> list = new ArrayList<>();
            while (rs.next()) {
                Article article = new Article();
                article.setTime(rs.getString("idx_time"));
                article.setTitle(rs.getString("idx_title"));
                article.setId(Integer.valueOf(rs.getString("pk_id")));
                article.setOriginal(rs.getString("idx_original").equals("true"));
                article.setSort(rs.getString("idx_sort"));
                article.setContent(rs.getString("idx_content"));
                article.setTag(rs.getString("idx_tag"));
                article.setAuthor(rs.getString("idx_author"));
                article.setCollect(Integer.parseInt(rs.getString("idx_collect")));
                article.setRead(Integer.parseInt(rs.getString("idx_read")));
                article.setLike(Integer.parseInt(rs.getString("idx_like")));
                list.add(article);
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Article> getCheckArticles() {
        String sql = "select * from checkarticle";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Article> list = new ArrayList<>();
            while (rs.next()) {
                Article article = new Article();
                article.setTime(rs.getString("idx_time"));
                article.setTitle(rs.getString("idx_title"));
                article.setId(Integer.valueOf(rs.getString("pk_id")));
                article.setOriginal(rs.getString("idx_original").equals("true"));
                article.setSort(rs.getString("idx_sort"));
                article.setContent(rs.getString("idx_content"));
                article.setTag(rs.getString("idx_tag"));
                article.setAuthor(rs.getString("idx_author"));
                article.setCollect(Integer.parseInt(rs.getString("idx_collect")));
                article.setRead(Integer.parseInt(rs.getString("idx_read")));
                article.setLike(Integer.parseInt(rs.getString("idx_like")));
                list.add(article);
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Article getCheckArticle(String id) {
        String sql = "select * from checkarticle where pk_id=?";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            Article article = new Article();
            while (rs.next()) {
                article.setTime(rs.getString("idx_time"));
                article.setTitle(rs.getString("idx_title"));
                article.setId(Integer.valueOf(rs.getString("pk_id")));
                article.setOriginal(rs.getString("idx_original").equals("true"));
                article.setSort(rs.getString("idx_sort"));
                article.setContent(rs.getString("idx_content"));
                article.setTag(rs.getString("idx_tag"));
                article.setAuthor(rs.getString("idx_author"));
                article.setRead(Integer.parseInt(rs.getString("idx_read")));
                article.setCollect(Integer.parseInt(rs.getString("idx_collect")));
                article.setLike(Integer.parseInt(rs.getString("idx_like")));
            }
            return article;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteCheck(String id) {
        String sql = "delete from checkarticle where pk_id =?;";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArticleDao dao = ArticleDaoImp.getInstance();
        System.out.println(dao.getCheckArticles());
    }
}
