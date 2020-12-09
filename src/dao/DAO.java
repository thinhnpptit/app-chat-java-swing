package dao;

import java.sql.*;

public class DAO {
	public static Connection con;
	
	public DAO(){
		if(con == null){
			String dbUrl = "jdbc:mysql://localhost:3306/chattingapp?autoReconnect=true&useSSL=false";
			String dbClass = "com.mysql.jdbc.Driver";

			try {
				//Class.forName(dbClass);
				con = DriverManager.getConnection (dbUrl, "root", "123456a@");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
