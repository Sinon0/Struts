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
	 * �ŵ����У����ɵ��ã����ٴ���
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
	 * AddUserAction����
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
	 * ��ʾ������Ϣ��Action 
	 * ,������Ϣ��ȡ����
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String list() throws ClassNotFoundException, SQLException{
		UserDAO dao=new UserDAO();
		ArrayList<User> list=dao.getUser();
		/**
		 * ����Ϣ��ŵ�ActionContext��
		 * ����ʱ��ʹ��<s:iterator value="#USERLIST">
		 * ���ַ�ʽ����
		 */
		ActionContext ctx=ActionContext.getContext();
		ctx.put("USERLIST", list);
		return SUCCESS;
	}
	/**
	 * ɾ����Action����
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
	 * �޸ĵ�Action����
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String modify() throws ClassNotFoundException, SQLException{
		UserDAO dao=new UserDAO();
		User newUser=dao.getUserById(user.getId());
		user=newUser;
		/**
		 * Ϊ��modify.jsp����ʾ��Ƭ׼������
		 * 1.pictureDAO�����ṩ����ͨ��uid�õ���Ƭ
		 * 2.���õ�����Ƭ���������list��
		 */
		PictureDAO pdao=new PictureDAO();		
		ArrayList<Picture> list=pdao.getPicture(user.getId());
		ActionContext ctx=ActionContext.getContext();
		ctx.put("PICTURES", list);
		return "modify";
	}
	/**
	 * �����޸���Ϣ
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
