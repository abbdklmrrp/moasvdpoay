<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="includes/head.jsp">
        <jsp:param name="tittle" value="For business "/>
    </jsp:include>
</head>
<body>
<jsp:include page="includes/headers/aboutHeader.jsp">
    <jsp:param name="pageName" value="business"/>
</jsp:include>
<section class="container">
    <div class="col-md-2"></div>
    <div class="col-md-8">
        <div class="col-md-12" style="text-indent: 20px">
            <h1 style="border-bottom: 1px solid #000">For business</h1><br>
            <h4> The Ukrainian marketplace is constantly evolving. Today’s digital revolution has disrupted traditional
                methods
                of working, and at the same time the connected generation has entered the workplace. In the face of
                complex IT agendas and an unpredictable world, it’s more important than ever to be confidently connected
                across your organisation.</h4>
            <h4>Being Ready for the future means combining all your fixed, mobile and cloud needs. With a unified
                communications solution, your organisation will be able to adapt quickly, work efficiently and be more
                productive, so you can seize every opportunity and put your customers first.</h4>
            <h4>Partnering with us will help your business embrace total communications – today and into the
                future.</h4>
            <h4>With a flexible infrastructure for your business, you’ll be free to innovate, and to implement new
                communications technology. So you’ll be in a stronger position to manage and react to change – keeping
                you ahead of the game.</h4>
            <h4>With unstable economic conditions affecting the UA marketplace and the threat of cyber security high on
                the agenda, it’s never been more important to ensure your organisation has a connectivity infrastructure
                that’s flexible and resilient</h4>
            <h4>With these changes in place, your business can:</h4>
            <ul type="circle" style="text-indent: 0">
                <li>respond rapidly to market changes and make the most of any emerging opportunity, while reacting
                    swiftly to any imminent threat
                </li>
                <li>optimise operations with a renewed focus on your core business, with the assurance that your
                    employees get a consistent experience wherever they work
                </li>
                drive efficient growth and be primed to harness new critical business applications and next-generation
                technologies
                <li>drive efficient growth and be primed to harness new critical business applications and
                    next-generation technologies
                </li>
            </ul>
            <br>
            <div class="col-md-6">
                <div class="col-md-4" style="text-indent:0; padding-top: 10px">
                    <img src="<c:url value="/resources/photos/Network-big.png"/>" style="width: 100%">
                </div>
                <div class="col-md-8" style="text-indent:0">
                    <h4>We're the network you can trust</h4>
                    As well as providing increased 4G coverage indoors and out, we also have our own nationwide fibre
                    network. So we have the power to control the quality of our signal and to resolve network issues
                    fast.
                </div>
            </div>
            <div class="col-md-6">
                <div class="col-md-4" style="text-indent:0; padding-top: 10px">
                    <img src="<c:url value="/resources/photos/Partner-big.png"/>" style="width: 100%">
                </div>
                <div class="col-md-8" style="text-indent:0">
                    <h4>We're a transformation partner</h4>
                    As a total communications provider, we can help you be Ready for the new world. Our unique portfolio
                    across fixed, mobile and cloud services is designed to deliver all your communications needs in an
                    effective and holistic way, and transform the way you do business.
                </div>
            </div>
            <div class="col-md-6">
                <div class="col-md-4" style="text-indent:0; padding-top: 10px">
                    <img src="<c:url value="/resources/photos/Businesses-big.png"/>" style="width: 100%">
                </div>
                <div class="col-md-8" style="text-indent:0">
                    <h4>We help businesses get ready for the future</h4>
                    With us you can measure how prepared your business is for today –
                    and tomorrow. Our simple, interactive online assessment can help you gauge your organisation’s
                    strengths and weaknesses, compare your score with other organisations like yours, and put you on the
                    path towards unlocking its full potential.
                </div>
            </div>
            <div class="col-md-6">
                <div class="col-md-4" style="text-indent:0; padding-top: 10px">
                    <img src="<c:url value="/resources/photos/Pioneer-big.png"/>" style="width: 100%">
                </div>
                <div class="col-md-8" style="text-indent:0">
                    <h4>We pioneer Better Ways of Working</h4>
                    Using our tried and tested methodology, we’ll look at your use of people, space, processes and
                    technology. From this, we’ll deliver a tailored workplace solution to help you work more flexibly,
                    increase productivity and realise more efficiencies.
                </div>
            </div>
        </div>

    </div>
    </div>
</section>
<jsp:include page="includes/footer.jsp"/>
</body>
</html>
