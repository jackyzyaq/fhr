/*
Javascript:
网页可见区域宽： document.body.clientWidth
网页可见区域高： document.body.clientHeight
网页可见区域宽： document.body.offsetWidth (包括边线的宽)
网页可见区域高： document.body.offsetHeight (包括边线的高)
网页正文全文宽： document.body.scrollWidth
网页正文全文高： document.body.scrollHeight
网页被卷去的高： document.body.scrollTop
网页被卷去的左： document.body.scrollLeft
网页正文部分上： window.screenTop
网页正文部分左： window.screenLeft
屏幕分辨率的高： window.screen.height
屏幕分辨率的宽： window.screen.width
屏幕可用工作区高度： window.screen.availHeight
屏幕可用工作区宽度： window.screen.availWidth
JQuery:
$(document).ready(function(){
alert($(window).height()); //浏览器当前窗口可视区域高度
alert($(document).height()); //浏览器当前窗口文档的高度
alert($(document.body).height());//浏览器当前窗口文档body的高度
alert($(document.body).outerHeight(true));//浏览器当前窗口文档body的总高度 包括border padding margin

alert($(window).width()); //浏览器当前窗口可视区域宽度
alert($(document).width());//浏览器当前窗口文档对象宽度
alert($(document.body).width());//浏览器当前窗口文档body的宽度
alert($(document.body).outerWidth(true));//浏览器当前窗口文档body的总宽度 包括border padding margin

})
*/
var _window_height = 0,_window_width = 0,_document_height = 0,_document_width = 0,
	_document_body_height = 0,_document_body_width = 0,_document_body_outer_height = 0,_document_body_outer_width = 0;
$(document).ready(function(){
	_window_height = ($(window).height()); //浏览器当前窗口可视区域高度
	_document_height = ($(document).height()); //浏览器当前窗口文档的高度
	_document_body_height = ($(document.body).height());//浏览器当前窗口文档body的高度
	_document_body_outer_height = ($(document.body).outerHeight(true));//浏览器当前窗口文档body的总高度 包括border padding margin

	_window_width = ($(window).width()); //浏览器当前窗口可视区域宽度
	_document_width = ($(document).width());//浏览器当前窗口文档对象宽度
	_document_body_width = ($(document.body).width());//浏览器当前窗口文档body的宽度
	_document_body_outer_width = ($(document.body).outerWidth(true));//浏览器当前窗口文档body的总宽度 包括border padding margin

});


var localObj = window.location;
var ctx = "/"+localObj.pathname.split("/")[1];
var splitStr = "||||";

/**
 * 字符串 头和尾 空格的过滤
 */
String.prototype.replaceAll = function (sObj,newObj) {
	var reg=new RegExp(sObj,"g");
    return this.replace(reg, newObj);
}
/**
 * 字符串 头和尾 空格的过滤
 */
String.prototype.Trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
/**
 * 实现对字符串 头（左侧Left） 空格的过滤， 代码如下所示：
 */
String.prototype.LTrim = function () {
	return this.replace(/(^\s*)/g, "");
}
/**
 * 实现对字符串 尾（右侧Right） 空格的过滤， 代码如下所示：
 * 
 * @returns
 */

String.prototype.RTrim = function () {
    return this.replace(/(\s*$)/g, "");
}
/**
 * 实现Contains方法（核心是用Index方法的返回值进行判断），代码如下所示：
 */
String.prototype.Contains = function (subStr) {
	var currentIndex = this.indexOf(subStr);
    if (currentIndex != -1) {
    	return true;
    } else {
    	return false;
    }
}

//验证手机号
function isPhoneNo(phone) { 
 var pattern = /^1[34578]\d{9}$/; 
 return pattern.test(phone); 
}
 
// 验证身份证 
function isCardNo(card) { 
 var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
 return pattern.test(card); 
}
/**
 * 判断是否正浮点数 2014-8-11
 * 
 * @param str
 * @return
 */
function isNumeric(obj) {
	var reNum=/^(-?\d+)(\.\d+)?$/;
	return(reNum.test(obj));
}
function isNum(obj){
	var reNum=/^[0-9]*$/;
	return(reNum.test(obj));
}
function replaceForNum(obj) {
	obj.value=obj.value.replace(/[^0-9]/g,'');
}
function replaceForHalf(obj){
	var reNum=/^\d*(\.(5|0))?$/;
	if(!reNum.test(obj.value)){
		obj.value = '0';
	}
}
function wdateInstance(){
	WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-{%d}',alwaysUseStartDate:true});
}

function wdateInstance1(){
	WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:false});
}

function wdateInstance2(fn){
	var _fn;
	if(typeof(fn) == "undefined"||fn == null||fn ==''){
		_fn = function(){};
	}else if(typeof(fn) == 'function'){
		_fn = fn();
	}else{
		_fn = eval(fn+'()');
	}
	WdatePicker({dateFmt:'yyyy-MM-dd',alwaysUseStartDate:false,onpicked: _fn});
}

function wTimeInstance(){
	WdatePicker({startDate:'%H:00:00',dateFmt:'HH:mm:00',alwaysUseStartDate:true,
		disabledDates:[
		        		'..\:.1\:..','..\:.2\:..','..\:.3\:..','..\:.4\:..','..\:.5\:..','..\:.6\:..','..\:.7\:..','..\:.8\:..',
		        		'..\:.9\:..','..\:10\:..','..\:11\:..','..\:12\:..','..\:13\:..','..\:14\:..','..\:15\:..','..\:16\:..',
		        		'..\:17\:..','..\:18\:..','..\:19\:..','..\:20\:..','..\:21\:..','..\:22\:..','..\:23\:..','..\:24\:..',
		        		'..\:25\:..','..\:26\:..','..\:27\:..','..\:28\:..','..\:29\:..','..\:31\:..','..\:32\:..','..\:33\:..',
		        		'..\:34\:..','..\:35\:..','..\:36\:..','..\:37\:..','..\:38\:..','..\:39\:..','..\:40\:..','..\:41\:..',
		        		'..\:42\:..','..\:43\:..','..\:44\:..','..\:45\:..','..\:46\:..','..\:47\:..','..\:48\:..','..\:49\:..',
		        		'..\:50\:..','..\:51\:..','..\:52\:..','..\:53\:..','..\:54\:..','..\:55\:..','..\:56\:..','..\:57\:..',
		        		'..\:58\:..','..\:59\:..'	                                                          
		        	]});
}

function wYearInstance(fn){
	WdatePicker({startDate:'%yyyy',dateFmt:'yyyy',alwaysUseStartDate:true,maxDate:'%yyyy',
		onpicked: fn});
}

function wdateBeginInstance(endId){
	WdatePicker({startDate:'%y-%M-%d',
		//minDate:'%y-%M-%d',
		maxDate:'#F{$dp.$D(\''+endId+'\')||\'2140-10-01\'}',
		dateFmt:'yyyy-MM-dd'});
}

function wdateEndInstance(beginId){
	WdatePicker({startDate:'%y-%M-%d',
		minDate:'#F{$dp.$D(\''+beginId+'\')}',
		dateFmt:'yyyy-MM-dd'});
}

function wdateTimeBeginInstance(endId){
	//WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00',alwaysUseStartDate:true});
	WdatePicker({startDate:'%y-%M-%d 16:30:00',
		minDate:'16:30:00',
		maxDate:'#F{$dp.$D(\''+endId+'\')||\'2040-10-01\'}',
		dateFmt:'yyyy-MM-dd HH:mm:00'});
}

function wdateTimeEndInstance(beginId){
	WdatePicker({startDate:'%y-%M-%d 08:00:00',
		minDate:'#F{$dp.$D(\''+beginId+'\')}',
		dateFmt:'yyyy-MM-dd HH:mm:00'});
}

function wdateTimeInstance(){
	//WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00',alwaysUseStartDate:true});
	WdatePicker({startDate:'%y-%M-%d 16:30:00',
		minDate:'16:30:00',
		dateFmt:'yyyy-MM-dd HH:mm:00'});
}

function wdateYearMonthInstance(obj,fn){
	if(typeof(fn) == 'function'){
		WdatePicker({el:obj,dateFmt:'yyyy-MM',onpicked:fn});
	}else if(fn != null&&fn.Trim()!=''){
		WdatePicker({el:obj,dateFmt:'yyyy-MM',onpicked:eval(fn+'()')});
	}else{
		WdatePicker({el:obj,dateFmt:'yyyy-MM'});
	}
}

function wdateYearInstance(obj,fn){
	if(typeof(fn) == 'function'){
		WdatePicker({el:obj,dateFmt:'yyyy',onpicked:fn});
	}else if(fn != null&&fn.Trim()!=''){
		WdatePicker({el:obj,dateFmt:'yyyy',onpicked:eval(fn+'()')});
	}else{
		WdatePicker({el:obj,dateFmt:'yyyy'});
	}
}

/**
 * 获取毫秒数
 * 
 */
function getTimeInMillis(t,type){
	var millis = 0;
	if(typeof(t) == "undefined"||typeof(type) == "undefined"){
	}else if(type=='d'){
		millis = t*24*60*60*1000;
	}else if(type=='h'){
		millis = t*60*60*1000;
	}else if(type=='m'){
		millis = t*60*1000;
	}else if(type=='s'){
		millis = t*1000;
	}
	return millis;
}


//判断输入内容是否为空    
function IsNull(str){    
    if(str.length==0){    
        return true;
    }
    return false;
}    
   
//判断日期类型是否为YYYY-MM-DD格式的类型    
function IsDate(str){     
    if(str.length!=0){    
        var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;     
        var r = str.match(reg);     
        if(r==null)    
            return false;
        return true;
    }
}     
   
//判断日期类型是否为YYYY-MM-DD hh:mm:ss格式的类型    
function IsDateTime(str){     
    if(str.length!=0){    
        var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;     
        var r = str.match(reg);     
        if(r==null)    
        	return false; 
        return true;
    }    
}     
   
//判断日期类型是否为hh:mm:ss格式的类型    
function IsTime(str){     
	reg=/^((20|21|22|23|[0-1]\d)\:[0-5][0-9])(\:[0-5][0-9])?$/     
    if(!reg.test(str)){    
    	return false;
    }
	return true;
}     
   
//判断输入的字符是否为英文字母    
function IsLetter(str){     
	reg=/^[a-zA-Z]+$/;     
    if(!reg.test(str)){    
        return false;
    }
    return true;
}     
   
//判断输入的字符是否为整数    
function IsInteger(str){       
	reg=/^[-+]?\d*$/;     
    if(!reg.test(str)){    
        return false;
    }
    return true;
}     
   
//判断输入的字符是否为双精度    
function IsDouble(str) {     
	reg=/^[-\+]?\d+(\.\d+)?$/;    
    if(!reg.test(str)){    
        return false;
    }
    return true;
}     
   
   
//判断输入的字符是否为:a-z,A-Z,0-9    
function IsString(str) {     
	reg=/^[a-zA-Z0-9_]+$/;     
    if(!reg.test(str)){    
        return false;
    }
    return true;
}     
   
//判断输入的字符是否为中文    
function IsChinese(str) {     
	reg=/^[\u0391-\uFFE5]+$/;    
    if(!reg.test(str)){    
       	return false;
    }
    return true;
}
   
//判断输入的EMAIL格式是否正确    
function IsEmail(str) {     
	reg=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;    
    if(!reg.test(str)){    
        return false;
    }
    return true;
}     
   
//判断输入的邮编(只能为六位)是否正确    
function IsZIP(str){     
	reg=/^\d{6}$/;    
    if(!reg.test(str)){    
        return false;
    }
    return true;
}     
 //Phone : /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/    
 //Mobile : /^((\(\d{2,3}\))|(\d{3}\-))?13\d{9}$/    
 //Url : /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/   
 //IdCard : /^\d{15}(\d{2}[A-Za-z0-9])?$/   
 //QQ : /^[1-9]\d{4,8}$/   
 //某种特殊金额：/^((\d{1,3}(,\d{3})*)|(\d+))(\.\d{2})?$/               //说明：除“XXX    XX,XXX    XX,XXX.00”格式外

/**
 * 获取日期
 */
function getTime() {
	var now= new Date(),
	h=now.getHours(),
	m=now.getMinutes(),
	s=now.getSeconds(),
	ms=now.getMilliseconds();
	return (h+":"+m+":"+s+ " " +ms);
}

/**
 * 日期格式化
 * var nowStr = now.format("yyyy-MM-dd hh:mm:ss"); 
 * @param fmt
 * @returns
 */
Date.prototype.format = function(fmt)
{
  var o = {
    "M+" : this.getMonth()+1,                 // 月份
    "d+" : this.getDate(),                    // 日
    "h+" : this.getHours(),                   // 小时
    "m+" : this.getMinutes(),                 // 分
    "s+" : this.getSeconds(),                 // 秒
    "q+" : Math.floor((this.getMonth()+3)/3), // 季度
    "S"  : this.getMilliseconds()             // 毫秒
  };
  if(/(y+)/.test(fmt))
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
  for(var k in o)
    if(new RegExp("("+ k +")").test(fmt))
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
  return fmt;
}

//用字符串分割, 精确到日
function diffDays(day1, day2){
    var y1, y2, m1, m2, d1, d2;//year, month, day;
    y1 = parseInt(day1);
    y2 = parseInt(day2.split('-')[0]);

    m1 = parseInt(day1.split('-')[1].split('-')[0]);
    m2 = parseInt(day2.split('-')[1]);

    d1 = parseInt(day1.split('-')[1].split('-')[1].split('-')[0]);
    d2 = parseInt(day2.split('-')[2]);

    var date1 = new Date(y1, m1, d1);
    var date2 = new Date(y2, m2, d2);
     
    //用距标准时间差来获取相距时间
    var minsec = Date.parse(date2) - Date.parse(date1);
    var days = minsec / 1000 / 60 / 60 / 24; //factor: second / minute / hour / day

    return days;
}

//用正则, 且精确到小时
function diffDays1(day1, day2){
    var y1, y2, y3, m1, m2, m3, d1, d2, d3, h1, h2, h3, _m1, _m2, _m3, s1, s2, s3;
    var reg = /-|\/|:| /;
    //dayinfo -  用正则分割
    var DI1 = day1.split(reg);
    var DI2 = day2.split(reg);

    var bhour = parseInt(DI1[3])+parseInt(DI1[4])/60+parseInt(DI1[5])/60/60;
    var ehour = parseInt(DI2[3])+parseInt(DI2[4])/60+parseInt(DI2[5])/60/60;
    days = ehour-bhour;
    if(days>8)days=8;
	if((DI1[0]+"-"+ DI1[1]+"-"+ DI1[2])!=(DI2[0]+"-"+ DI2[1]+"-"+ DI2[2])){
		diffDays((DI1[0]+"-"+DI1[1]+"-"+DI1[2]),(DI2[0]+"-"+DI2[1]+"-"+DI2[2]))*8+days;
	}
    return days;
}

//日期运算
function addDays(date, hour){
	var time = parseDate(date).getTime()+getTimeInMillis(parseFloat(hour), "h");
    return new Date(time).format("yyyy-MM-dd hh:mm:ss");
}

/*
 *   功能:实现VBScript的DateAdd功能.
 *   参数:interval,字符串表达式，表示要添加的时间间隔.
 *   参数:number,数值表达式，表示要添加的时间间隔的个数.
 *   参数:date,时间对象.
 *   返回:新的时间对象.
 *   var now = new Date();
 *   var newDate = DateAdd( "d", 5, now);
 *---------------   DateAdd(interval,number,date)   -----------------
 */
function DateAdd(interval, number, date) {
    switch (interval) {
    case "y ": {
        date.setFullYear(date.getFullYear() + number);
        return date;
        break;
    }
    case "q ": {
        date.setMonth(date.getMonth() + number * 3);
        return date;
        break;
    }
    case "m ": {
        date.setMonth(date.getMonth() + number);
        return date;
        break;
    }
    case "w ": {
        date.setDate(date.getDate() + number * 7);
        return date;
        break;
    }
    case "d ": {
        date.setDate(date.getDate() + number);
        return date;
        break;
    }
    case "h ": {
        date.setHours(date.getHours() + number);
        return date;
        break;
    }
    case "m ": {
        date.setMinutes(date.getMinutes() + number);
        return date;
        break;
    }
    case "s ": {
        date.setSeconds(date.getSeconds() + number);
        return date;
        break;
    }
    default: {
        date.setDate(d.getDate() + number);
        return date;
        break;
    }
    }
    //测试
    //var now = new Date();
	 // 加五天.
	// var newDate = DateAdd("d ", 5, now);
	// alert(newDate.toLocaleDateString())
	 // 加两个月.
	// newDate = DateAdd("m ", 2, now);
	// alert(newDate.toLocaleDateString())
	 // 加一年
	// newDate = DateAdd("y ", 1, now);
	// alert(newDate.toLocaleDateString())
}


function parseDate(dateStr){
	var reg = /-|\/|:| /;
	var d = dateStr.split(reg);
	return new Date(d[0], d[1]-1, d[2],
			typeof(d[3]) == "undefined"?"00":d[3],
			typeof(d[4]) == "undefined"?"00":d[4],
			typeof(d[5]) == "undefined"?"00":d[5]);
}

/**
 * 字符串转日期
 */
function getDate(strDate) {  
    var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,  
     function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');  
    return date;  
}

/**
 * RGB颜色转换为16进制
 */
var reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/;
String.prototype.colorHex = function(){
	var that = this;
	if(/^(rgb|RGB)/.test(that)){
		var aColor = that.replace(/(?:\(|\)|rgb|RGB)*/g,"").split(",");
		var strHex = "#";
		for(var i=0; i<aColor.length; i++){
	var hex = Number(aColor[i]).toString(16);
	if(hex === "0"){
		hex += hex;	
	}
	strHex += hex;
		}
		if(strHex.length !== 7){
	strHex = that;	
		}
		return strHex;
	}else if(reg.test(that)){
		var aNum = that.replace(/#/,"").split("");
		if(aNum.length === 6){
	return that;	
		}else if(aNum.length === 3){
	var numHex = "#";
	for(var i=0; i<aNum.length; i+=1){
		numHex += (aNum[i]+aNum[i]);
	}
	return numHex;
		}
	}else{
		return that;	
	}
};
/**
 * 16进制颜色转为RGB格式
 * @returns
 */
String.prototype.colorRgb = function(){
	var sColor = this.toLowerCase();
	if(sColor && reg.test(sColor)){
		if(sColor.length === 4){
	var sColorNew = "#";
	for(var i=1; i<4; i+=1){
		sColorNew += sColor.slice(i,i+1).concat(sColor.slice(i,i+1));	
	}
	sColor = sColorNew;
		}
		//处理六位的颜色值
		var sColorChange = [];
		for(var i=1; i<7; i+=2){
	sColorChange.push(parseInt("0x"+sColor.slice(i,i+2)));	
		}
		return "RGB(" + sColorChange.join(",") + ")";
	}else{
		return sColor;	
	}
};
function validateNum(obj){
	if(!isNum(obj.value.Trim())){
		showMsgInfo('请输入合法的数字');
		obj.value='';
		return false;
	};
}
/**
 * 验证表单
 * @param form
 * @returns {Boolean}
 */
function validateForm(form){
	var isTrue = true;
	var valForm = $("#"+form+"").find("*");
	$.each(valForm,function(i,v){
		if(typeof($(this).attr("norequired")) != "undefined"&&($(this).attr("norequired")==''||$(this).attr("norequired").length>0)){
		}else{
			if($(this).is('input')){
				if(v.type!='file'&&(v.value.Trim() == ""||v.value.Trim() == "根目录")) {
					$("#"+form+" #"+v.id).focus(function(){
						$("#"+form+" #"+v.id).css("background-color","#FCC");
					});
					$("#"+form+" #"+v.id).blur(function(){
						$("#"+form+" #"+v.id).css("background-color","");
					});
					v.focus();
					isTrue =  false;
					if(v.title!=''){
						showMsgInfo(v.title+"不能为空！");
					}
					return isTrue;
				}
			}
			if($(this).is('select')){
				if (typeof($(this).attr("multiple")) == "undefined") {
					if(v.value.Trim() == "") {
						$("#"+form+" #"+v.id).focus(function(){
							$("#"+form+" #"+v.id).css("background-color","#FCC");
						});
						$("#"+form+" #"+v.id).blur(function(){
							$("#"+form+" #"+v.id).css("background-color","");
						});	
						v.focus();
						isTrue =  false;
						if(v.title!=''){
							showMsgInfo(v.title+"不能为空！");
						}
						return isTrue;
					}
				}else{
				}
			}
			if($(this).is('textarea')){
				if(v.value.Trim() == "") {
					v.focus();
					isTrue =  false;
					if(v.title!=''){
						showMsgInfo(v.title+"不能为空！");
					}
					return isTrue;
				}
			}
		}
	});
	return isTrue;
}

/**
 * 
 * @param obj_id
 * @returns {String}
 */
function getParams(obj_id){
	var urlparam = "";
	var params = $("#"+obj_id+" input");
	$.each(params,function(i,v){
		if(v.name.Trim() == '' || v.value == ""||v.type=='button') return;
		if(urlparam != ""){
			urlparam+="&";
		}
		if(v.type == 'radio'){
			if(typeof($("#"+obj_id+" input[id='"+v.id+"']:checked").val()) == "undefined"){
			}else{
				urlparam += v.name +"="+encodeURIComponent($("#"+obj_id+" input[id='"+v.id+"']:checked").val().Trim());
			}
		}else{
			urlparam += v.name +"="+encodeURIComponent(v.value.Trim());
		}
	});
	params = $("#"+obj_id+" textarea");
	$.each(params,function(i,v){
		if(v.name.Trim() == '' || v.value == "") return;
		if(urlparam != ""){
			urlparam+="&";
		}
		urlparam += v.name +"="+encodeURIComponent(v.value.Trim());
	});	
	params = $("#"+obj_id+" select");
	$.each(params,function(i,v){
		if(v.name.Trim() == '' || v.value == "") return;
		if(urlparam != ""){
			urlparam+="&";
		}
		urlparam += v.name +"="+encodeURIComponent(v.value.Trim());
	});	
	return urlparam;
}

/**
 * 
 * @param obj_id
 * @returns {String}
 */
function getParamsJson(obj_id){
	var urlparam = {};
	var params = $("#"+obj_id+" input");
	$.each(params,function(i,v){
		if(v.name.Trim() == '' || v.value == ""||v.type=='button') return;
		if(v.type == 'radio'){
			if(typeof($("#"+obj_id+" input[id='"+v.id+"']:checked").val()) == "undefined"){
			}else{
				urlparam[v.name]=$("#"+obj_id+" input[id='"+v.id+"']:checked").val();
			}
		}else if(v.type == 'checkbox'){
			if(typeof($("#"+obj_id+" input[name='"+v.name+"']:checked").val()) == "undefined"){
			}else{
				var check_box_val = '';
				$("#"+obj_id+" input[name='"+v.name+"']:checked").each(function(){
					check_box_val += $(this).val().Trim()+",";
				});
				if(check_box_val.length>0){
					urlparam[v.name] = check_box_val.substring(0, check_box_val.length-1);
				}
			}
		}else{
			urlparam[v.name]=v.value.Trim();
		}
	});
	params = $("#"+obj_id+" textarea");
	$.each(params,function(i,v){
		if(v.name.Trim() == '' || v.value == "") return;
		urlparam[v.name]=v.value.Trim();
	});	
	params = $("#"+obj_id+" select");
	$.each(params,function(i,v){
		if (typeof($("#"+v.id).attr("multiple")) == "undefined") {
			if(v.name.Trim() == '' || v.value == "") return;
			urlparam[v.name]=v.value.Trim();
		}else{
		}
	});
	return urlparam;
}

function disabledOperat(obj_id,obj_disabled){
	 // 遍历div中的所有input找到id最大值赋给max
	 $("#" + obj_id + " input").each(function() {
		 if(typeof(obj_disabled) != "undefined"&&(obj_disabled != '' && obj_disabled == 'disabled')){
			 $(this).attr("disabled",obj_disabled==''?"":obj_disabled);
		 }else{
			 $(this).removeAttr("disabled");
		 }
		 
	 });
	 $("#" + obj_id + " select").each(function() {
		 if(typeof(obj_disabled) != "undefined"&&(obj_disabled != '' && obj_disabled == 'disabled')){
			 $(this).attr("disabled",obj_disabled==''?"":obj_disabled);
		 }else{
			 $(this).removeAttr("disabled");
		 }
	 });
	 $("#" + obj_id + " textarea").each(function() {
		 if(typeof(obj_disabled) != "undefined"&&(obj_disabled != '' && obj_disabled == 'disabled')){
			 $(this).attr("disabled",obj_disabled==''?"":obj_disabled);
		 }else{
			 $(this).removeAttr("disabled");
		 }
	 });	 
}

function readonlyOperat(obj_id,obj_disabled){
	 // 遍历div中的所有input找到id最大值赋给max
	 $("#" + obj_id + " input").each(function() {
		$(this).attr("readonly",obj_disabled==''?"":obj_disabled);
	 });
	 $("#" + obj_id + " textarea").each(function() {
		$(this).attr("readonly",obj_disabled==''?"":obj_disabled);
	 });	 
}

function inner_html(url,params,inner_div,fn){
	getHtml(url,params,inner_div,fn);
}

function getHtml(url,params,inner_div,fn){
	if(params != null){
		params['menu_id']=_menu_id;
	}
	$.ajax({
		url: url,
		type: "post",
		dataType: "html",
		data: params,
		cache:false,
		timeout: 300000,
		async:true,
		success: function(data){
			if(typeof(fn) == 'function'){
				fn(data);
			}else{
				if(fn == null||fn.Trim()==''){
					$("#"+inner_div).html(data);
				}else{
					eval(fn+'(data)');
				}
			}
		},
		beforeSend:function(){
			$("#"+inner_div).html("<div style=\"text-align:center;padding-top:20px;\"><img src=\""+ctx+"/images/loader6.gif\" width=\"80\" height=\"80\"/></div>");
		},
		complete:function(){
		}
	});
}

function showInfo(message){
	jAlert(message,'提示信息');
}

function showInfoCheck(fn){
	jCheck('审核意见:', '', '审核', function(r,s) {
		if(typeof(fn) == 'function'){
			fn(r,s);
		}else{
			if(fn == null||fn.Trim()==''){
			}else{
				eval(fn+'(r,s)');
			}
		}
	});
}

function showMsgInfo(message){
	var position = "center";
	//var scrollpos = $(document).scrollTop();
	//alert(scrollpos);
	//if(scrollpos < 50) position = "customtop-right";
	$.jGrowl(message, { life: 5000, position: position});
	return false;
};

function showHtml(url,title,_width,_height){
	if (typeof(_width) == "undefined") {
		_width = 900;
	} 
	if (typeof(_height) == "undefined") {
		_height = ($(window).height()-100);
	} 
	var content = '<iframe scrolling="yes" id="j_content_frame" name="j_content_frame" frameborder="0"  src="'+url+'" style="width:'+(_width)+'px;height:'+(_height)+'px;"></iframe>';
	jContent(content,title);
	return false;
}

function click_(url){
	window.open(url, "_blank", "fullscreen, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes, location=yes, status=yes");
}
function click_no(url){
	var scnWidth = screen.availWidth;//浏览器的屏幕的可用宽度
	var scnHeight = screen.availHeight;
	window.open(url,'addWindow','height='+scnHeight+', width='+scnWidth+',left=0,top=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,titlebar=no');
}

function click_href(url){
	window.document.location.href=url;
}

// 用户退出
function loginOut(){
	if(confirm("确认退出吗？")){
	    location.href=ctx+'/main/loginOut.do';
	}
}
function updatePass(){
	if(confirm("确认修改密码吗？")){
	    openPop(ctx+'/jsp/healthrate/userUpdatePass.jsp','修改密码');
	}
}
		
// 返回首页
function goMain(){
	location.href=ctx+"/index.jsp";
}		

/**
 * 
 * jquery文件下载
 * 
 */
function downFile(url,param){
	$.fileDownload(url,{
		httpMethod: 'POST',
		data:param,
		prepareCallback:function(url){
			jMask();
		},
		successCallback:function(url){
			jMaskClose();
		},
		failCallback: function (html, url) {
			jMaskClose();
		}
	});
}


/**
 * ajax请求
 * async:true,同步
 * @param url
 * @param param 参数 例：var param = { name:'1', pwd:'2' };
 * @param ajax成功执行完后回调函数
 */
function ajaxUrl(url,param,fn){
	param['menu_id']=_menu_id;
	$.ajax({
		url : url, // 请求链接
		data: param,
		type:"post",     // 数据提交方式
		dataType:"json", // 接受数据格式
		cache: true,
		timeout: 300000,
		async:true,
		success:function(json){
			if(typeof(fn) == 'function'){
				fn(json);
			}else{
				if(fn == null||fn.Trim()==''){
				}else{
					eval(fn+'(json)');
				}
			}
		},
		beforeSend:function(){
			jMask();
		},
		complete:function(){
			jMaskClose();
		},
		error:function(json){
			if(typeof(fn) == 'function'){
				fn(json);
			}else{
				if(fn == null||fn.Trim()==''){
				}else{
					eval(fn+'(json)');
				}
			}
		}
	});	
}

/**
 * ajax请求
 * async:true,异步
 * @param url
 * @param param 参数 例：var param = { name:'1', pwd:'2' };
 * @param ajax成功执行完后回调函数
 */
function ajaxUrlFalse(url,param,fn){
	param['menu_id']=_menu_id;
	alert(url);
	$.ajax({
		url : url, // 请求链接
		data: param,
		type:"post",     // 数据提交方式
		dataType:"json", // 接受数据格式
		cache: true,
		timeout: 300000,
		async:false,
		success:function(json){
			if(typeof(fn) == 'function'){
				fn(json);
			}else{
				if(fn == null||fn.Trim()==''){
				}else{
					eval(fn+'(json)');
				}
			}
		},
		beforeSend:function(){
		},
		complete:function(){
		},
		error:function(json){
			if(typeof(fn) == 'function'){
				fn(json);
			}else{
				if(fn == null||fn.Trim()==''){
				}else{
					eval(fn+'(json)');
				}
			}
		}
	});	
}


/**
 * ajax Form 表单请求
 * @param _form
 * @param _url
 * @param _param 参数 例：var param = { name:'1', pwd:'2' };
 * @param _fn ajax成功执行完后回调函数
 */
function ajaxFormUrl(_form,_url,_param,_fn){
	_param['menu_id']=_menu_id;
	$("#"+_form).ajaxSubmit({
        type: 'post', //提交方式 get/post
        url: _url, // 需要提交的 url
        data: _param,
        dataType:'json',
        success: function(json) { 
        	if(_fn == null||_fn.Trim()==''){
			}else{
				eval(_fn+'(json)');
			}
        	jMaskClose();
        },
        beforeSubmit:function(){
        	jMask();
        }
    });	
}

function createRigthMenu(params){
	$('body').append("<div id='right_menu'></div>");
	inner_html(ctx+'/share/jsp/right_menu.jsp',
			params,
			'right_menu',
			function(html_str){
				$("#right_menu").html(html_str);
			});
}

function getAll(begin,end){
	var date_str = '';
	var begin_date = parseDate(begin);
	var end_date = parseDate(end);
	var begin_time=begin_date.getTime();
    var end_time=end_date.getTime();
	if(begin_time<=end_time){
		for(var k=begin_time;k<=end_time;){
	    	date_str += new Date(k).format("yyyy-MM-dd")+",";
	    	k=k+24*60*60*1000;
	    }
		date_str = date_str.substring(0,date_str.length-1);
	}
	return date_str;
}

/**
 * 从服务端获取对应值填给input等
 * @param _url
 * @param param
 * @param objFormDiv
 */
function valueIn(_url,param,objFormDiv,fn){
	ajaxUrl(_url,param,(typeof(fn) == 'function'?fn:(function(json){
		$.each(json.rows, function (n, value) {
        	$.each(value,function(name,value) {
				$("#"+objFormDiv+" #"+name).val(value);
			});
        });
	})));
}

function down_file(upload_uuid){
	if(upload_uuid.Trim().length==0){
	}else{
		click_href(ctx+'/share/jsp/showUploadFile.jsp?upload_uuid='+upload_uuid);
	}
}
//监听窗口大小调整事件，每次窗口改变大小就触发该事件
//$(window).resize(function(){
//    var elem = $(this);
//    // 更新窗口宽度和高度
//    // 可以替换这段代码，做一些有用的事情
//    $('#window-info').text( 'window width: ' + elem.width() + ', height: ' + elem.height() );
//});

/**
 * menu_url:最顶层URL;
 * menu_id:最顶层菜单ID;
 * sub_menu_id:要跳转的标签页菜单ID;
 * params:url拼接字符串参数；k1:v2&k2:v2&k3:v3
 */
function jumpLabelPage(menu_url,menu_id,sub_menu_id,params){
	if($(".header .headermenu li").length>0){
		$(".header .headermenu li").each(function(){
			if($(this).attr("id")=="head_"+menu_id){
				$(this).addClass('current');
			}else{
				$(this).removeClass('current');
			}
		});
		head_click(menu_url,menu_id,sub_menu_id,params);
	}else{
		parent.jumpLabelPage(menu_url,menu_id,sub_menu_id,params);
	}
}

/**
 * 
 * @param color
 */
function colorRGB2Hex(r,g,b){
	return "#" + ((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1);
}