<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%
	String div_id = StringUtils.defaultIfEmpty(request.getParameter("div_id"), "tmp_div");
%>
<script>
$(function () { 
    //全选或全不选 
    $("#all").click(function(){    
        if(this.checked){    
            $("#<%=div_id%> :checkbox").attr("checked", true);   
        }else{    
            $("#<%=div_id%> :checkbox").attr("checked", false); 
        }    
     });  
    //全选   
    $("#selectAll").click(function () { 
         $("#<%=div_id%> :checkbox,#all").attr("checked", true);   
    });   
    //全不选 
    $("#unSelect").click(function () {   
         $("#<%=div_id%> :checkbox,#all").attr("checked", false);   
    });   
    //反选  
    $("#reverse").click(function () {  
         $("#<%=div_id%> :checkbox").each(function () {   
              $(this).attr("checked", !$(this).attr("checked"));   
         }); 
         allchk(); 
    }); 
     
    //设置全选复选框 
    $("#<%=div_id%> :checkbox").click(function(){ 
        allchk(); 
    }); 
  
    //获取选中选项的值
     $("#getValue").click(function(){
     	alert(getCheckBoxVals().join(','));
     });
});
function getCheckBoxVals(){
	var valArr = new Array;
    $("#<%=div_id%> :checkbox[checked]").each(function(i){ 
		valArr[i] = $(this).val(); 
	}); 
    return valArr;
}
function allchk(){ 
    var chknum = $("#<%=div_id%> :checkbox").size();//选项总个数 
    var chk = 0; 
    $("#<%=div_id%> :checkbox").each(function () {   
        if($(this).attr("checked")==true){ 
            chk++; 
        } 
    }); 
    if(chknum==chk){//全选 
        $("#all").attr("checked",true); 
    }else{//不全选 
        $("#all").attr("checked",false); 
    } 
}
</script>
<input type="checkbox" id="all"/>&nbsp;&nbsp;&nbsp;全选
<!-- <input type="button" value="全选" class="btn" id="selectAll"/>    -->
<!-- <input type="button" value="全不选" class="btn" id="unSelect"/>    -->
<!-- <input type="button" value="反选" class="btn" id="reverse"/>    -->
<!-- <input type="button" value="获得选中的所有值" class="btn" id="getValue"/> -->