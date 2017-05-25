package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBUtils;
import entity.Picture;

public class PictureDAO {
	private Connection conn;
	private String sql;
	PreparedStatement ps;
	public PictureDAO() throws ClassNotFoundException, SQLException{
		conn=DBUtils.getConnection();
	}
	/**
	 * 添加图片
	 * @param picture
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public void addPicture(Picture picture) throws SQLException, ClassNotFoundException{
		sql="insert into pictures(uid,name,url)values(?,?,?)";
		if(conn.isClosed()){
			conn=DBUtils.getConnection();
		}
		ps=conn.prepareStatement(sql);
		ps.setInt(1, picture.getUid());
		ps.setString(2, picture.getName());
		ps.setString(3, picture.getUrl());
		ps.executeUpdate();
		conn.close();
	}
	/**
	 * 根据uid来获取到数据库中的图片
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Picture> getPictures(int id,String idType) throws SQLException{
		String fieldName="";
		if(idType.equals("user")){
			fieldName="uid";
		}else{
			fieldName="id";
		}
		sql="select * from pictures where "+fieldName+"=?";
		ArrayList<Picture> pics=new ArrayList<Picture>();
		ps=conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			Picture pic=new Picture();
			pic.setId(rs.getInt(1));
			pic.setUid(rs.getInt(2));
			pic.setName(rs.getString(3));
			pic.setUrl(rs.getString(4));
			pics.add(pic);
		}
		conn.close();
		return pics;
	}
	/**
	 * 获取全部照片
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public ArrayList<Picture> getPicture(int id) throws SQLException, ClassNotFoundException{
		sql="select * from pictures where uid=?";
		if(conn.isClosed()){
			conn=DBUtils.getConnection();
		}
		ArrayList<Picture> pict=new ArrayList<Picture>();
		ps=conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			Picture picts=new Picture();
			picts.setId(rs.getInt(1));
			picts.setUid(rs.getInt(2));
			picts.setName(rs.getString(3));
			picts.setUrl(rs.getString(4));
			pict.add(picts);
		}
		conn.close();
		return pict;
		}
	/**
	 * 收到一个uid，并根据uid将照片的个数获取到
	 * @param uid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public int getPictureCount(int uid) throws SQLException, ClassNotFoundException{
		sql="select count(*) from pictures where uid=?";
		if(conn.isClosed()){
			conn=DBUtils.getConnection();
		}
		int i=0;
		ps=conn.prepareStatement(sql);
		ps.setInt(1, uid);
		ResultSet rs=ps.executeQuery();
		rs.next();
		i=rs.getInt(1);
		conn.close();
		return i;
	}
	/**
	 * 删除图片方法
	 * @param id
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public void deletePicture(int id) throws SQLException, ClassNotFoundException{
		sql="delete from pictures where id=?";
		if(conn.isClosed()){
			conn=DBUtils.getConnection();
		}
		ps=conn.prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();
		conn.close();
	}
	/**
	 * 获取照片的url
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public String getUrl(int id) throws SQLException, ClassNotFoundException{
		sql="select url from pictures where id=?";
		String url="";
		if(conn.isClosed()){
			conn=DBUtils.getConnection();
		}
		ps=conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs=ps.executeQuery();
		rs.next();
		url=rs.getString(1);
		conn.close();
		return null;
	}
}
