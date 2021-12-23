package utils;

import bean.Article;
import bean.Static;
import bean.User;
import dao.UserDao;
import imp.UserDaoImp;
import service.ArticleService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

public class LoginUtil {

    public static boolean login(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserDao dao = UserDaoImp.getInstance();
        User user = dao.login(email, password);
        if (user == null)
            return false;
        // 写入session
        Static.user = user;
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        List<String> collects  = ArticleService.getCollect(user.getId());
        session.setAttribute("collect",collects);
        session.setAttribute("edit",null);
        List<Article> articles = ArticleService.getAllArticle(req);
        Collections.reverse(articles);
        session.setAttribute("article",articles);
        return true;
    }

    public static void main(String[] args) {
        UserDao dao = UserDaoImp.getInstance();
        UserDao dao1 = UserDaoImp.getInstance();
        dao1.exit("11111");
        dao.login("2631223275@qq.com","qq123456");
    }

}
