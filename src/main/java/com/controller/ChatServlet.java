package com.controller;

import bean.Message;
import bean.User;
import service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "ChatServlet", urlPatterns = "/chatServlet")
public class ChatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("action");
        System.out.println("action:"+method);
        if ("chat".equals(method)){
            String id = req.getParameter("id");
            User user = (User) req.getSession().getAttribute("user");
            UserService.addChat(id,user.getId());
            List<User> userList = UserService.getAllChatUser(user.getId());
            List<Message> messageList  = UserService.getAllMessage(id,user.getId());
            User chatUser = UserService.getIdUser(id);
            HttpSession session =  req.getSession();
            session.setAttribute("chat_userList",userList);
            session.setAttribute("chat_message",messageList);
            session.setAttribute("chat_user",chatUser);
            req.getRequestDispatcher("/pages/chat.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doGet(req, resp);
    }
}
