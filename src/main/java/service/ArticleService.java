package service;

import bean.Article;
import bean.Comment;
import bean.Static;
import dao.ArticleDao;
import imp.ArticleDaoImp;
import utils.ArticlesSearch;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleService {

    public static boolean addArticle(HttpServletRequest req) {
        ArticleDao dao = ArticleDaoImp.getInstance();
        Article article = new Article();
        int num = Integer.valueOf(req.getParameter("number"));
        String tag = "";
        for (int i = 1; i <= num; i++) {
            String s = "tag-" + i;
            tag += "{" + req.getParameter(s) + "}";
        }
//        System.out.println(tag);
        article.setTag(tag);
        article.setContent(req.getParameter("content"));
        article.setTitle(req.getParameter("title"));
        article.setSort(req.getParameter("sort"));
        if (article.getSort().equals("")) {
            article.setSort("默认分栏");
        }
        article.setOriginal(req.getParameter("original").equals("true") ? true : false);
        article.setAuthor(Static.user.getEmail());
        article.setTime(getDate());
//        HttpSession session = req.getSession();
//        List<Article> list = (List<Article>) session.getAttribute("article");
        boolean f = dao.saveCheckArticle(article);
//        article.setId(Integer.parseInt(ArticleService.getLast()));
//        list.add(article);
//        session.setAttribute("article", list);
        return f;
    }

    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public static List<Article> getAllArticle(HttpServletRequest req) {
        List<Article> articles;
        ArticleDao dao = ArticleDaoImp.getInstance();
        String email = req.getParameter("email");
        articles = dao.getAllArticle(email);
        return articles;
    }

    public static List<Article> getCheckArticles(){
        ArticleDao dao = ArticleDaoImp.getInstance();
        return dao.getCheckArticles();
    }

    public static List<Article> getAllArticle(String email) {
        List<Article> articles;
        ArticleDao dao = ArticleDaoImp.getInstance();
        articles = dao.getAllArticle(email);
        return articles;
    }

    public static Article getArticle(HttpServletRequest req) {
        String id = req.getParameter("id");
        Article article = null;
        ArticleDao dao = ArticleDaoImp.getInstance();
        article = dao.getArticle(id);
        return article;
    }

    public static Article getArticle(String id) {
        Article article = null;
        ArticleDao dao = ArticleDaoImp.getInstance();
        article = dao.getArticle(id);
        return article;
    }

    public static void addRead(String id) {
        ArticleDao dao = ArticleDaoImp.getInstance();
        dao.addRead(id);
    }

    public static void addLike(String id) {
        ArticleDao dao = ArticleDaoImp.getInstance();
        dao.addLike(id);
    }

    public static void cancelLike(String id) {
        ArticleDao dao = ArticleDaoImp.getInstance();
        dao.cancelLike(id);
    }

    public static void addCollect(String id, String user_id) {
        ArticleDao dao = ArticleDaoImp.getInstance();
        dao.addCollect(id, user_id);
    }

    public static void cancelCollect(String id, String user_id) {
        ArticleDao dao = ArticleDaoImp.getInstance();
        dao.cancelCollect(id, user_id);
    }


    public static List<String> getCollect(String id) {
        ArticleDao dao = ArticleDaoImp.getInstance();
        return dao.getCollect(id);
    }

    public static String getLast() {
        ArticleDao dao = ArticleDaoImp.getInstance();
        return dao.getLast();
    }

    public static boolean editArticle(HttpServletRequest req) {
        ArticleDao dao = ArticleDaoImp.getInstance();
        Article article = new Article();
        int num = Integer.valueOf(req.getParameter("number"));
        String tag = "";
        for (int i = 1; i <= num; i++) {
            String s = "tag-" + i;
            tag += "{" + req.getParameter(s) + "}";
        }
//        System.out.println(tag);
        article.setId(Integer.parseInt(req.getParameter("id")));
        article.setTag(tag);
        article.setContent(req.getParameter("content"));
        article.setTitle(req.getParameter("title"));
        article.setSort(req.getParameter("sort"));
        article.setOriginal(req.getParameter("original").equals("true") ? true : false);
        HttpSession session = req.getSession();
        List<Article> list = (List<Article>) session.getAttribute("article");
        boolean f = dao.editArticle(article);
        Article article1 = ArticleService.getArticle(String.valueOf(article.getId()));
        for (int i = 0; i < list.size(); i++) {
            if (article.getId() == list.get(i).getId()) {
                list.set(i, article1);
            }
        }
        session.setAttribute("article", list);
        return f;
    }

    public static void delete(String id) {
        ArticleDao dao = ArticleDaoImp.getInstance();
        dao.delete(id);
    }


    public static List<Article> searchMyBlog(HttpServletRequest req) {
        String original = req.getParameter("original");
        String year = req.getParameter("year");
        String month = req.getParameter("month");
        String sort = req.getParameter("sort");
        String key = req.getParameter("key");
        System.out.println("original:" + original);
        System.out.println(year);
        System.out.println(month);
        System.out.println(sort);
        System.out.println(key);
        List<Article> list1 = (List<Article>) req.getSession().getAttribute("article");
        List<Article> list = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            list.add(list1.get(i));
        }

        if (original.equals("原创")) {
            for (int i = list.size() - 1; i >= 0; i--) {
                if (list.get(i).isOriginal() == false) {
                    list.remove(i);
                }
            }
        }
        if (original.equals("转载")) {
            for (int i = list.size() - 1; i >= 0; i--) {
                if (list.get(i).isOriginal() == true) {
                    list.remove(i);
                }
            }
        }
        if (year != "") {
            for (int i = list.size() - 1; i >= 0; i--) {
                if (!list.get(i).getTime().substring(0, 4).equals(year)) {
                    list.remove(i);
                }
            }
        }
        if (month != "") {
            for (int i = list.size() - 1; i >= 0; i--) {
                if (!(Integer.valueOf(list.get(i).getTime().substring(5, 7)) == Integer.valueOf(month))) {
                    list.remove(i);
                }
            }
        }
        if (sort != "") {
            for (int i = list.size() - 1; i >= 0; i--) {
                if (!list.get(i).getSort().equals(sort)) {
                    list.remove(i);
                }
            }
        }
        if (key != "") {
            for (int i = list.size() - 1; i >= 0; i--) {
                boolean f = false;
                Article article = list.get(i);
                List<String> list12 = getTags(article.getTag());
                for (String var : list12) {
                    if (key.equals(var)) {
                        f = true;
                    }
                }
                if (!f) {
                    list.remove(i);
                }
            }
        }
        for (Article a : list) {
            System.out.println(a.getId());
        }
        return list;
    }


    public static List<String> getTags(String s) {
        String a = s;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == '{') {
                i++;
                String w = "";
                int j = i;
                for (; j < a.length(); j++) {
                    if (a.charAt(j) != '}')
                        w += a.charAt(j);
                    else break;
                }
                list.add(w);
                i = j;
            }
        }
        return list;
    }


    public static List<Article> getSearchArticle(String details) {
        ArticlesSearch.createIndex();
        List<String> list_id = ArticlesSearch.searchData(details);
        List<Article> list = new ArrayList<>();
        for (String id : list_id) {
            Article article = getArticle(id);
            list.add(article);
        }
        return list;
    }


    public static List<Article> getSearchArticle(String details, String query) {
        ArticlesSearch.createIndex();
        List<String> list_id = ArticlesSearch.searchData(details, query);
        List<Article> list = new ArrayList<>();
        for (String id : list_id) {
            Article article = getArticle(id);
            list.add(article);
        }
        return list;
    }

    public static List<Article> getAllArticle() {
        ArticleDao dao = ArticleDaoImp.getInstance();
        List<Article> list = dao.getAllArticle();
        return list;
    }

    public static void sendComment(String vlu, String id, String articleId) {
        ArticleDao dao = ArticleDaoImp.getInstance();
        dao.sendComment(vlu, id, articleId);
    }

    public static List<Comment> getComment(String id) {
        ArticleDao dao = ArticleDaoImp.getInstance();
        List<Comment> list = dao.getComment(id);
        return list;
    }
    public static void concern(String user,String fan){
        ArticleDao dao = ArticleDaoImp.getInstance();
        dao.concern(user,fan);
    }


    public static void cancleconcern(String userid, String fan) {
        ArticleDao dao = ArticleDaoImp.getInstance();
        dao.cancleconcern(userid,fan);
    }



    public static List<Article> getRanking() {
        List<Article> list = ArticleService.getAllArticle_Collect();
        return list;
    }

    private static List<Article> getAllArticle_Collect() {
        ArticleDao dao = ArticleDaoImp.getInstance();
        return dao.getAllArticle_Collect();
    }



    public static List<Article> getActivePage(String email, String valueOf, String s){
        ArticleDao dao = ArticleDaoImp.getInstance();
        return dao.getActivePage(email,valueOf,s);
    }



    public static Article getCheckArticle(HttpServletRequest req) {
        String id = req.getParameter("id");
        Article article ;
        ArticleDao dao = ArticleDaoImp.getInstance();
        article = dao.getCheckArticle(id);
        return article;
    }



    public static void agreeArticle(Article article) {
        ArticleDao dao = ArticleDaoImp.getInstance();
        dao.savaArticle(article);
        dao.deleteCheck(String.valueOf(article.getId()));
    }

    public static void main(String[] args) {
        ArticleDao dao = ArticleDaoImp.getInstance();
        System.out.println(dao.getActivePage("2631223275@qq.com", "5", "5"));
    }
}
