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
		//1.��������
		Class.forName("com.mysql.jdbc.Driver");
		//2.��ȡ����
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web08", "root", "root");
		//3.������ִ����
		Statement stmt = conn.createStatement();
		//4.��дsql���
		String sql = "select * from tbl_user";
		//5.ִ��sql
		ResultSet rs = stmt.executeQuery(sql);
		//6.���������
		while(rs.next()){
			int uid = rs.getInt(1);
			String uname = rs.getString(2);
			String upassword = rs.getString(3);
			System.out.println(uid+" "+uname+" "+upassword);
		}
		//7.�ͷ���Դ
		rs.close();
		stmt.close();
		conn.close();
	}
}
