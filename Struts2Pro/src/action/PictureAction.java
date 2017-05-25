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
	 * 公共使用的几个方法
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public String add() throws ClassNotFoundException, SQLException{
		//利用application得到网站的关于picture的存储目录,首先得到application
		ServletContext application=ServletActionContext.getServletContext();
		String path=application.getRealPath("")+"/images";
		//for循环接收上传图片的个数
		for(int i=0;i<image.length;i++)
		{
		//创建一个文件，用来存储上传上来的文件，path是存放的目录
		File myfile=new File(path,imageFileName[i]);
		//判断存储目录是否存在，不存在就将这个目录创建出来，创建完成之后进行保存
		if(!myfile.getParentFile().exists()){
			myfile.getParentFile().mkdirs();
		}
		/**
		 * 将上传(myfile)的文件拷贝到源文件(image)中,为什么已经上传但还需要拷贝到源文件中？
		 * 回答：因为struts在运行结束之后会把上传的临时文件删除
		 */
		try {
			FileUtils.copyFile(image[i], myfile);
			/**
			 * 上面编码只是把文件存放在路径下了，把文件信息存储到数据库中去，目前picture的url和uid都是可以拿到的，
			 * 只是url拿不到，所以现在获取url,并存放到数据库中
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
	 * 获取当前照片
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
		//拿到当前上下文路径
		String path=ServletActionContext.getRequest().getContextPath();
		String json=PictureService.getJSON(list, path);
		HttpServletResponse response=ServletActionContext.getResponse();
		PrintWriter out=response.getWriter();
		out.print(json);
		System.out.println(json);
		return null;
	}
	/**
	 * 获得当前用户id下的所有照片
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
	 * 向前端打印照片的个数
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
	 * 删除照片Action
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String delete() throws ClassNotFoundException, SQLException{
		PictureDAO dao=new PictureDAO();
		//删除文件
		String url=dao.getUrl(picture.getId());
		ServletContext application=ServletActionContext.getServletContext();
		String path=application.getRealPath("")+"/"+url;
		File myfile=new File(path);
		FileUtils.deleteQuietly(myfile);
		//删除数据库中表的记录
		dao.deletePicture(picture.getId());
		return null;
	}
}
