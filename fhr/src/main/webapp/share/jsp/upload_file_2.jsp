<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
		<style>
			#obj1 #file{ position:absolute; z-index:12; filter:alpha(opacity:0); opacity:0;}
			#obj1 input{ margin: 5px;height:21px;}
			#obj1 #msg{
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
				function fileUpload(obj) {
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
						    		$("#"+obj+" #upload_uuid").val(data.upload_uuid);
						    		showMsgInfo(strFileName+'.'+FileExt+"（上传成功）");
						    		editFile(data.upload_uuid);
						    	}else if(data.flag=='0'&&typeof(data.msg) != "undefined"){
						    		showMsgInfo(data.msg);
						    	}else{
						    	
						    	}
						    },
				        	error: function (data) {
				            	
				        	}
						});
				}
		</script>
	    <span id="obj1">
			<input type="file" id="file" name="file" onchange="fileUpload('obj1');" accept=".xls,.xlsx,.jpg,.zip,.rar">
			<input type="button" id="txt_id" value="上传" />
			<input type="hidden" id="upload_uuid" name="upload_uuid" value=""/>
		</span>
