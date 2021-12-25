package com.controller;

import net.sf.json.JSONObject;
import service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="RegisterServlet",urlPatterns="/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    private static String code = "";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-type","text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String  method = req.getParameter("action");
        System.out.println("method:"+method);
        if("initCode".equals(method)){
            code="";
            JSONObject jsonObject = new JSONObject();
            resp.getWriter().print(jsonObject);
        }
        if ("sendCode".equals(method)){
            code  = UserService.sendCode(req.getParameter("email"));
//            System.out.println(code);
            return ;
        }
        if("register".equals(method)){
            System.out.println("code:"+code);
            //判断验证码是否正确
            if(!code.equals(req.getParameter("code"))){
                req.setAttribute("msg","验证码错误");
                System.out.println("验证码错误");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("msg","验证码错误");
                resp.getWriter().print(jsonObject);
//                req.getRequestDispatcher("/pages/register.jsp").forward(req, resp);
                return ;
            }
            //判断该邮箱是否已经注册
            if (UserService.exit(req)){
                System.out.println("该邮箱已注册");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("msg","该邮箱已注册");
                resp.getWriter().print(jsonObject);
                return ;
            }
            System.out.println(req.getParameter("email"));
            System.out.println(req.getParameter("password"));
            JSONObject jsonObject = new JSONObject();
            UserService.register(req);
            jsonObject.put("msg","注册成功");
            resp.getWriter().print(jsonObject);
//            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }

}
