<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
body,html {
	padding: 0px;
	margin: 0px;
}

* {
	font-family: '微软雅黑'
}

.fileitems {
	font-size: 14px;
	height: 220px;
	overflow: auto;
	border: solid 1px #cccccc;
}

.fileitems .fileitem {
	border-bottom: solid 1px #cccccc;
	display: flex;
}

.fileitem div {
	border-right: solid 1px #cccccc;
	padding-top: 5px;
	padding-bottom: 5px;
}

.filetable {
	width: 100%;
	border-collapse: 0px;
	border-spacing: 0px;
	border-top: solid 1px #cccccc;
	font-size: 12px;
}

.filetable th {
	font-size: 13px;
	border-bottom: solid 1px #cccccc;
	border-right: solid 1px #cccccc;
	height: 30px;
	line-height: 30px;
	padding: 0px;
}

.filetable th:last-child {
	border-right: none;
}

.filetable td {
	padding: 0px;
	text-align: center;
	border-bottom: solid 1px #cccccc;
	padding-top: 8px;
	padding-bottom: 8px;
}

.filetable td .delbtn {
	font-size: 12px;
	padding-left: 6px;
	padding-right: 6px;
	padding-top: 3px;
	padding-bottom: 3px;
	color: #ff624d;
	border-radius: 4px;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	-o-border-radius: 4px;
	-ms-border-radius: 4px;
}

.filetable td .delbtn:hover {
	background-color: #ff624d;
	color: #ffffff;
}

.btn {
	display: inline-block;
	padding: 6px 12px;
	margin-bottom: 0;
	font-size: 14px;
	font-weight: normal;
	line-height: 1.428571429;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	cursor: pointer;
	background-image: none;
	border: 1px solid transparent;
	border-radius: 4px;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	-o-border-radius: 4px;
	-ms-border-radius: 4px;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	-o-user-select: none;
	user-select: none;
}

.btn-default {
	background-color: #6ec42d;
	color: #ffffff;
}

#picker {
	display: inline-block;
	line-height: 1.428571429;
	vertical-align: middle;
	margin: 0 12px 0 0;
}

#picker .webuploader-pick {
	padding: 6px 12px;
	display: block;
}
</style>



<link rel="stylesheet"
	href="${ctx}/static/components/webupload/webuploader.css" />
<script>
var successfiles = new Array();

function addfileobj(fileid,filename,filepath){
	if(fileid && filename && filepath){
		var obj = {fileid:fileid,filename:filename,filepath:filepath,uploadfileid:fileid};
		successfiles.push(obj);
	}
}
</script>

<div>

	<input type="hidden" id="articleids" name="articleids" />

	<table style="margin-top: 10px;">
		<tr>
			<td>
				<div id="selectuploadbtn" style="margin-right: 10px;">
					<div id="picker" class="uploadbtn">
						选择文件
					</div>
				</div>
			</td>
			<td>
				<div id="sumitupload" class="btn btn-default">
					确定上传
				</div>
				<div id="uploadingbtn" class="btn btn-default"
					style="display: none; background-color: #e7e7e7; color: #000000;">
					拼命上传...
				</div>
			</td>
		</tr>
	</table>


	<div id="items" class="fileitems" style="margin-top: 5px;">
		<div class="fileitem">
			<div style="padding-left: 10px; width: 30%;">
				文件名
			</div>
			<div style="width: 50%; padding-left: 10px;">
				音频文件
			</div>
			<div style="width: 10%;" align="center">
				上传状态
			</div>
			<div style="width: 10%;" align="center">
				操作
			</div>
		</div>

		<c:if test="${not empty attachments}">
			<c:forEach items="${attachments}" var="attachmentinfo">
				<div id="item_${attachmentinfo.id}" class="fileitem"
					style="display: flex;">
					<div style="padding-left: 10px; width: 30%;">
						<span title="${attachmentinfo.originalName}">${attachmentinfo.originalName}</span>
					</div>
					<div style="width: 50%; padding-left: 10px;"
						id="audio_${attachmentinfo.id}">
						<audio src="${ctx}/${attachmentinfo.address}" controls="controls">
						您的浏览器不支持 audio 标签。
						</audio>
					</div>
					<div style="width: 10%;" align="center">
						<span id="status_${attachmentinfo.id}">已上传</span>
					</div>
					<div style="width: 10%;" align="center">
						<a class="delbtn"
							href="javascript:deletefile('${attachmentinfo.id}',false)">删除</a>
					</div>
				</div>
				<script>
	  					addfileobj('${attachmentinfo.id}','${attachmentinfo.originalName}','${attachmentinfo.address}');
	  				</script>
			</c:forEach>
		</c:if>
	</div>

</div>

<script type="text/javascript"
	src="${ctx}/static/components/webupload/webuploader.min.js"></script>

<script>



//上传结束
function uploadjs(){
	var selectuploadbtn = document.getElementById("selectuploadbtn");
	if(selectuploadbtn){
		selectuploadbtn.style.display = "";
	}
	
	var sumitupload = document.getElementById("sumitupload");
	if(sumitupload){
		sumitupload.style.display = "";
	}
	
	var uploadingbtn = document.getElementById("uploadingbtn");
	if(uploadingbtn){
		uploadingbtn.style.display = "none";
	}
}


function sureupload(){
	var selectuploadbtn = document.getElementById("selectuploadbtn");
	if(selectuploadbtn){
		selectuploadbtn.style.display = "none";
	}
	
	var sumitupload = document.getElementById("sumitupload");
	if(sumitupload){
		sumitupload.style.display = "none";
	}
	
	var uploadingbtn = document.getElementById("uploadingbtn");
	if(uploadingbtn){
		uploadingbtn.style.display = "";
	}
}

//把文件传给extjs
function submittofont(){
	var fileids = "";
	if(successfiles && successfiles.length > 0){
		var sl = successfiles.length;
		for(var i = 0; i < sl; i++){
			var fobj = successfiles[i]; 
			var fileId = fobj.fileid;
			var fileName = fobj.filename;
			var filePath = fobj.filepath;
			if(fileId){
				if(fileids){
					fileids += ",";
				}
				fileids += fileId;
			}
		}
	}
	document.getElementById("articleids").value = fileids;
}


var uploadauto = false;

function deletefile(fileid,flag){
	if(flag != false){
		if(uploader){
			uploader.cancelFile(fileid);
		}
	}
	var filetable = document.getElementById("items");
	var fileitem = document.getElementById("item_"+fileid);
	filetable.removeChild(fileitem);
	
	//删除数据
	var newarr = new Array();
	if(successfiles && successfiles.length > 0){
		var sl = successfiles.length;
		for(var i = 0; i < sl; i++){
			var fobj = successfiles[i];
			var uploadfileid = fobj.uploadfileid;
			if(uploadfileid != fileid){
				newarr.push(fobj);
			}
		}
	}
	successfiles = newarr;
	submittofont();
};


var uploader = null;




 

function initupload(){
	var sumitupload = $("#sumitupload");
	
	var issubmit = false;
	
	//额外参数
	var formobj = {};

	//初始化上传控件
	uploader = WebUploader.create({
	    swf: '${ctx}/static/components/webupload/Uploader.swf',
	    server: '${ctx}/UploadServlet',
	    pick: '#picker',
	    resize: false,
	    auto: uploadauto,
	    formData:formobj,
	    accept:{
	    	title: '音频文件',
		    extensions: 'mp3,wav,amr',
		    mimeTypes: 'audio/*'
	    } 
	});
	
	//确定上传按钮事件
	sumitupload.on('click',function(){
		issubmit = true;
		//不自动上传
		sureupload();
		uploader.upload();
    });

 
	//上传文件队列
	uploader.on('fileQueued',function( file ){
		document.getElementById("sumitupload").disable = "disable";
		if(file){
			var maindiv = document.getElementById("items");
			if(maindiv){ 
				var fileid = file.id;
				var filename = file.name;
				var filediv = document.createElement("div");
				filediv.id = "item_"+fileid;
				filediv.style.display = "flex";
				filediv.className = "fileitem";
				filediv.innerHTML = '<div style="padding-left: 10px;width:30%;"><span title="'+filename+'">'+filename+'</span></div>'+
									'<div style="width:50%;padding-left: 10px;" id="audio_'+fileid+'"></div>'+
									'<div style="width:10%;" align="center"><span id="status_'+fileid+'">未上传</span></div>'+
									'<div style="width:10%;" align="center"><a class="delbtn" href="javascript:deletefile(\''+fileid+'\')">删除<a></div>';
				maindiv.appendChild(filediv);
			}
		}
	});
	
	// 文件上传过程中创建进度条实时显示。
	uploader.on( 'uploadProgress',function( file,percentage){
		var fileid = file.id;
		var s = document.getElementById("status_"+fileid);
		if(s){
			var p = parseInt(percentage*100);
			s.innerHTML = "上传:"+p+"%";
		}
	});
	
	
	uploader.on( 'uploadSuccess', function(file,response) {
		var msg = "上传异常";
		 var fileid = file.id;
		var s = document.getElementById("status_"+fileid);
		var filejson = response._raw;
		alert("filejson:"+filejson);
		if(filejson){
			var filejsonobj = eval("("+filejson+")");
			if(filejsonobj && filejsonobj.data){
				var fileobjarr = filejsonobj.data;
				if(fileobjarr && fileobjarr.length > 0){
					var fileobj = fileobjarr[0];
					msg = "上传成功";
					var fid = fileobj.id;
					var fname = fileobj.originalName;
					var fpath = fileobj.address;
					var obj = {fileid:fid,filename:fname,filepath:fpath,status:'0',uploadfileid:fileid};
					successfiles.push(obj);
					var audioitem = document.getElementById("audio_"+fileid);
					if(audioitem){
						audioitem.innerHTML = '<audio src="${ctx}/'+fpath+'" controls="controls">您的浏览器不支持 audio 标签。</audio>';
					}
					
				}
			}
		}
		
		if(s){
			s.innerHTML = msg;
		}
	});

	uploader.on( 'uploadError', function( file ) {
	     var fileid = file.id;
		 var s = document.getElementById("status_"+fileid);
		 if(s){
			s.innerHTML = "上传失败";
		 }
	});
	
	
	uploader.on( 'uploadStart', function( file ) {
	     
	});
	
	//所有文件上传结束后触发
	uploader.on( 'uploadFinished', function( file ) {
		uploadjs();
	    submittofont();
	});

	uploader.on( 'uploadComplete', function( file ) {
	   
	});
}

$(function(){
	initupload();
	if(successfiles && successfiles.length > 0){
		submittofont();
	}
});
</script>

