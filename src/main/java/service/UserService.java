package service;

import bean.Message;
import bean.Static;
import bean.User;
import dao.UserDao;
import imp.UserDaoImp;
import utils.SendEmail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserService {

    public static boolean forgot(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        System.out.println(email + password);
        UserDao dao = UserDaoImp.getInstance();
        return dao.forgot(email, password);
    }

    public static void login(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserDao dao = UserDaoImp.getInstance();
        User user = dao.login(email, password);
        if (user == null)
            return;
        // 写入session
        Static.user = user;
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
    }

    public static boolean register(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        System.out.println(email + password);
        UserDao dao = UserDaoImp.getInstance();
        return dao.register(email, password);
    }

    public static boolean exit(HttpServletRequest req) {
        String email = req.getParameter("email");
        UserDao dao = UserDaoImp.getInstance();
        return dao.exit(email);
    }

    public static boolean edData(HttpServletRequest req) {
        User user = new User();
        user.setId(req.getParameter("id"));
        user.setHead(req.getParameter("head"));
        user.setEmail(req.getParameter("email"));
        user.setSex(req.getParameter("sex"));
        user.setBirth(req.getParameter("birth"));
        user.setBrief(req.getParameter("brief"));
        user.setName(req.getParameter("name"));
        user.setEmail(Static.user.getEmail());
        UserDao dao = UserDaoImp.getInstance();
        System.out.println("edituser:" + user);
        boolean f = dao.edData(user);
        if (f) {
            Static.user = user;
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
        }
        return false;
    }

    public static User getData(String id) {
        UserDao dao = UserDaoImp.getInstance();
        User user = dao.getUser(id, id);
        return user;
    }

    /**
     * 判断旧密码是否正确
     *
     * @param oldPassword
     * @return
     */
    public static boolean ispassword(String oldPassword) {
        UserDao dao = UserDaoImp.getInstance();
        return dao.isPassword(oldPassword);
    }

    /**
     * 发送验证码
     *
     * @param to
     * @return
     */
    public static String sendCode(String to) {
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        String code1 = String.valueOf(code);
        try {
            SendEmail.sendMail(to, code1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code1;
    }

    public static User getUser(String email) {
        UserDao dao = UserDaoImp.getInstance();
        User user = dao.getUser(email);
        return user;
    }


    public static List<User> getActiveUser(String details) {
        UserDao dao = UserDaoImp.getInstance();
        List<User> list = dao.getActiveUser(details);
        return list;
    }

    public static void deleteUser(String id) {
        UserDao dao = UserDaoImp.getInstance();
        dao.delete(id);
    }


    public static List<User> getAllUser() {
        UserDao dao = UserDaoImp.getInstance();
        List<User> users = dao.getAllUser();
        return users;
    }


    public static List<String> getFan(String id) {
        UserDao dao = UserDaoImp.getInstance();
        List<String> list = dao.getFan(id);
        return list;
    }


    public static List<String> getConcern(String id) {
        UserDao dao = UserDaoImp.getInstance();
        List<String> list = dao.getConcern(id);
        return list;
    }


    public static void changeHead(String path, String id) {
        UserDao dao = UserDaoImp.getInstance();
        dao.changeHead(path, id);
    }


    public static List<User> getAllChatUser(String id) {
        UserDao dao = UserDaoImp.getInstance();
        return dao.getAllChatUser(id);
    }

    public static User getIdUser(String id) {
        UserDao dao = UserDaoImp.getInstance();
        return dao.getIdUser(id);
    }

    public static void addChat(String id, String userid) {
        UserDao dao = UserDaoImp.getInstance();
        dao.addChat(id, userid);
    }

    public static List<Message> getAllMessage(String send, String receive) {
        UserDao dao = UserDaoImp.getInstance();
        return dao.getAllMessage(send, receive);
    }

    public static void addMessage(Message message) {
        UserDao dao = UserDaoImp.getInstance();
        dao.addMessage(message);
    }
}
