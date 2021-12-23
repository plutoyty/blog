package com.controller;

import net.sf.json.JSONObject;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(name="ForgotServlet",urlPatterns="/forgot")
public class ForgotServlet extends HttpServlet {
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
            return ;
        }
        if("forgot".equals(method)){
            System.out.println("code:"+code);
            //判断验证码是否正确
            if(!code.equals(req.getParameter("code"))){
                System.out.println("验证码错误");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("msg","验证码错误");
                resp.getWriter().print(jsonObject);
                return ;
            }
            JSONObject jsonObject = new JSONObject();
            boolean f = UserService.forgot(req);
            if(f==true){
                jsonObject.put("msg","修改密码成功");
            }else {
                jsonObject.put("msg","修改密码失败");
            }
            resp.getWriter().print(jsonObject);
        }
    }
}
