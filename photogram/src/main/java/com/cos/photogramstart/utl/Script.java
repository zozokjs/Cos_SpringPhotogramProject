package com.cos.photogramstart.utl;

public class Script {

	//에러 메세지를 alert로 표시함
	public static String back(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert('"+msg+"');");
		sb.append("history.back();");
		sb.append("</script>");
		
		return sb.toString();
		
	}
	
}
