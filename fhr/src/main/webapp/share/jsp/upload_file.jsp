<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%
	String upload_uuid = StringUtils.defaultIfEmpty(request.getParameter("upload_uuid"), "");
	String input_name = StringUtils.defaultIfEmpty(request.getParameter("input_name"), "upload_uuid");
%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
		<style>
			.upload_file #file{ position:absolute; z-index:12; filter:alpha(opacity:0); opacity:0;}
			.upload_file input{ margin: 5px;height:34px;}
			.upload_file #msg{
				color:RED;
				FONT-SIZE: 14px; 
				FONT-FAMILY: Arial, Helvetica, sans-serif,'宋体',tahoma, Srial, helvetica, sans-serif;
			}
		</style>
		<script type="text/javascript">
			var _url = '${ctx}/common/fileUpload/upload.do';
		</script>
		<script type="text/javascript" src="${ctx}/share/js/guid.js"></script>
		<script type="text/javascript" src="${ctx}/js/ajax_upload/ajaxfileupload.js" ></script> 
        <script type="text/javascript">
				function fileUpload(obj,upload_uuid) {
					var file = $("#"+obj+" #file");
					var strFileName=file.val().replace(/^.+?\\([^\\]+?)(\.[^\.\\]*?)?$/gi,"$1");  //正则表达式获取文件名，不带后缀
					var FileExt=file.val().replace(/.+\./,"");   //正则表达式获取后缀
					if(file.attr("accept").indexOf(FileExt)>-1){
						//$("#"+obj+" #txt_id").val(strFileName+'.'+FileExt);
					}else if(file.attr("accept")==''){
						
					}else{
						alert("格式不对！");
						return ;
					}
					$.ajaxFileUpload( {
						    url : _url,//用于文件上传的服务器端请求地址
						    secureuri : false,          //一般设置为false
						    fileElementId : obj+" #file",     //文件上传空间的id属性  <input type="file" id="file" name="file" />
						    dataType : 'json',          //返回值类型 一般设置为json
						    success : function(data) {
						    	if(data.flag=='1'&&typeof(data.upload_uuid) != "undefined"){
						    		if(data.upload_uuid == 'null'||data.upload_uuid == ''){
						    		}else{
							    		$("#"+obj+" #"+upload_uuid).val(data.upload_uuid);
							    		$("#"+obj+" #msg").text(strFileName+'.'+FileExt+"（上传成功）");
							    		editFile(data.upload_uuid);
						    		}
						    	}else if(data.flag=='0'&&typeof(data.msg) != "undefined"){
						    		$("#"+obj+" #msg").text(data.msg);
						    	}else{
						    	
						    	}
						    },
				        	error: function (data) {
				            	
				        	}
						});
				}
		</script>
	    <div id="obj1" class="upload_file">
			<input type="file" id="file" name="file" onchange="fileUpload('obj1','<%=input_name %>');" value="sss" accept=".xls,.xlsx,.ppt,.pptx,.jpg,.zip,.rar">
			<input type="button" id="txt_id" value="上传" />
			<div style="text-align: left;">
			<input type="hidden" id="<%=input_name %>" name="<%=input_name %>" value="<%=upload_uuid %>"  title="上传文件"/>
			</div>
			<div id="msg"></div>
		</div>
