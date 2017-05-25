package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBUtils;
import entity.User;

public class UserDAO {
	private Connection conn;
	private String sql;
	PreparedStatement ps;
	/**
	 *在 构造函数中获得数据库链接
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public UserDAO() throws ClassNotFoundException, SQLException {
		conn=DBUtils.getConnection();
	}
	/**
	 * addUser方法(User user)
	 * 添加用户
	 * @throws SQLException 
	 */
	public void addUser(User user) throws SQLException{
		sql="insert into users(userName,pwd) values(?,?)";
		ps=conn.prepareStatement(sql);
		ps.setString(1, user.getUserName());
		ps.setString(2, user.getPwd());
		ps.execute();
		conn.close();
	}	
	/**
	 * 显示所有用户信息
	 * @throws SQLException 
	 */
	public ArrayList<User> getUser() throws SQLException{
		sql="select * from users";
		ArrayList<User> list=new ArrayList<User>();
		ps=conn.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			User u=new User();
			u.setId(rs.getInt(1));
			u.setUserName(rs.getString(2));
			u.setPwd(rs.getString(3));
			list.add(u);
		}
		conn.close();
		return list;
	}
	/**
	 * 判断用户名信息方法
	 * @throws SQLException 
	 */
	public boolean checkExists(User user) throws SQLException{
		sql="select * from users where userName=?";
		boolean flag=false;
		ps=conn.prepareStatement(sql);
		ps.setString(1, user.getUserName());
		ResultSet rs=ps.executeQuery();
		if(rs.next()){
			flag=true;
		}
		conn.close();
		return flag;
	}
	/**
	 * 根据名称删除用户方法
	 * @throws SQLException 
	 */
	public void deleteUser(User user) throws SQLException{
		sql="delete from users where id=?";
		ps=conn.prepareStatement(sql);
		ps.setInt(1, user.getId());
		ps.execute();
		conn.close();
	}
	/**
	 * 通过Id得到用户名及密码的方法
	 * @throws SQLException 
	 */
	public User getUserById(int id) throws SQLException{
		sql="select * from users where id=?";
		User user=new User();
		ps=conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			user.setId(rs.getInt(1));
			user.setUserName(rs.getString(2));
			user.setPwd(rs.getString(3));
		}
		conn.close();
		return user;
	}
	/**
	 * 修改用户方法
	 * @throws SQLException 
	 */
	public void modifyUser(User user) throws SQLException{
		sql="update users set userName=?,pwd=? where id=?";
		ps=conn.prepareStatement(sql);
		ps.setString(1, user.getUserName());
		ps.setString(2, user.getPwd());
		ps.setInt(3, user.getId());
		ps.execute();
		conn.close();
	}
	/**
	 * 登陆dao方法
	 * @throws SQLException 
	 */
	public boolean checkLogin(User user) throws SQLException{
		boolean flag=false;
		sql="select * from users where userName=? and pwd=?";
		ps=conn.prepareStatement(sql);
		ps.setString(1, user.getUserName());
		ps.setString(2, user.getPwd());
		ResultSet rs=ps.executeQuery();
		if(rs.next()){
			flag=true; 
		}
		conn.close();
		return flag;
	}
}
