<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container" style="width:60%; max-width: 400px;">
    <form id="formWithRegionsAndDates" action="${pageContext.request.contextPath}${param.downloadUrl}" method="get">
        <div class="login-form">
            <div class="form-group row">
                <label class="col-sm-4 control-label" for="sel1">Choose region</label>
                <div class="col-sm-8">
                    <select class="form-control" id="sel1" name="region" required>
                        <c:forEach var="region" items="${regions}">
                            <option value="${region.id}">${region.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 control-label" for="beginDate">Date Begin</label>
                <div class="col-sm-8">
                    <input class="form-control" id="beginDate" type="date" name="beginDate" onchange="fillData()"/>
                </div>
            </div>
            <div class="from-group row">
                <label class="col-sm-4 control-label" for="endDate">Date End</label>
                <div class="col-sm-8">
                    <input class="form-control" id="endDate" type="date" name="endDate" onchange="fillData()"/>
                </div>
            </div>
        </div>
    </form>
    <div class="row">
        <button class="btn btn-primary col-sm-5 col-xs-5" id="btnDownloadReport" disabled="disabled">Download report
        </button>
        <div class="col-sm-2 col-xs-2"></div>
        <button class="btn btn-primary col-sm-5 col-xs-5" id="btnShowReport" disabled="disabled">Show report</button>
    </div>
    <span id="err" style="color: red"></span>
</div>
<div class="container" style="margin-bottom: 30px; margin-top: 30px;">
    <div class="row">
        <div class="col-sm-12" id="line_top_x" style="height: 400px;"></div>
    </div>
    <br/>
    <div class="row">
        <div class="col-sm-3 col-lg-3"></div>
        <div class="col-sm-6 col-lg-6" id="table_div"></div>
        <div class="col-sm-3 col-lg-3"></div>
    </div>
</div>