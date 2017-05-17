<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <div class="col-md-2"></div>
    <div class="col-md-8">
        <h1 style="text-align: center">Tariffs Catalog</h1>
        <br>
        <div class="col-md-12">
            <h4 id="currentUserTariff"></h4>
        </div>
        <div class="table-responsive">
            <table border="1" class="table table-striped table-hover" id="tableTariffsCatalog">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Duration(days)</th>
                        <th>Price($)</th>
                        <th>Services</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody id="myTable">
                </tbody>
            </table>
        </div>
        <div class="col-md-12 text-center">
            <ul class="pagination" id="myPager"></ul>
        </div>
    </div>
    <div class="col-md-2"></div>
</div>