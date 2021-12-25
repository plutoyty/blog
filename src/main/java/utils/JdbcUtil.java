package utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtil {

    private static DataSource ds;
    private static DruidDataSource dataSource;
    public  static  Connection con1 ;
    static {
        //加载配置文件和建立连接池
          Properties pro = new Properties();
        try {
            InputStream inputStream = JdbcUtil.class.getClassLoader().getResourceAsStream("druid.properties");
//            System.out.println(inputStream);
            pro.load(inputStream);
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(pro);
//            System.out.println(dataSource.getConnection());
            con1 = dataSource.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接池
     * @return
     */
    public static DataSource getDataSource(){
        return dataSource;
    }


    /**
     * 获取连接池中的一个连接
     * @return
     * @throws SQLException
     */
    public Connection getconnection(){
        Connection connection=null;
        try {
             connection = ds.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    return connection;
    }


    /**
     * 关闭数据库的资源  三个对象都存在时
     * @param conn
     * @param res
     * @param pstmt
     */
    public static void close(Connection conn, ResultSet res, PreparedStatement pstmt){
        if (conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (res!=null){
            try {
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt!=null){
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭数据库的连接（只存在Connection和PreparedStatement对象时）
     * @param conn
     * @param pstmt
     */
    public static void close(Connection conn, PreparedStatement pstmt){
        if (conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt!=null){
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws SQLException {
        Connection con = JdbcUtil.dataSource.getConnection();
        System.out.println(con);
        String sql = "select * from user where idx_email=? and idx_password=?";
        PreparedStatement preparedStatement =null;
        preparedStatement=con.prepareStatement(sql);
        preparedStatement.setString(1,"admin");
        preparedStatement.setString(2,"admin");
        ResultSet res = preparedStatement.executeQuery();
        while (res.next()){
            System.out.println(res.getString("idx_name"));
            System.out.println();
        }
        JdbcUtil.close(con,res,preparedStatement);    }
}