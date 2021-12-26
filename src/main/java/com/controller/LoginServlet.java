package com.controller;

import bean.Static;
import utils.LoginUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/loginServlet")
public class LoginServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get 请求");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("post 请求");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
//        System.out.println(email);
//        System.out.println(password);
        boolean flag = LoginUtil.login(req);
        if (flag == false) {
            System.out.println("登陆失败");
            req.setAttribute("msg", "密码或账号错误");
            req.getRequestDispatcher("/pages/login.jsp").forward(req, resp);
        } else {
            System.out.println("登陆成功");
            Cookie cookie = new Cookie("username", Static.user.getEmail());
            resp.addCookie(cookie);
            req.setAttribute("msg", "");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
