<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<style>
    .wrapper{
        background-color: #ffc0cb;
        width: 90%;
        height: 500px;
        padding: 10px;
        box-sizing: border-box;
    }
    .search{
        background-color: #ffff32;
        height: 20%;
        width: 95%;
        margin: 10px auto 50px auto;
    }
    .section{
        background-color: #ffbb23;
        width: 95%;
        margin: 0 auto;
        height: 50%;
    }
    .select-btn-wrapper{
        background-color: #fff842;
        width: 95%;
        height: 20px;
        margin: 10px auto;
        display: flex;
        justify-content: right;
    }
    .select-btn{
        /*background-color: black;*/
    }
    .pagination-btn-wrapper{
        width: 95%;
        height: 20px;
        display: flex;
        justify-content: center;
    }
</style>
<head>
    <title>Title</title>
</head>
<body>
<%--<div class="wrapper">--%>
<%--    <div class="search">--%>

<%--    </div>--%>
<%--    <div class="section">--%>
<%--        <c:if test="${adminMemberList.size() > 10 }">--%>
<%--            <c:forEach items="${adminMemberList}" var="item" begin="0" end="3">--%>
<%--                <TABLE>--%>
<%--                    <tr>--%>
<%--                        <td>${item.name}</td>--%>
<%--                        <td>${item.mobilePhoneNum}</td>--%>
<%--                    </tr>--%>
<%--                </TABLE>--%>
<%--            </c:forEach>--%>
<%--        </c:if>--%>
<%--        <c:choose>--%>
<%--            <c:when test=""></c:when>--%>
<%--            <c:when test=""></c:when>--%>
<%--            <c:when test=""></c:when>--%>
<%--            <c:otherwise>saddas</c:otherwise>--%>
<%--        </c:choose>--%>
<%--    </div>--%>
<%--    <div class="select-btn-wrapper">--%>
<%--        <div class="select-btn">button</div>--%>
<%--    </div>--%>
<%--    <div class="pagination-btn-wrapper">--%>
<%--        <div class="pagination-btn">pagination button</div>--%>
<%--    </div>--%>
<%--</div>--%>
</body>
</html>
<%--<script>--%>
<%--    $(document).ready(--%>
<%--        getMember();--%>
<%--    )--%>
<%--    --%>
<%--    function getMember() {--%>
<%--        ajax.get("asddasd")--%>
<%--            .then((res) => {--%>
<%--                coasdasasdsa--%>
<%--            })--%>
<%--    }--%>
<%--</script>--%>