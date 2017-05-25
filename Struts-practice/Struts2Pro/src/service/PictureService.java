package service;

import java.util.ArrayList;
import entity.Picture;

/**
 * ����ͼƬ�������ڽ������е�ͼƬ��Ϣ������Json�ύ��ǰ��
 * �ֲ㴦���ṹ����
 * @author Sinon
 *
 */
public class PictureService {
	public static String getJSON(ArrayList<Picture> list,String contextPath){
		/**
		 * �����Json���ո�ʽ�������Ϣ
		 */
		StringBuilder str=new StringBuilder();
		str.append("{");
		
		str.append("\"status\":1,");
		str.append("\"msg\": \"\" ,");
		str.append("\"title\": \"�û����\",");
		str.append("\"id\": 0,");
		str.append("\"start\": 0,");
		
		str.append("\"data\": [");
		
		//������Ƭ
		for(int i=0;i<list.size();i++){
			str.append("{");
			str.append("\"name\": \""+list.get(i).getName()+ "\",");
			str.append("\"pid\":0,");
			str.append("\"src\":\""+contextPath+"/"+list.get(i).getUrl()+"\",");
			str.append("\"thumb\": \"\",");
			str.append("\"area\":[638,851]");
			str.append("}");
			/**
			 * �ж��ǲ������һ���������һ��Ҫ��Ӷ���
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
