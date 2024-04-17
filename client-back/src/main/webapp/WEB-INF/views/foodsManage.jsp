<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<style>
    .wrapper {
        background-color: #9acd32;
        /*width: 90%;*/
        height: 500px;
        padding: 10px;

    }
    .search {
        background-color: #ffff32;
        height: 20%;
        width: 95%;
        margin: 10px auto 50px auto;
        position: relative;
    }
    .section {
        background-color: #eee8aa;
        width: 95%;
        height: 50%;
        margin: 0 auto;
    }
    .footer {
        background-color: #db7093;
        width: 95%;
        height: 10%;
        margin: 30px auto 0 auto;
    }
    .searchBox {
        position: absolute;
        right: 0;
        margin-right: 30px;
    }
    .checkbox-wrapper{
        display: flex;
        justify-content: flex-start;
    }
    .name-box {
        padding: 10px;
        width:10%;
    }
    form {
        padding : 10px;
    }
</style>
<head>
    <title>Title</title>
</head>
<body>
<div class="wrapper">
    <div class="search">
        <div class="checkbox-wrapper">
            <div class="name-box">재고</div>
            <div>
                <form>
                    <label for="check-all">전체</label>
                    <input type="radio" value="" id="check-all" name="stock">
                    <label for="exist">있음</label>
                    <input type="radio" value="Y" id="exist" name="stock">
                    <label for="none">없음</label>
                    <input type="radio" value="N" id="none" name="stock">
                </form>
            </div>
        </div>
        <div>
            <label for="select1">검색어</label>
            <select id="select1" name="select1">
                <option value="name">이름</option>
                <option value="value">종류</option>
                <option value="price">가격</option>
            </select>
            <input />
        </div>
        <button class="searchBox">검색</button>
    </div>
    <div class="section">
        <div>
            <c:forEach items="${foodList}" var="item">
                ${item.name}
                ${item.price}
                ${item.imgPath}
            </c:forEach>
        </div>
    </div>
    <div class="footer">

    </div>
</div>
</body>
</html>
