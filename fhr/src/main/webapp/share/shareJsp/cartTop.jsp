<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%-- <%@ page import="com.yq.authority.pojo.UserInfo" %> --%>
<%
	UserInfo user = (UserInfo)session.getAttribute("user");
	String roleNames = StringUtils.defaultIfEmpty((String)session.getAttribute("roleNames"),"");
%>
	<script type="text/javascript" src="${ctx }/share/js/top.js"></script>
    <div class="topheader">
        <div class="left">
        	<a href="javascript:goMain()">
<!--             <h1 style="float: left; margin: 2px 0"><a href="javascript:goMain()"> -->
<!--             <img alt="" src="${ctx }/images/image001.png" width="150" height="38"> -->
<!--             </a></h1> -->
            <h1 class="logo"><span>FAS</span></h1>
            <span class="slogan"><font style="font-size: 28px;font-weight: normal; color: #fb9337"><span>Anting</span></font></span>
            </a>
            <br clear="all" />
        </div><!--left-->
        <div class="right">
            <div class="userinfo">
            	<img src="${ctx }/share/jsp/showSessionImage.jsp?sessionObect=photo" alt="" width="28" height="28"/>
                <span><%=user.getZh_name() %></span>
            </div><!--userinfo-->
            
            <div class="userinfodrop">
            	<div class="avatar">
                	<a href=""><img src="${ctx }/share/jsp/showSessionImage.jsp?sessionObect=photo" alt=""  width="100" height="130" align="right" /></a>
                </div><!--avatar-->
                <div class="userdata">
                	<h4 title="<%=roleNames %>"><%=roleNames.split(",")[0] %></h4>
                    <span class="email"><%=StringUtils.defaultIfEmpty(user.getEmail(), "") %></span>
                    <ul>
                        <li><a href="javascript:showHtml(ctx+'/authority/user/user_pwd_edit.jsp?menu_id=0&user_id=<%=user.getId()%>','修改密码');">修改密码</a></li>
                        <li><a href="javascript:showMsgInfo('正在开发阶段......');">帮助</a></li>
                        <li><a href="javascript:loginOut();">退出</a></li>
                    </ul>
                </div><!--userdata-->
            </div><!--userinfodrop-->
        </div><!--right-->
    </div><!--topheader-->