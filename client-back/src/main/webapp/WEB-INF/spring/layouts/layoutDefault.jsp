<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<style>
    header,body, nav, section, footer { margin:0; padding:0;}
    a{text-decoration: none}
    header {height: 6vh}
    #section_wrap{
        display: flex;
        justify-content: space-between;
    }
    nav{
        width: 15%;
        height: 92.7vh;
        display: flex;
        flex-direction: column;
        justify-content: start;
        /*background-color: rgba(53,53,53,1.0);*/
        background: #263544;
        padding-top: 10px;
    }
    nav div{
        /*width:70%;*/
        /*text-align: center;*/
        margin-bottom: 20px;
        height: 7%;
        /*border: 1px solid;*/
        cursor: pointer;
        color: white;
    }
    nav div:hover{
        text-decoration: underline;
    }
    section{
        width: 90%;
    }
    .font_15{
        font-size: 15px;
    }
    .titleContain {
        /*position: relative;*/
        display: flex;
        justify-content: space-between;
        padding: 0 10px;
        align-items: center;
    }
    .default-section {
        padding: 20px;
        background-color: #f3f7fc;
    }
</style>
<html lang="ko">
<head>
    <link rel="stylesheet" href="/static/css/globalStyle.css" />
</head>
<body>
    <!-- header -->
    <tiles:insertAttribute name="header" />
    <!--// header -->
    <div id="section_wrap">
        <nav id="navigation">
        </nav>
        <section class="default-section">
            <div id="nav-bar"></div>
            <tiles:insertAttribute name="body" />
        </section>
    </div>
    <!-- footer -->
<%--    <tiles:insertAttribute name="footer" />--%>
    <!--// footer -->
</body>
</html>

<script>
    $(document).ready(
        function() {
            let path = window.location.pathname;
            if(path == "/"){
                $("#nav-bar").append("메인 홈");
            };
            $.ajax({
                url: "/getAdminMenu",
                type: "get",
                // data:JSON.stringify(params),
                contentType: "application/json",
                success: function (data) {
                    console.log(data);
                    let html;
                    data.forEach((el, index) => {
                        html = "";
                        html += '<a href='+ el.path +' class="titleContain">';
                        html += '<div class="font_15">' + el.title + '</div>';
                        if(el.path == path) {
                            html += '<div class="">v</div>';
                        }
                        html += '</a>'
                        $("#navigation").append(html);
                        if(el.path == path){
                            $("#nav-bar").append(el.title);
                        };
                    });
                },
                error: function (err){
                    console.log(err);
                }
            });
            console.log(path);
            // $("#nav-bar").append(nav);
        }
    )

</script>
