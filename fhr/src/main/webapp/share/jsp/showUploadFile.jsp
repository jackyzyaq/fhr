<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@page import="java.io.*"%>
<%@ page import="com.yq.common.service.UploadFileService"%>
<%@ page import="com.yq.common.pojo.UploadFile"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%
                String uuid = StringUtils.defaultIfEmpty(request.getParameter("upload_uuid"), "0");
 				ServletContext st = this.getServletConfig().getServletContext();
				ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(st);
				UploadFileService uploadFileService = (UploadFileService)ctx.getBean("uploadFileService");
				try{
					UploadFile uploadFile=uploadFileService.getUploadFileByUUId(uuid);
					if(uploadFile.getFileType().equals("image/pjpeg") ||uploadFile.getFileType().equals("image/gif") ||uploadFile.getFileType().equals("image/bmp")){		
							byte[] bs = uploadFile.getSource();
							out.clear();
							response.setContentType(uploadFile.getFileType()); 
							OutputStream outs = response.getOutputStream(); 
							outs.write(bs);
							outs.flush();
					}else{						
						byte[] bytes = uploadFile.getSource();
						String mime =uploadFile.getFileType();
						String title=uploadFile.getFileName();
						title = new String(title.getBytes("GBK"), "ISO8859_1");
						response.reset();
						if(bytes==null)
						{
							bytes = new byte[0] ;
							mime = "text/plain" ;
						}
						if(mime.equals("application/msword")){
						response.addHeader("Content-Disposition","attachment;   filename=\""   +   title   + ""+  "\"");
						} else if(mime.equals("application/vnd.ms-excel")){
						response.addHeader("Content-Disposition","attachment;   filename=\""   +   title   +  ""+ "\"");
						} else  if(mime.equals("application/pdf")){
						response.addHeader("Content-Disposition","attachment;   filename=\""   +   title   +  ""+ "\"");
						}else if(mime.equals("application/rar")){
						response.addHeader("Content-Disposition","attachment;   filename=\""   +   title   +  ""+ "\"");
						}else if(mime.equals("application/octet-stream")){
						response.addHeader("Content-Disposition","attachment;   filename=\""   +   title   +  ""+ "\"");					
						} else {
						response.addHeader("Content-Disposition","attachment;   filename=\""   +   title   +   "\"");
						}
						
						OutputStream outStream = response.getOutputStream();
						
						outStream.write(bytes, 0, bytes.length);
						outStream.flush();
						outStream.close();
						
						out.clear();
						out = pageContext.pushBody();
					}
				}catch(Exception e){				
				  //必须使用log4j输出异常日志e.printStackTrace();
				}				
%>