package imp;

import bean.Message;
import bean.Static;
import bean.User;
import dao.UserDao;
import service.UserService;
import utils.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImp implements UserDao {

    private Connection con;

    public UserDaoImp() {
        try {
            con = JdbcUtil.getDataSource().getConnection();
            System.out.println(con);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static UserDao instance;

    public static final UserDao getInstance() {
        if (instance == null) {
            try {
                instance = new UserDaoImp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public boolean register(String email, String password) {
        String sql = "insert into user (idx_email,idx_password,idx_name,idx_head)values(?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, "bolg" + email);
            ps.setString(4, "/blogHead/user.png");
            boolean f = ps.execute();
//            JdbcUtil.close(con,ps);
            return f;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public User login(String email, String password) {
        User user = null;
        String sql = "select * from user where idx_email=? and idx_password=? ";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            // bean导入
            while (rs.next()) {
                user = new User();
                user.setHead(rs.getString("idx_head"));
                user.setId(rs.getString("pk_id"));
                user.setName(rs.getString("idx_name"));
                user.setEmail(rs.getString("idx_email"));
                user.setPassword(rs.getString("idx_password"));
                user.setBirth(rs.getString("idx_birth"));
                user.setSex(rs.getString("idx_sex"));
                user.setBrief(rs.getString("idx_brief"));
            }
//            JdbcUtil.close(con,rs, ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean exit(String email) {
        User user = null;
        String sql = "select * from user where idx_email=?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
//            JdbcUtil.close(con,rs, ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean forgot(String email, String password) {
        String sql = "update user set idx_password=? where idx_email=?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(2, email);
            ps.setString(1, password);
            boolean f = ps.executeUpdate() != 0;
//            JdbcUtil.close(con,ps);
            return f;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean edData(User user) {
        String sql = "update user set idx_name=?,idx_brief=?,idx_sex=?,idx_birth=? where pk_id=?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(2, user.getBrief());
            ps.setString(1, user.getName());
            ps.setString(3, user.getSex());
            ps.setString(4, user.getBirth());
            ps.setString(5, user.getId());
            boolean f = ps.executeUpdate() != 0;
//            JdbcUtil.close(con,ps);
            return f;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isPassword(String oldPassword) {
        String sql = "select * from user where idx_email=? and idx_password=? ";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, Static.user.getEmail());
            ps.setString(2, oldPassword);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUser(String email) {
        String sql = "select * from user where idx_email=?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            User user = new User();
            while (rs.next()) {
                user.setHead(rs.getString("idx_head"));
                user.setId(rs.getString("pk_id"));
                user.setName(rs.getString("idx_name"));
                user.setEmail(rs.getString("idx_email"));
                user.setPassword(rs.getString("idx_password"));
                user.setBirth(rs.getString("idx_birth"));
                user.setSex(rs.getString("idx_sex"));
                user.setBrief(rs.getString("idx_brief"));
            }
            return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User getIdUser(String id) {
        String sql = "select * from user where pk_id=?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            User user = new User();
            while (rs.next()) {
                user.setHead(rs.getString("idx_head"));
                user.setId(rs.getString("pk_id"));
                user.setName(rs.getString("idx_name"));
                user.setEmail(rs.getString("idx_email"));
                user.setPassword(rs.getString("idx_password"));
                user.setBirth(rs.getString("idx_birth"));
                user.setSex(rs.getString("idx_sex"));
                user.setBrief(rs.getString("idx_brief"));
            }
            return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUser(String id, String id1) {
        String sql = "select * from user where pk_id=?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            User user = new User();
            while (rs.next()) {
                user.setHead(rs.getString("idx_head"));
                user.setId(rs.getString("pk_id"));
                user.setName(rs.getString("idx_name"));
                user.setEmail(rs.getString("idx_email"));
                user.setPassword(rs.getString("idx_password"));
                user.setBirth(rs.getString("idx_birth"));
                user.setSex(rs.getString("idx_sex"));
                user.setBrief(rs.getString("idx_brief"));
            }
            return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getActiveUser(String details) {
        List<User> list = new ArrayList<>();
        String sql = "select * from user where idx_name like '%" + details + "%'";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setHead(rs.getString("idx_head"));
                user.setId(rs.getString("pk_id"));
                user.setName(rs.getString("idx_name"));
                user.setEmail(rs.getString("idx_email"));
                user.setPassword(rs.getString("idx_password"));
                user.setBirth(rs.getString("idx_birth"));
                user.setSex(rs.getString("idx_sex"));
                user.setBrief(rs.getString("idx_brief"));
                list.add(user);
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(String id) {
        String sql = "delete from user where pk_id=?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUser() {
        String sql = "select * from user";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<User> list = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setHead(rs.getString("idx_head"));
                user.setId(rs.getString("pk_id"));
                user.setName(rs.getString("idx_name"));
                user.setEmail(rs.getString("idx_email"));
                user.setPassword(rs.getString("idx_password"));
                user.setBirth(rs.getString("idx_birth"));
                user.setSex(rs.getString("idx_sex"));
                user.setBrief(rs.getString("idx_brief"));
                list.add(user);
            }
            list.remove(0);
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getFan(String id) {
        String sql = "select * from concern where idx_user=?";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            List<String> list = new ArrayList<>();
            while (rs.next()) {
                String s = rs.getString("idx_fan");
                list.add(s);
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getConcern(String id) {
        String sql = "select * from concern where idx_fan=?";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            List<String> list = new ArrayList<>();
            while (rs.next()) {
                String s = rs.getString("idx_user");
                list.add(s);
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void changeHead(String path, String id) {

        String sql = "update user set idx_head=? where pk_id=?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, path);
            ps.setString(2, id);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<User> getAllChatUser(String id) {
        String sql = "select * from msglist where (idx_user1=? or idx_user2=?)";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, id);
            ResultSet rs = ps.executeQuery();
            List<User> list = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                String s = rs.getString("idx_user1");
                if (s.equals(id)) {
                    s = rs.getString("idx_user2");
                }
                user = UserService.getIdUser(s);
                list.add(user);
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void addChat(String id, String userid) {
        String sql = "select * from msglist where (idx_user1=? and idx_user2=?) or (idx_user1=? and idx_user2=?)";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, userid);
            ps.setString(3, userid);
            ps.setString(4, id);
            ResultSet rs = ps.executeQuery();
            boolean flag;
            if (rs.next()) {
                flag = true;
            } else {
                flag = false;
            }
            String sql1 = "insert into msglist (idx_user1,idx_user2) values(?,?)";
            PreparedStatement ps1;
            if (flag == true) {
                System.out.println(id + "已添加" + userid + "已添加到聊天！");
            } else {
                ps1 = con.prepareStatement(sql1);
                ps1.setString(1, id);
                ps1.setString(2, userid);
                ps1.execute();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Message> getAllMessage(String send, String receive) {
        String sql = "select * from message where (idx_send=? and idx_receive=?) or (idx_send=? and idx_receive=?)";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, send);
            ps.setString(2, receive);
            ps.setString(3,receive);
            ps.setString(4,send);
            ResultSet rs = ps.executeQuery();
            List<Message> list = new ArrayList<>();
            while (rs.next()) {
                Message message = new Message();
                message.setSend(rs.getString("idx_send"));
                message.setContent(rs.getString("idx_content"));
                message.setReceive(rs.getString("idx_receive"));
                message.setTime(rs.getString("idx_time"));
                list.add(message);
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void addMessage(Message message) {
        String sql =  "insert into message (idx_send,idx_receive,idx_content,idx_time)values(?,?,?,?)";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,message.getSend());
            ps.setString(2,message.getReceive());
            ps.setString(3,message.getContent());
            ps.setString(4,message.getTime());
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UserDao dao = UserDaoImp.getInstance();
        System.out.println(dao.getAllMessage("2", "3"));
    }
}
