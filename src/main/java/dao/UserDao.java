package dao;

import bean.Message;
import bean.User;

import java.util.List;

public interface UserDao {
    /**
     * 用户注册
     * @param email
     * @param password
     * @return
     */
    boolean register(String email, String password);

    /**
     * 用户登录
     * @param email
     * @param password
     * @return
     */
    User login(String email,String password);

    /**
     * 检查是否已经注册
     * @param email
     * @return
     */
    boolean exit(String email);

    /**
     * 修改密码
     * @param email
     * @param password
     * @return
     */
    boolean forgot(String email,String password);

    /**
     * 修改资料
     * @param user
     * @return
     */
    boolean edData(User user);

    /**
     * 修改密码中判断修密码是否正确
     * @param oldPassword
     * @return
     */
    boolean isPassword(String oldPassword);

    /**
     * 通过邮箱获取user信息
     * @param email
     * @return
     */
    User getUser(String email);

    /**
     * 通过id获取user信息
     * @param id
     * @return
     */
    User getIdUser(String id);
    /**
     * 通过id获取个人信息
     * @param id
     * @param id1
     * @return
     */
    User getUser(String id,String id1);

    /**
     * 查询用户
     * @param details
     * @return
     */
    List<User> getActiveUser(String details);

    /**
     * 删除用户
     * @param id
     */
    void delete(String id);

    /**
     * 获取所有用户
     * @return
     */
    List<User> getAllUser();

    /**
     * 获取所有粉丝
     * @param id
     * @return
     */
    List<String> getFan(String id);

    /**
     * 获取所有关注对象
     * @param id
     * @return
     */
    List<String> getConcern(String id);

    /**
     * 更换头像
     * @param path
     */
    void changeHead(String path,String id);

    /**
     * 获取当前所有的聊天对象
     * @param id
     * @return
     */
    List<User> getAllChatUser(String id);

    /**
     * 添加聊天的对象
     * @param id
     * @param userid
     */
    void addChat(String id, String userid);

    /**
     * 获取
     * @param send
     * @param receive
     * @return
     */
    List<Message> getAllMessage(String send,String receive);

    /**
     * 添加消息
     * @param message
     */
    void addMessage(Message message);
}
