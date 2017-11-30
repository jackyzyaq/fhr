function Guid()
{
return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
}

function GenerateGuid()
{
return Guid()+Guid()+Guid()+Guid()+Guid()+Guid()+Guid()+Guid();
}

function isTime(str) {
	var a = str.match(/^(\d{0,2}):(\d{0,2}):(\d{0,2})$/);
	if (a == null)
		return false;
	if (a[1] >= 24 || a[2] >= 60 || a[3] >= 60)
		return false;
	return true;
}
function isDateTime(str) {
	var a = str
			.match(/^(\d{0,4})-(\d{0,2})-(\d{0,2}) (\d{0,2}):(\d{0,2}):(\d{0,2})$/);
	if (a == null)
		return false;
	if (a[2] >= 13 || a[3] >= 32 || a[4] >= 24 || a[5] >= 60
			|| a[6] >= 60)
		return false;
	return true;
}
function isDate(str) {
	var a = str.match(/^(\d{0,4})-(\d{0,2})-(\d{0,2})$/);
	if (a == null)
		return false;
	if (a[2] >= 13 || a[3] >= 32 || a[4] >= 24)
		return false;
	return true;
}


function getThisPageSize() {
	// http://www.blabla.cn/js_kb/javascript_pagesize_windowsize_scrollbar.html
	var winW, winH;
	if (window.innerHeight) {// all except IE
		winW = window.innerWidth;
		winH = window.innerHeight;
	} else if (document.documentElement
			&& document.documentElement.clientHeight) {// IE 6 Strict Mode
		winW = document.documentElement.clientWidth;
		winH = document.documentElement.clientHeight;
	} else if (document.body) { // other
		winW = document.body.clientWidth;
		winH = document.body.clientHeight;
	} // for small pages with total size less then the viewport
	return {
		WinW : winW,
		WinH : winH
	};
}