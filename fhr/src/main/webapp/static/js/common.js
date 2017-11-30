function PreviewImage(fileObj, imgPreviewId, divPreviewId){
    var allowExtention = ".jpg,.png"; //.jpg,.bmp,.gif,.png,允许上传文件的后缀名
    var extention = fileObj.value.substring(fileObj.value.lastIndexOf(".") + 1).toLowerCase(); //获取当前上传文件的扩展名
    var browserVersion = window.navigator.userAgent.toUpperCase();
    if (allowExtention.indexOf(extention) > -1) {
        if (fileObj.files) {//兼容chrome、火狐7+、360浏览器5.5+等，应该也兼容ie10，HTML5实现预览
            if (window.FileReader) {
                var reader = new FileReader();
                reader.onload = function(e) {
                    document.getElementById(imgPreviewId).setAttribute("src", e.target.result);
                }
                reader.readAsDataURL(fileObj.files[0]);
            } else if (browserVersion.indexOf("SAFARI") > -1) {
                alert("不支持Safari浏览器6.0以下版本的图片预览!");
            } else {
                alert("不支持您当前使用的浏览器的图片预览!");
            }
        } else if (browserVersion.indexOf("MSIE") > -1) {//ie、360低版本预览
            if (browserVersion.indexOf("MSIE 6") > -1) {//ie6
                document.getElementById(imgPreviewId).setAttribute("src", fileObj.value);
            } else {//ie[7-9]

                fileObj.select();
                if (browserVersion.indexOf("MSIE 9") > -1) {
                    //fileObj.blur(); //不加上document.selection.createRange().text在ie9会拒绝访问
                    document.getElementById(divPreviewId).focus(); //参考http://gallop-liu.iteye.com/blog/1344778
                }     
                
            	document.getElementById(imgPreviewId).src = document.selection.createRange().text;
            }
        } else if (browserVersion.indexOf("FIREFOX") > -1) {//firefox
            var firefoxVersion = parseFloat(browserVersion.toLowerCase().match(/firefox\/([\d.]+)/)[1]);
            if (firefoxVersion < 7) {//firefox7以下版本
                document.getElementById(imgPreviewId).setAttribute("src", fileObj.files[0].getAsDataURL());
            } else {//firefox7.0+                    
                document.getElementById(imgPreviewId).setAttribute("src", window.URL.createObjectURL(fileObj.files[0]));
            }
        } else {
            alert("不支持您当前使用的浏览器的图片预览!");
        }
    } else {
        alert("仅支持" + allowExtention + "为后缀名的文件!");
        fileObj.value = ""; //清空选中文件
        if (browserVersion.indexOf("MSIE") > -1) {
            fileObj.select();
            document.selection.clear();
        }
        fileObj.outerHTML = fileObj.outerHTML;
    } 
}


function selectmenu(id){
	if(id){
		var leftmenus = document.getElementById("leftmenus");
		if(leftmenus){
			var arr = document.getElementsByTagName("a");
			if(arr && arr.length > 0){
				var l = arr.length;
				for(var i = 0; i < l; i++){
					var a = arr[i];
					var aid = a.id;
					if(aid){
						if(aid == id){
							a.className = "current";
						}else{
							a.className = "";
						}	
					}
				}
			}
		}
	}
}

var mobilereg = /^1[3-8]\d{9}$/;
function checkmobile(mobile){
	if(mobile && mobile.length == 11){
		var f = RegExp(mobilereg).test(mobile);
		return f;
	}
	return false;
}


function changetreedata(treedata,deptid){
	var treemainobj = null;
	var treearr = null;
	var selectdept = null;
	if(treedata && treedata.length > 0){
		treemainobj = {};
		treearr = new Array();
		var l = treedata.length;
		var num = 0;
		for(var i = 0; i < l; i++){
			var obj =  treedata[i];
			var t = obj['text'];
			var oid = obj['id'];
			var newobj = {text:t,oid:oid};
			
			if(deptid && deptid == oid){
				selectdept = obj;
			}
			if(obj.children){
				var childrendata = obj.children;
				var cobj = changetreedata(childrendata,deptid);
				if(cobj){
					var arr = cobj['datalist'];
					if(arr && arr.length > 0){
						newobj["nodes"] = arr;
					}
					var cnum = cobj["num"];
					if(cnum){
						var tempnum = parseInt(cnum);
						newobj["tags"] = [tempnum+""];
						num +=tempnum;
					}
					
					var sdept = cobj['selectdept'];
					if(sdept){
						selectdept = sdept;
					}
				}
			}else{
				newobj["tags"] = ["1"];
				num++;
			}
			treearr.push(newobj);
		}
		treemainobj['datalist'] = treearr;
		treemainobj["num"] = num;
		treemainobj["selectdept"] = selectdept;
	}
	return treemainobj
}
//错误提示信息
function commonerror(content,eid){
	var e = null; 
	if(eid){
		e = document.getElementById(eid);
	}
	if(e){
		$.tips.error(content,e);
	}else{ 
		$.tips.error(content);
	}
}

//普通信息
function commoninfo(content,eid){
	var e = null; 
	if(eid){
		e = document.getElementById(eid);
	}
	if(e){
		$.tips.info(content,e);
	}else{
		$.tips.info(content);
	}
}


//成功信息
function commonsuccess(content,eid){
	var e = null; 
	if(eid){
		e = document.getElementById(eid);
	}
	if(e){
		$.tips.success(content,e);
	}else{
		$.tips.success(content);
	}
	
}


function commonconfirm(content,action){
	$("#selectmodal").modal('show');
	var c = document.getElementById("selectmodal_content");
	if(c){
		c.innerHTML = content;
	}
	var okbtn = document.getElementById("selectmodal_ok");
	if(okbtn){
		var ffn = function(){$("#selectmodal").modal('hide');action()};
		okbtn.onclick = ffn;
	}
}


function deleteitem(url,tipcontent,refshurl){
	if(url){
		var actionfun = function(){
			$.post(url,{},function(data){
					if(data){
						if(data.result == '0'){
							if(refshurl){
								window.location.href = refshurl;
							}
						}else{
							commonerror(data.message);
						}
					}else{
						commonerror("操作失败");
					}
				});
		}
		
		if(tipcontent){
			$("#selectmodal").modal('show');
			var c = document.getElementById("selectmodal_content");
			if(c){
				c.innerHTML = tipcontent;
			}
			var okbtn = document.getElementById("selectmodal_ok");
			if(okbtn){
				okbtn.onclick = function(){
					$("#selectmodal").modal('hide');
					actionfun();
				};
			}	
		}else{
			actionfun();
		}
	
		
	}
	
}






function unescapehtml(content){
	if(content){
		content = content.replace(new RegExp("&amp;","gm"),"&");
		content = content.replace(new RegExp("&gt;","gm"),">");
		content = content.replace(new RegExp("&lt;","gm"),"<");
	}
	return content;
}
 
function escapehtml(content){
	if(content){
		content = content.replace(new RegExp("&","gm"),"&amp;");
		content = content.replace(new RegExp(">","gm"),"&gt;");
		content = content.replace(new RegExp("<","gm"),"&lt;");
	}
	return content;
}


Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}



