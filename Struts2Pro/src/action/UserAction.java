package action;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.PictureDAO;
import dao.UserDAO;
import entity.Picture;
import entity.User;

public class UserAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private User user;
	/**
	 * 放到类中，均可调用，减少代码
	 */
	HttpServletResponse response=ServletActionContext.getResponse();
	HttpServletRequest request=ServletActionContext.getRequest();
	HttpSession session=request.getSession();
	PrintWriter out=null;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * AddUserAction方法
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		// TODO Auto-generated method stub
		UserDAO dao=new UserDAO();
		dao.addUser(user);
		return "list";
	}
	/**
	 * CheckLoginAction
	 * @return
	 * @throws Exception
	 */
	public String checkLogin() throws Exception {
		// TODO Auto-generated method stub
		UserDAO dao=new UserDAO();
		out=response.getWriter();
		if(dao.checkLogin(user)){
			session.setAttribute("user", user);
			out.print("1");
		}else{
			out.print("0");
		}
		return null;
	}
	/**
	 * CheckExistsAction
	 * @return
	 * @throws Exception
	 */
	public String checkExists() throws Exception {
		// TODO Auto-generated method stub
		out=response.getWriter();
		UserDAO dao=new UserDAO();
		if(dao.checkExists(user)){
			out.print("1");
		}else{
			out.print("0");
		}
		return null;
	}
	/**
	 * 显示所有信息的Action 
	 * ,并将信息提取出来
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String list() throws ClassNotFoundException, SQLException{
		UserDAO dao=new UserDAO();
		ArrayList<User> list=dao.getUser();
		/**
		 * 将信息存放到ActionContext中
		 * 调用时，使用<s:iterator value="#USERLIST">
		 * 这种方式调用
		 */
		ActionContext ctx=ActionContext.getContext();
		ctx.put("USERLIST", list);
		return SUCCESS;
	}
	/**
	 * 删除的Action方法
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String delete() throws ClassNotFoundException, SQLException{
		UserDAO dao=new UserDAO();
		dao.deleteUser(user);
		return null;
	}
	/**
	 * 修改的Action方法
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String modify() throws ClassNotFoundException, SQLException{
		UserDAO dao=new UserDAO();
		User newUser=dao.getUserById(user.getId());
		user=newUser;
		/**
		 * 为在modify.jsp中显示照片准备数据
		 * 1.pictureDAO中有提供方法通过uid得到照片
		 * 2.将得到的照片存放在数组list中
		 */
		PictureDAO pdao=new PictureDAO();		
		ArrayList<Picture> list=pdao.getPicture(user.getId());
		ActionContext ctx=ActionContext.getContext();
		ctx.put("PICTURES", list);
		return "modify";
	}
	/**
	 * 保存修改信息
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String save() throws ClassNotFoundException, SQLException{
		UserDAO dao=new UserDAO();
		dao.modifyUser(user);
		return "list";
	}
}
