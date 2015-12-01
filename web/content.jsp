<%--
  Created by IntelliJ IDEA.
  User: ccmicky
  Date: 15-9-2
  Time: 下午7:26
  To change this template use File | Settings | File Templates.

<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.*" errorPage="" %>
--%>
<%@ page contentType="text/html; charset=utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
<div class="pagelistbox">
  <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}" />
  <span>共 ${pager.totalPages} 页/${pager.totalRows}条记录 </span>
  <c:if test="${pager.hasPreviousPage}">
    <span class="indexPage"> <a href="${pageContext.request.contextPath}/${page_url}1">首页</a></span>
  </c:if>
  <c:forEach var="search" items="${sentlist}" varStatus="status">
    <table>
      <tr <c:if test="${status.count%2==0}"> bgcolor="#eeeeee" </c:if>>
        <td>${search}</td>
      </tr>
    </table>
  </c:forEach>
  <c:if test="${pager.hasNextPage}">
    <a class="nextPage" href="${pageContext.request.contextPath}/${page_url}${pager.nextPage}">下页</a>
    <a class="nextPage" href="${pageContext.request.contextPath}/${page_url}${pager.totalPages}">末页</a>
  </c:if>
</div>