<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="org.springframework.data.domain.Page" required="true"%>
<%@ attribute name="paginationSize" type="java.lang.Integer" required="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	int current = page.getNumber() + 1;
	int begin = Math.max(1, current - paginationSize / 2);
	int end = Math.min(begin + (paginationSize - 1), page.getTotalPages());

	request.setAttribute("current", current);
	request.setAttribute("begin", begin);
	request.setAttribute("end", end);
%>
<script>
	function pagechange(pageNo,pageSize){
		document.getElementById("pagination_pageNo").value = pageNo;
		document.getElementById("pagination_pageSize").value = pageSize;
		document.getElementById("pagechangeform").submit();
	}
	
	function gotopage(totalpage,pagesize){
			var pagenumber = document.getElementById("pagenumber");
			if(pagenumber){
				var v = pagenumber.value;
				if(v){
					try{
						var num = parseInt(v);
						if(num >= 1 && num <= totalpage){
							getNewsList(num,pagesize);
						}else{
							pagenumber.value = "";
						}
					}catch(e){
						pagenumber.value = "";
					}
				}
			}
		}
</script>

<form id="pagechangeform" action="" method="post">
		<input id="pagination_pageNo" name="pageNo" type="hidden"/>
		<input id="pagination_pageSize" name="pageSize" type="hidden"/>
		<c:forEach items="${searchparamlist}" var="paraminfo">
			<input type="hidden"  name="${paraminfo.key}" value="${paraminfo.value}"/>
		</c:forEach> 
	</form>

	<c:if test="${page.totalPages > 0}">
		<div class="pagination-content">
		<%
			if (page.hasPreviousPage()) {
		%>
		
		<a href="javascript:pagechange(1,${page.size})">首页</a>	
		<a href="javascript:pagechange(${current-1},${page.size})"  class="pagination-btn ajax-page-btn "><i class="caret caret-right"></i></a>
		<%
			}
		%>
		<div class="pagination-show"><span class="current-page">${current}</span>/<span class="total-page">${page.totalPages}</span></div>
		<%
			if (page.hasNextPage()) {
		%>
		<a href="javascript:pagechange(${current+1},${page.size});"  class="pagination-btn ajax-page-btn"><i class="caret caret-left"></i></a>
		
		<a href="javascript:pagechange(${page.totalPages},${page.size})">末页</a>
		<%
			}
		%>
		<div class="pagination-show"><input id="pagenumber" type="text" class="pagination-input goto-page-num"></div>
		<div class="pagination-show"><a href="javascript:gotopage(${page.totalPages},${page.size});" class="pagination-btn ajax-page-btn goto-btn">跳转</a></div>
	</div>
</c:if>
	





