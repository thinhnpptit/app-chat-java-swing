/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author thinh
 */
public class UserDAO extends DAO{
    public UserDAO(){
        super();
    }
    public int checkUserLogin(User user){
        int classify = 0;
        
        String sql = "SELECT * FROM tbluser WHERE username = ? AND password =?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                user.setName(rs.getString("name"));
//                user.setChat(rs.getString("historyChat"); // TODO: save log chat to db
                classify = 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classify;
    }
    public boolean addClient(User user) throws SQLException{
                String sql2 = "select name from tbluser where username = ?\n";
//                                    "union\n" +
//                                    "select name from tbstaff where username = ?";
                PreparedStatement ps2 = con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
                ps2.setString(1, user.getUsername());
//                ps2.setString(2, user.getUsername());
		ResultSet rs2 = ps2.executeQuery();
                if(rs2.getRow()>0) return false;
                
		String sql = "INSERT INTO tbluser(username,password,name,dob,gender,phonenumber,description) VALUES(?,?,?,?,?,?,?)";
		try{
			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getDob());
			ps.setString(5, user.getGender());
			ps.setString(6, user.getPhonenumber());
                        ps.setString(7, user.getDescription());
			ps.executeUpdate();
			
			//get id of the new inserted client
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
				user.setId(generatedKeys.getInt(1));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
                return true;
	}
}
