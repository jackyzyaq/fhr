<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="java.io.*"%>
<%@ page import="com.yq.common.service.UploadFileService"%>
<%@ page import="com.yq.common.pojo.UploadFile"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<jsp:directive.page import="java.net.URLEncoder"/>
<%
	try{
		String file_uuid = StringUtils.defaultIfEmpty(request.getParameter("file"),"0");
 		ServletContext st = this.getServletConfig().getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(st);
		UploadFileService uploadFileService = (UploadFileService)ctx.getBean("uploadFileService");
		UploadFile uf = uploadFileService.getUploadFileByUUId(file_uuid);
		
		byte[] bs = null;
		if(uf!=null&&uf.getSource()!=null){
			bs = uf.getSource();
			out.clear();
			response.addHeader("Content-Disposition","attachment; filename="+URLEncoder.encode(uf.getFileName()+".jpg","UTF-8"));
			response.setContentType(uf.getFileType()); 
			OutputStream outs = response.getOutputStream(); 
			outs.write(bs);
			outs.flush();
			outs.close();
			out.clear();  
			out = pageContext.pushBody(); 
		}
	} catch(Exception e){e.printStackTrace();}
%>


