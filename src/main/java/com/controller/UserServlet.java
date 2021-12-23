package com.controller;

import bean.Article;
import bean.Message;
import bean.User;
import net.sf.json.JSONObject;
import service.ArticleService;
import service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = "/userServlet")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-type", "text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String method = req.getParameter("action");
        System.out.println("method:" + method);
        if ("editData".equals(method)) {
            boolean f = UserService.edData(req);
            req.getRequestDispatcher("/pages/personal.jsp").forward(req, resp);

        } else if ("edPassword".equals(method)) {
            boolean flag = UserService.ispassword(req.getParameter("oldPassword"));
            if (!flag) {
                req.setAttribute("msg", "密码错误");
            } else {
                if (UserService.forgot(req)) {
                    req.setAttribute("msg", "修改成功");
                } else {
                    req.setAttribute("msg", "修改失败");
                }
            }
            req.getRequestDispatcher("/pages/personal.jsp").forward(req, resp);

        } else if ("deleteUser".equals(method)) {
            String id = req.getParameter("id");
            UserService.deleteUser(id);
            HttpSession session = req.getSession();
            List<User> UserList = UserService.getAllUser();
            List<Article> list = ArticleService.getAllArticle();
            session.setAttribute("AllUser", UserList);
            session.setAttribute("AllArticle",list);
            session.setAttribute("manager_type","user");
            req.getRequestDispatcher("/pages/manager.jsp").forward(req, resp);

        }
        else if ("checkUser".equals(method)){
            String id = req.getParameter("id");
            User user = UserService.getData(id);
            HttpSession session = req.getSession();
            session.setAttribute("userData",user);
            req.getRequestDispatcher("/pages/editdata.jsp").forward(req, resp);

        }
        else if ("cancleEditUser".equals(method)){
            req.getRequestDispatcher("/pages/manager.jsp").forward(req, resp);

        }
        else if ("editUser".equals(method)){
            UserService.edData(req);
            HttpSession session = req.getSession();
            List<User> UserList = UserService.getAllUser();
            session.setAttribute("AllUser", UserList);
            req.getRequestDispatcher("/pages/manager.jsp").forward(req, resp);

        }
        else if ("logout".equals(method)){
            HttpSession session = req.getSession();
            session.setAttribute("user",null);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);

        }
        else if ("chatMsg".equals(method)){
            JSONObject jsonObject = new JSONObject();
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            Message message = new Message();
            String msg = req.getParameter("msg");
            String receive = req.getParameter("receive");
            message.setReceive(receive);
            message.setContent(msg);
            message.setSend(user.getId());
            message.setTime("");
            UserService.addMessage(message);
            jsonObject.put("message",message);
            resp.getWriter().print(jsonObject);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
