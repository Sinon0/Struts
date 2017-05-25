package action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import dao.PictureDAO;
import entity.Picture;
import service.PictureService;

public class PictureAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	private File[] image;
	private String[] imageFileName;
	String[] imageContentType;
	String idType;
	
	ArrayList<Picture> pictures=new ArrayList<Picture>();
	
	public ArrayList<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(ArrayList<Picture> pictures) {
		this.pictures = pictures;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public File[] getImage() {
		return image;
	}

	public void setImage(File[] image) {
		this.image = image;
	}

	public String[] getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String[] imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String[] getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String[] imageContentType) {
		this.imageContentType = imageContentType;
	}

	
	Picture picture;
	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}
	/**
	 * ����ʹ�õļ�������
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public String add() throws ClassNotFoundException, SQLException{
		//����application�õ���վ�Ĺ���picture�Ĵ洢Ŀ¼,���ȵõ�application
		ServletContext application=ServletActionContext.getServletContext();
		String path=application.getRealPath("")+"/images";
		//forѭ�������ϴ�ͼƬ�ĸ���
		for(int i=0;i<image.length;i++)
		{
		//����һ���ļ��������洢�ϴ��������ļ���path�Ǵ�ŵ�Ŀ¼
		File myfile=new File(path,imageFileName[i]);
		//�жϴ洢Ŀ¼�Ƿ���ڣ������ھͽ����Ŀ¼�����������������֮����б���
		if(!myfile.getParentFile().exists()){
			myfile.getParentFile().mkdirs();
		}
		/**
		 * ���ϴ�(myfile)���ļ�������Դ�ļ�(image)��,Ϊʲô�Ѿ��ϴ�������Ҫ������Դ�ļ��У�
		 * �ش���Ϊstruts�����н���֮�����ϴ�����ʱ�ļ�ɾ��
		 */
		try {
			FileUtils.copyFile(image[i], myfile);
			/**
			 * �������ֻ�ǰ��ļ������·�����ˣ����ļ���Ϣ�洢�����ݿ���ȥ��Ŀǰpicture��url��uid���ǿ����õ��ģ�
			 * ֻ��url�ò������������ڻ�ȡurl,����ŵ����ݿ���
			 */
			pictures.get(i).setUrl("images/"+imageFileName[i]);
			PictureDAO dao=new PictureDAO();
			dao.addPicture(pictures.get(i));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		}
		return "list";
	}
	/**
	 * ��ȡ��ǰ��Ƭ
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public String getpic() throws ClassNotFoundException, SQLException, IOException{
		int id=0;
		if(idType.equals("user")){
			id=picture.getUid();
		}else{
			id=picture.getId();
		}
		PictureDAO dao=new PictureDAO();
		ArrayList<Picture> list=dao.getPictures(id,idType);
		//�õ���ǰ������·��
		String path=ServletActionContext.getRequest().getContextPath();
		String json=PictureService.getJSON(list, path);
		HttpServletResponse response=ServletActionContext.getResponse();
		PrintWriter out=response.getWriter();
		out.print(json);
		System.out.println(json);
		return null;
	}
	/**
	 * ��õ�ǰ�û�id�µ�������Ƭ
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public String getpics() throws ClassNotFoundException, SQLException, IOException{
		PictureDAO dao=new PictureDAO();
		ArrayList<Picture> list=new ArrayList<Picture>();
		list=dao.getPicture(picture.getUid());
		String path=ServletActionContext.getRequest().getContextPath();
		String json=PictureService.getJSON(list, path);
		HttpServletResponse response=ServletActionContext.getResponse();
		PrintWriter out=response.getWriter();
		out.print(json);
		System.out.println(json);
		return null;
	}
	/**
	 * ��ǰ�˴�ӡ��Ƭ�ĸ���
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public String getnum() throws ClassNotFoundException, SQLException, IOException{
		PictureDAO dao=new PictureDAO();
		HttpServletResponse response=ServletActionContext.getResponse();
		int num=dao.getPictureCount(picture.getUid());
		PrintWriter out=response.getWriter();
		out.print(num);
		return null;
	}
	/**
	 * ɾ����ƬAction
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String delete() throws ClassNotFoundException, SQLException{
		PictureDAO dao=new PictureDAO();
		//ɾ���ļ�
		String url=dao.getUrl(picture.getId());
		ServletContext application=ServletActionContext.getServletContext();
		String path=application.getRealPath("")+"/"+url;
		File myfile=new File(path);
		FileUtils.deleteQuietly(myfile);
		//ɾ�����ݿ��б�ļ�¼
		dao.deletePicture(picture.getId());
		return null;
	}
}
