import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_QueryAll {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		QueryAll();
	}
	
	public static void QueryAll() throws ClassNotFoundException, SQLException{
		//1.加载驱动
		Class.forName("com.mysql.jdbc.Driver");
		//2.获取链接
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web08", "root", "root");
		//3.获得语句执行者
		Statement stmt = conn.createStatement();
		//4.编写sql语句
		String sql = "select * from tbl_user";
		//5.执行sql
		ResultSet rs = stmt.executeQuery(sql);
		//6.遍历结果集
		while(rs.next()){
			int uid = rs.getInt(1);
			String uname = rs.getString(2);
			String upassword = rs.getString(3);
			System.out.println(uid+" "+uname+" "+upassword);
		}
		//7.释放资源
		rs.close();
		stmt.close();
		conn.close();
	}
}
