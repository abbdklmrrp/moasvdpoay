<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="includes/head.jsp">
        <jsp:param name="tittle" value="About "/>
    </jsp:include>
</head>
<body>
<jsp:include page="includes/headers/aboutHeader.jsp">
    <jsp:param name="pageName" value="about"/>
</jsp:include>

<section class="container" style="align-self: center">
    <div class="col-md-2"></div>
    <div class="col-md-8">
        <div class="col-md-12" style="text-indent: 20px;">
            <h1 style="border-bottom: 1px solid #000">About company</h1><br>
            <h4> jTelecom - pretty young, but already rapidly developing company that provides a wide range of services,
                including mobile voice communication, data transmission, messaging, fixed Internet.</h4>
            <h4> So far, the operator works only in one country - in Ukraine. As of May 15, 2017,
                jTelecom provides services to 52 mobile subscribers and 13 users of fixed Internet.</h4><br>
            <h1 style="border-bottom: 1px solid #000">Company history</h1><br>
            <h4>March 20 - April 10, 2017 &#8212; somewhere in the bowels of the NetCracker the idea of the product was
                being developed.</h4><br>
            <h4>April 11, 2017 &#8212; team of future jTelecom company was formed.</h4><br>
            <h4>April 12 - May 22, 2017 &#8212; team has been working hard to create something outstanding and
                previously unknown.</h4><br>
            <h4>May 23, 2017 - Day x, jTelecom was finally finished.</h4><br>
            <h4>May 30, 2017 - jTelecom was shown to investors and was approved. Its life has begun.</h4><br>
            <h1 style="border-bottom: 1px solid #000">jTelecom brand</h1><br>
            <h4>The jTelecom brand is estimated at 0.0 billion pounds and 6,040 man-hours. This is the most promising
                brand in Urine (according to Brand Finance rating).</h4>
        </div>
    </div>
</section>
<jsp:include page="includes/footer.jsp"/>
</body>
</html>
