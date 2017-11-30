<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="java.io.*"%>
<%@ page import="com.yq.common.service.UploadFileService"%>
<%@ page import="com.yq.common.pojo.UploadFile"%>
<jsp:directive.page import="java.net.URLEncoder"/>
<%
	ServletContext st = this.getServletConfig().getServletContext();
	ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(st);
	UploadFileService uploadFileService = (UploadFileService) ctx.getBean("uploadFileService");
	try{
		String sessionObject = request.getParameter("sessionObect");
		byte[] bs = (byte[])session.getAttribute(sessionObject);
		if(bs==null||bs.length==0){
		}else{
			out.clear();
			response.addHeader("Content-Disposition","attachment; filename="+URLEncoder.encode(Math.random()+".jpg","UTF-8"));
			response.setContentType("image/pjpeg"); 
			OutputStream outs = response.getOutputStream(); 
			outs.write(bs);
			outs.flush();
			outs.close();
			out.clear();  
			out = pageContext.pushBody(); 
		}
	} catch(Exception e){e.printStackTrace();}
%>


