<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="com.yq.faurecia.pojo.*"%>
<%@ page import="com.yq.faurecia.service.*"%>
<%
	SimpleDateFormat sdf = new SimpleDateFormat(Global.DATE_FORMAT_STR_A);
	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR);
	int m = cal.get(Calendar.MONTH)+1;
	String month = StringUtils.defaultIfEmpty(request.getParameter("month"), (m<10?"0"+m:m)+"");
	String holidays = StringUtils.defaultIfEmpty(request.getParameter("holidays"),"");

	//String month = mi.getHolidays().split(",")[0].split("-")[1];
%>
	<style type="text/css">
	.calendar-new-box-table  {
		margin:3px;
		margin-left:120px;
		border-collapse: separate;
		border-spacing: 0;
		background-color: #f6f6f6;
		border: 1px solid #e9e9e9;
		width: 70%;
		clear: both;
	}
	
	.calendar-new-box-table  th {
		margin:1px;
		padding:1px;
		height: 50px;
		line-height: 50px;
		vertical-align: middle;
		border-right: 1px solid #e9e9e9;
		border-bottom: 1px solid #e9e9e9;
		font-weight: bold;
		color: #555;
		background-color: #bdebee;
		font-size: 16px;
	}
	
	.calendar-new-box-table  tr {
		line-height: 38px;
	}
	
	.calendar-new-box-table  td {
		margin:1px;
		padding:1px;
		border-right: 1px solid #e9e9e9;
		border-bottom: 1px solid #e9e9e9;
		text-align: center;
		vertical-align: middle;
		color: #666;
		height: 50px;
		line-height: 50px;
		background-color: #ffffff;
		font-size: 16px;
		font-weight: bold;
	}
	
	.calendar-new-box-table  label {
		width:40px;
		float: none;
	}	
	
	.calendar-new-box-table  tr:hover>td {
		background-color: #f2fbfe;
	}
	</style>
	<script type="text/javascript">
		$(function(){
			showDays();
		});

	    function showDays(){
	            $('#dates-table').empty();//将table标签中的内容清空  
	            var dateStr = $('#scheduleDates').val();
	            var dateArr = dateStr.split("-"); // 将年份和月份分开  
	            var daysLen = new Date(dateArr[0],dateArr[1],0).getDate();// 获取该月的天数  
	            $('<tr><th>星期一</th><th>星期二</th><th>星期三</th><th>星期四</th><th>星期五</th><th>星期六</th><th>星期日</th></tr>').appendTo($('#dates-table'));  
	            var html="<tr>";// 组装html  
	            for(var i=1;i<=daysLen;i++){// 循环日期  
	                var weekIndex = new Date(dateArr[0],dateArr[1]-1,i).getDay();// 获取该天使星期几  
	                if(weekIndex == 0){//如果是星期日  
	                    if(i == 1){//并且是1号  
	                        for(var j=0;j<6;j++){// 星期一到星期六显示空格  
	                            html += "<td></td>";  
	                        }  
	                    }  
	                    html += '<td><div style="margin:3px;"><input type="checkbox" name="days" value="'+dateStr+'-'+i+'">'+i+'号<br/><select name="holiday_name" id="holiday_name" onchange="selectHoliday(this);" class="uniformselect"><option value="">---请选择---</option> <%for(String s:Global.holidays_name){ %> <option value="<%=s%>"><%=s%></option> <%} %></select></div></td>';  
	                    $(html+"</tr>").appendTo($('#dates-table'));  
	                    html = "<tr>";  
	                    continue;  
	                }else if(i == 1){// 如果是1号  
	                    for(var j=1;j<weekIndex;j++){  
	                        html += "<td></td>";  
	                    }  
	                    html += '<td><div style="margin:3px;"><input type="checkbox" name="days" value="'+dateStr+'-'+i+'">&nbsp;'+i+'号<br/><select name="holiday_name" id="holiday_name" onchange="selectHoliday(this);" class="uniformselect"><option value="">---请选择---</option> <%for(String s:Global.holidays_name){ %> <option value="<%=s%>"><%=s%></option> <%} %></select></div></td>';  
	                }else if(i == daysLen){//如果是该月最后一天  
	                    html += '<td><div style="margin:3px;"><input type="checkbox" name="days" value="'+dateStr+'-'+i+'">&nbsp;'+i+'号<br/><select name="holiday_name" id="holiday_name" onchange="selectHoliday(this);" class="uniformselect"><option value="">---请选择---</option> <%for(String s:Global.holidays_name){ %> <option value="<%=s%>"><%=s%></option> <%} %></select></div></td>';  
	                    $(html+"</tr>").appendTo($('#dates-table'));  
	                    break;  
	                }else{  
	                    html += '<td><div style="margin:3px;"><input type="checkbox" name="days" value="'+dateStr+'-'+i+'">&nbsp;'+i+'号<br/><select name="holiday_name" id="holiday_name" onchange="selectHoliday(this);" class="uniformselect"><option value="">---请选择---</option> <%for(String s:Global.holidays_name){ %> <option value="<%=s%>"><%=s%></option> <%} %></select></div></td>';  
	                }  
	            }
	            setDefaultDay();
	            ajaxUrl(ctx+'/common/nationalHoliday/nationalHolidayView.do',{'holiday':dateStr+'-01'},
	            		function(json){
	            			$(json.rows).each(function(index) {
	            				loadHoliday(json.rows[index]);
	            			});		
	            		});
	        }
	        function setDefaultDay(){
	        	$("#dates-table [name='days']").each(function(){
		        	var date = getDate($(this).val());
		        	var day=date.getDay();
		            if(day==0||day==6){
			   			$(this).attr("checked",'true');
			    		$(this).next().next().val('<%=Global.holidays_name[2]%>');
			   		}else if(day>=1&&day<=5){
			   			$(this).attr("checked",'true');
			    		$(this).next().next().val('<%=Global.holidays_name[1]%>');
			   		}
				});
	        }
	        function loadHoliday(obj){
		        $("#dates-table [name='days']").each(function(){
		        	var date = getDate($(this).val());
		        	var day=date.getDay();
		            if(obj.holiday===date.format("yyyy-MM-dd")){
			    		$(this).attr("checked",'true');
			    		if(obj.state!=0){
			    			$(this).next().next().val(obj.holiday_name);
			    			if(obj.holiday_name=='<%=Global.holidays_name[0]%>'){
			    				$(this).next().next().css("background-color","#FCC");
			    			}
			    		}else{
			    			$(this).next().next().val("");
			    		}
			   		} else {
			    		//$(this).removeAttr("checked");
			   		}
		                    
				});
	        }
	        function selectHoliday(obj){
	        	if(obj.value!=''&&obj.value=='<%=Global.holidays_name[0]%>'){
	        		obj.style.backgroundColor="#FCC";
	        	}else{
	        		obj.style.backgroundColor="";
	        	}
	        }
	</script>
		<input style="width: 70%;" id="scheduleDates" readonly="readonly" type="text" value="<%=year+"-"+month %>" onclick="WdatePicker({el:'scheduleDates',dateFmt:'yyyy-MM',onpicked:showDays})"/>
		<!-- 定义一个table用于显示日期情况 -->  
		<table class="calendar-new-box-table" id="dates-table"></table>

