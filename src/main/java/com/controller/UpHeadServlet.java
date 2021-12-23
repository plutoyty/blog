package com.controller;

import bean.User;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "UpHeadServlet", urlPatterns = "/upHeadServlet")
public class UpHeadServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("text/html;charset=utf-8");
//            创建DiskFileItemFactory工厂对象
            DiskFileItemFactory factory = new DiskFileItemFactory();
//            设置文件缓存目录，如果该文件夹不存在则创建一个
            File f = new File("D:/TempFolder");
            if (!f.exists()) {
                f.mkdirs();
            }
            factory.setRepository(f);
//            创建ServletFileUpload对象
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
//            设置字符编码
            fileUpload.setHeaderEncoding("utf-8");
//            解析request，将form表单的各个字段封装为FileItem对象
            List<FileItem> fileItems = null;
            fileItems = fileUpload.parseRequest(req);
//            获取字符流
            PrintWriter writer = resp.getWriter();
//            遍历List集合
            for (FileItem fileItem : fileItems) {
//            判断是否为普通字段
                if (fileItem.isFormField()) {
//                    获取字段名称
                    String name = fileItem.getFieldName();
                    if (name.equals("name")) {
//                        如果字段值不为空
                        if (!fileItem.getString().equals("")) {
                            String value = fileItem.getString("utf-8");
//                            writer.print("上传者：" + value + "<br />");
                        }
                    }
                } else {
                    //获取上传的文件名
                    String filename = fileItem.getName();
//                    处理上传文件
                    if (filename != null && filename != "") {
//                        writer.print("上传的文件名称是：" + filename + "<br />");
//                        保持文件名唯一
                        filename = UUID.randomUUID().toString() + "_" + filename;
                        String webpath = "/upload/";
//                        创建文件路径
                        String filepath = getServletContext().getRealPath(webpath + filename);
//                        System.out.println(filename);
                        String filepath1 = "D:\\学习\\javaweb项目\\blogHead\\"+filename;
//                        System.out.println(filepath1);
                        //创建File对象
                        File file = new File(filepath1);
                        //创建文件夹
                        file.getParentFile().mkdirs();
                        //创建文件
                        file.createNewFile();
                        //获取上传文件流
                        InputStream in = fileItem.getInputStream();
//                        使用 FileOutputStream打开服务器端的上传文件
                        FileOutputStream out = new FileOutputStream(file);
//                        流的对拷
                        byte[] bytes = new byte[1024];//每次读取一个字节
                        int len;
//                        开始读取上传文件的字节，并将其输出到服务器端的上传文件输出流中
                        while ((len = in.read(bytes)) > 0)
                            out.write(bytes, 0, len);
                        in.close();
                        out.close();
                        fileItem.delete();
//                        writer.print("文件上传成功！");
                        User user = (User) req.getSession().getAttribute("user");
                        user.setHead("/blogHead/"+filename);
                        String pa = "/blogHead/"+filename;
                        req.getSession().setAttribute("user",user);
                        UserService.changeHead(pa,user.getId());
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name",filename);
                        resp.getWriter().print(jsonObject);
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
