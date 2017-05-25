package service;

import java.util.ArrayList;
import entity.Picture;

/**
 * 创建图片服务，用于接收所有的图片信息并返回Json提交到前端
 * 分层处理，结构清晰
 * @author Sinon
 *
 */
public class PictureService {
	public static String getJSON(ArrayList<Picture> list,String contextPath){
		/**
		 * 构造出Json接收格式：相册信息
		 */
		StringBuilder str=new StringBuilder();
		str.append("{");
		
		str.append("\"status\":1,");
		str.append("\"msg\": \"\" ,");
		str.append("\"title\": \"用户相册\",");
		str.append("\"id\": 0,");
		str.append("\"start\": 0,");
		
		str.append("\"data\": [");
		
		//处理照片
		for(int i=0;i<list.size();i++){
			str.append("{");
			str.append("\"name\": \""+list.get(i).getName()+ "\",");
			str.append("\"pid\":0,");
			str.append("\"src\":\""+contextPath+"/"+list.get(i).getUrl()+"\",");
			str.append("\"thumb\": \"\",");
			str.append("\"area\":[638,851]");
			str.append("}");
			/**
			 * 判断是不是最后一个，若最后一个要添加逗号
			 */
			if(i<list.size()-1){
				str.append(",");
			}
			str.append("]");
		}
		str.append("}");
		return str.toString();
	}
}
