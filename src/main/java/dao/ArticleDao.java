package dao;

import bean.Article;
import bean.Comment;

import java.sql.SQLException;
import java.util.List;

public interface ArticleDao {
    /**
     * 保存文章
     * @param article
     * @return
     */
    boolean savaArticle(Article article);

    /**
     * 保存到审核表
     * @param article
     * @return
     */
    boolean saveCheckArticle(Article article);

    /**
     * 获取该用户的文章
     * @param email
     * @return
     */
    List<Article> getAllArticle(String email);

    /**
     * 通过id获取文章
     * @param id
     * @return
     */
    Article getArticle(String id);

    /**
     * 增加阅读
     * @param id
     */
    void addRead(String id);

    /**
     * 增加点赞
     * @param id
     */
    void addLike(String id);

    /**
     * 取消点赞
     * @param id
     */
    void cancelLike(String id);

    /**
     * 收藏文章
     * @param id
     */
    void addCollect(String id,String user_id);

    /**
     * 取消收藏
     * @param id
     */
    void cancelCollect(String id,String user_id);

    /**
     * 获取收藏文章
     * @param id
     * @return
     */
    List<String> getCollect(String id);

    /**
     * 获取新添加的文章
     * @return
     */
    String getLast();

    /**
     * 修改文章
     * @param article
     * @return
     */
    boolean editArticle(Article article);

    /**
     * 删除博客
     * @param id
     */
    void delete(String id);

    /**
     * 获取所有文章
     * @return
     */
    List<Article> getAllArticle();

    /**
     * 发评论
     * @param vlu
     * @param id
     * @param article
     */
    void sendComment(String vlu, String id,String article);

    /**
     *评论
     * @param id
     * @return
     */
    List<Comment> getComment(String id);

    /**
     * 关注
     * @param id
     * @param fan
     */
    void concern(String id,String fan);

    /**
     * 取消关注
     * @param userid
     * @param fan
     */
    void cancleconcern(String userid, String fan);

    /**
     * 获取前10收藏
     */
    List<Article> getAllArticle_Collect();

    /**
     * 获取当前页的博客
     * @param page
     * @param count
     * @return
     */
    List<Article> getActivePage(String author,String page,String count);

    /**
     * 获取待审核的所有文章
     * @return
     */
    List<Article> getCheckArticles();

    /**
     * 获取待审核的文章
     * @param id
     * @return
     */
    Article getCheckArticle(String id);

    /**
     * 删除审核表中的文章
     * @param id
     */
    void deleteCheck(String id);
}
