package com.controller;

import bean.Article;
import bean.Comment;
import bean.User;
import net.sf.json.JSONObject;
import service.ArticleService;
import service.UserService;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "ArticleServlet", urlPatterns = "/articleServlet")
public class ArticleServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-type", "text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String method = req.getParameter("action");
        System.out.println("method:" + method);

        if ("save-article".equals(method)) {
            ArticleService.addArticle(req);
            req.getRequestDispatcher("/pages/edArticle.jsp").forward(req, resp);

        } else if ("open_write".equals(method)) {
            HttpSession session = req.getSession();
            session.setAttribute("edit", null);
            req.getRequestDispatcher("/pages/edArticle.jsp").forward(req, resp);

        } else if ("save-drafts".equals(method)) {


        } else if ("show-article".equals(method)) {
            System.out.println("id:" + req.getParameter("id"));
            Article article = ArticleService.getArticle(req);
//			System.out.println(article);
            List<Comment> list = ArticleService.getComment(req.getParameter("id"));
            Collections.reverse(list);
            User user = UserService.getUser(article.getAuthor());
            HttpSession session = req.getSession();
            User user1 = (User) session.getAttribute("user");
            if (user1 != null) {
                String id = user1.getId();
                List<String> list1 = UserService.getConcern(id);
                session.setAttribute("fan", list1);
            }
            session.setAttribute("show", article);
            session.setAttribute("show_user", user);
            session.setAttribute("show_comment", list);
            session.setAttribute("show_type","show");
            req.getRequestDispatcher("/pages/show.jsp").forward(req, resp);

        } else if ("addRead".equals(method)) {
            ArticleService.addRead(req.getParameter("id"));
            resp.getWriter().print("");

        } else if ("like".equals(method)) {
            JSONObject jsonObject = new JSONObject();
            ArticleService.addLike(req.getParameter("id"));
            int like = Integer.parseInt(req.getParameter("yes")) + 1;
            jsonObject.put("qaq", like);
            resp.getWriter().print(jsonObject);

        } else if ("cancelLike".equals(method)) {
            JSONObject jsonObject = new JSONObject();
            ArticleService.cancelLike(req.getParameter("id"));
            int like = Integer.parseInt(req.getParameter("yes"));
            jsonObject.put("qaq", like);
            resp.getWriter().print(jsonObject);

        } else if ("collect".equals(method)) {
            JSONObject jsonObject = new JSONObject();
            User user = (User) req.getSession().getAttribute("user");
            ArticleService.addCollect(req.getParameter("id"), user.getId());
            Article article = ArticleService.getArticle(req.getParameter("id"));
            int like = article.getCollect();
            jsonObject.put("qaq", like);
            HttpSession session = req.getSession();
            List<String> list = (List<String>) session.getAttribute("collect");
            list.add(req.getParameter("id"));
            session.setAttribute("collect", list);
            resp.getWriter().print(jsonObject);

        } else if ("cancelCollect".equals(method)) {
            JSONObject jsonObject = new JSONObject();
            User user = (User) req.getSession().getAttribute("user");
            ArticleService.cancelCollect(req.getParameter("id"), user.getId());
            Article article = ArticleService.getArticle(req.getParameter("id"));
            int like = article.getCollect();
            jsonObject.put("qaq", like);
            HttpSession session = req.getSession();
            List<String> list = (List<String>) session.getAttribute("collect");
            for (int a = 0; a < list.size(); a++) {
                if (list.get(a).equals(req.getParameter("id"))) {
                    System.out.println("remove:" + a);
                    list.remove(a);
                }
            }
            session.setAttribute("collect", list);
            resp.getWriter().print(jsonObject);

        } else if ("searchMyBlog".equals(method)) {
            JSONObject jsonObject = new JSONObject();
            List<Article> list = ArticleService.searchMyBlog(req);
            jsonObject.put("article", list);
            resp.getWriter().print(jsonObject);

        } else if ("edit-article".equals(method)) {
            String id = req.getParameter("id");
            System.out.println("id:" + id);
            Article article = ArticleService.getArticle(id);
            HttpSession session = req.getSession();
            session.setAttribute("edit", article);
            req.getRequestDispatcher("/pages/edArticle.jsp").forward(req, resp);

        } else if ("edit".equals(method)) {
            ArticleService.editArticle(req);
            HttpSession session = req.getSession();
            session.setAttribute("edit", null);
            String re = (String) session.getAttribute("return");
            if ("manager".equals(re)) {
                session.setAttribute("return", null);
                req.getRequestDispatcher("/pages/manager.jsp").forward(req, resp);
            } else req.getRequestDispatcher("/pages/Mblog.jsp").forward(req, resp);

        } else if ("delete-article".equals(method)) {
            String id = req.getParameter("id");
            System.out.println("delete" + id);
            ArticleService.delete(id);
            HttpSession session = req.getSession();
            List<Article> list = (List<Article>) session.getAttribute("article");
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() == Integer.valueOf(id)) {
                    list.remove(i);
                }
            }
            req.getRequestDispatcher("/pages/Mblog.jsp").forward(req, resp);

        } else if ("search".equals(method)) {
            String details = req.getParameter("q");
            System.out.println(details);
            List<Article> list = ArticleService.getSearchArticle(details);
            HttpSession session = req.getSession();
            session.setAttribute("search_article", list);
            session.setAttribute("q", details);
            session.setAttribute("search_user", null);
            session.setAttribute("search_type", "blog");
            req.getRequestDispatcher("/pages/search.jsp").forward(req, resp);

        } else if ("searchTag".equals(method)) {
            String details = req.getParameter("q");
            System.out.println(details);
            List<Article> list = ArticleService.getSearchArticle(details, "tag");
            HttpSession session = req.getSession();
            session.setAttribute("search_article", list);
            session.setAttribute("q", details);
            session.setAttribute("search_user", null);
            session.setAttribute("search_type", "tag");
            req.getRequestDispatcher("/pages/search.jsp").forward(req, resp);

        } else if ("searchSort".equals(method)) {
            String details = req.getParameter("q");
            System.out.println(details);
            List<Article> list = ArticleService.getSearchArticle(details, "sort");
            HttpSession session = req.getSession();
            session.setAttribute("search_article", list);
            session.setAttribute("q", details);
            session.setAttribute("search_user", null);
            session.setAttribute("search_type", "sort");
            req.getRequestDispatcher("/pages/search.jsp").forward(req, resp);

        } else if ("searchUser".equals(method)) {
            String details = req.getParameter("q");
            List<User> list = UserService.getActiveUser(details);
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            if (user != null) {
                String id = user.getId();
                List<String> list1 = UserService.getConcern(id);
                session.setAttribute("fan", list1);
            }
            session.setAttribute("search_article", null);
            session.setAttribute("q", details);
            session.setAttribute("search_type", "user");
            session.setAttribute("search_user", list);
            req.getRequestDispatcher("/pages/search.jsp").forward(req, resp);

        } else if ("hisBlog".equals(method)) {
            String email = req.getParameter("email");
            User user = UserService.getUser(email);
            HttpSession session = req.getSession();
            List<Article> list = ArticleService.getAllArticle(email);
            session.setAttribute("showArticleSize",list.size());
            for (int i = list.size() - 1; i >= 5; i--) {
                list.remove(i);
            }

            session.setAttribute("showUser", user);
            session.setAttribute("showArticles", list);
            req.getRequestDispatcher("/pages/blog.jsp").forward(req, resp);

        } else if ("openManager".equals(method)) {
            List<User> UserList = UserService.getAllUser();
            List<Article> list = ArticleService.getAllArticle();
            HttpSession session = req.getSession();
            session.setAttribute("AllUser", UserList);
            session.setAttribute("AllArticle", list);
            session.setAttribute("manager_type", "user");
            req.getRequestDispatcher("/pages/manager.jsp").forward(req, resp);


        } else if ("articleManager".equals(method)) {
            List<Article> list = ArticleService.getAllArticle();
            List<User> UserList = UserService.getAllUser();
            HttpSession session = req.getSession();
            session.setAttribute("AllUser", UserList);
            session.setAttribute("AllArticle", list);
            session.setAttribute("manager_type", "article");
            req.getRequestDispatcher("/pages/manager.jsp").forward(req, resp);

        } else if ("articleCheck".equals(method)){
            List<Article> list = ArticleService.getAllArticle();
            List<User> UserList = UserService.getAllUser();
            List<Article> list1 = ArticleService.getCheckArticles();
            HttpSession session = req.getSession();
            session.setAttribute("checkArticles",list1);
            session.setAttribute("AllUser", UserList);
            session.setAttribute("AllArticle", list);
            session.setAttribute("manager_type", "checkArticle");
            req.getRequestDispatcher("/pages/manager.jsp").forward(req, resp);

        } else if ("editArticle".equals(method)) {
            String id = req.getParameter("id");
            System.out.println("修改的博客 id:" + id);
            Article article = ArticleService.getArticle(id);
            HttpSession session = req.getSession();
            session.setAttribute("edit", article);
            session.setAttribute("return", "manager");
            req.getRequestDispatcher("/pages/edArticle.jsp").forward(req, resp);

        } else if ("sendComment".equals(method)) {
            String vlu = req.getParameter("vlu");
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            String id = user.getId();
            String articleId = req.getParameter("id");
            JSONObject jsonObject = new JSONObject();
            ArticleService.sendComment(vlu, id, articleId);
            jsonObject.put("com", vlu);
            jsonObject.put("user", user);
            resp.getWriter().print(jsonObject);

        } else if ("concern".equals(method)) {
            String userid = req.getParameter("userid");
            String fan = req.getParameter("fan");
            System.out.println(fan + "" + userid);
            ArticleService.concern(userid, fan);
            JSONObject jsonObject = new JSONObject();
            resp.getWriter().print(jsonObject);

        } else if ("cancleconcern".equals(method)) {
            String userid = req.getParameter("userid");
            String fan = req.getParameter("fan");
            System.out.println(fan + "" + userid);
            ArticleService.cancleconcern(userid, fan);
            JSONObject jsonObject = new JSONObject();
            ;
            resp.getWriter().print(jsonObject);

        } else if ("recommend".equals(method)) {
            JSONObject jsonObject = new JSONObject();
            String id = req.getParameter("id");
            Article article = ArticleService.getArticle(id);
            ServletContext application = this.getServletContext();
            application.setAttribute("today_show", article);
            resp.getWriter().print(jsonObject);

        } else if ("Paging".equals(method)) {
            String email = req.getParameter("email");
            User user = UserService.getUser(email);
            String page = req.getParameter("page");
            Integer pg = (Integer.valueOf(page) - 1) * 5;
            List<Article> list = ArticleService.getActivePage(user.getEmail(), String.valueOf(pg), "5");
            HttpSession session = req.getSession();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user", user);
            jsonObject.put("article", list);
            List<Article> list1 = ArticleService.getAllArticle(email);
            session.setAttribute("showArticleSize",list1.size());
            resp.getWriter().print(jsonObject);

        }else if ("readCheckArticle".equals(method)){
            System.out.println("id:" + req.getParameter("id"));
            Article article = ArticleService.getCheckArticle(req);
            User user = UserService.getUser(article.getAuthor());
            HttpSession session = req.getSession();
            User user1 = (User) session.getAttribute("user");
            if (user1 != null) {
                String id = user1.getId();
                List<String> list1 = UserService.getConcern(id);
                session.setAttribute("fan", list1);
            }
            session.setAttribute("show", article);
            session.setAttribute("show_user", user);
            session.setAttribute("show_comment", null);
            session.setAttribute("show_type","check");
            req.getRequestDispatcher("/pages/show.jsp").forward(req, resp);

        }else if ("agreeCheckArticle".equals(method)){
            String id = req.getParameter("id");
            Article article = ArticleService.getCheckArticle(req);
            ArticleService.agreeArticle(article);
            HttpSession session = req.getSession();
            List<Article> list = (List<Article>) session.getAttribute("article");
            article.setId(Integer.parseInt(ArticleService.getLast()));
            list.add(article);
            session.setAttribute("article", list);
            List<Article> list2 = ArticleService.getAllArticle();
            List<User> UserList = UserService.getAllUser();
            List<Article> list1 = ArticleService.getCheckArticles();
            session.setAttribute("checkArticles",list1);
            session.setAttribute("AllUser", UserList);
            session.setAttribute("AllArticle", list2);
            session.setAttribute("manager_type", "checkArticle");
            req.getRequestDispatcher("/pages/manager.jsp").forward(req, resp);

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

}
