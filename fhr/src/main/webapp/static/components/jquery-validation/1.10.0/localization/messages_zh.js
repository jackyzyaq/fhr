/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: ZH (Chinese, 中文 (Zhōngwén), 汉语, 漢語)
 */
(function($) {
	$.extend($.validator.messages, {
		required : "必选字段",
		remote : "请修正该字段",
		email : "请输入正确格式的电子邮件",
		url : "请输入合法的网址",
		date : "请输入合法的日期",
		dateISO : "请输入合法的日期 (ISO).",
		number : "请输入合法的数字",
		digits : "只能输入整数",
		creditcard : "请输入合法的信用卡号",
		equalTo : "请再次输入相同的值",
		accept : "请输入拥有合法后缀名的字符串",
		maxlength : $.validator.format("请输入一个长度最多是 {0} 的字符串"),
		minlength : $.validator.format("请输入一个长度最少是 {0} 的字符串"),
		rangelength : $.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
		range : $.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
		max : $.validator.format("请输入一个最大为 {0} 的值"),
		min : $.validator.format("请输入一个最小为 {0} 的值")
	});
}(jQuery));
// 验证正整数
jQuery.validator.addMethod("positiveValid", function(value, element, param) {
	return this.optional(element) || /^(0|[1-9][0-9]*)$/.test(value);
}, "请输入非负整数");

// 手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
	var length = value.length;
	var mobile = /^(1(([35][0-9])|(47)|[8][01236789]))\d{8}$/;
	// var tel = /^\d{3,4}-?\d{7,9}$/;
	return this.optional(element) || (mobile.test(value));
}, "请正确填写您的手机号码");
// 手机号码验证
jQuery.validator.addMethod("isPhone", function(value, element) {
	var length = value.length;
	var mobile = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	return this.optional(element) || (mobile.test(value));
}, "请正确填写您的电话号码");
