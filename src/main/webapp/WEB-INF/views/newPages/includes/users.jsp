<div class="container">
    <div class="col-md-12">
        <h1 style="text-align: center">${param.pageName}</h1>
        <div class="grid-progress-bar-placeholder">
            <div class="progress grid-progress-bar" style="display: none;" id="progressId">
                <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100"
                     aria-valuemin="0" aria-valuemax="100" style="width: 100%">
                    <span class="sr-only">in progress</span>
                </div>
            </div>
        </div>

        <div class="panel panel-default" id="productsIds">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-10" data-grid="title">
                        ${param.pageName}
                    </div>
                    <div class="col-md-2" style="text-align:right;">
                        <a href="javascript:" data-grid="pager-refresh">
                            Refresh
                        </a>
                    </div>
                </div>
            </div>
            <div class="panel-body">
                <div class="row" style="margin: 20px 0;">
                    <div class="col-md-2">
                        <select class="form-control" data-grid="pager-length">
                            <option value="10">10</option>
                            <option value="50">50</option>
                            <option value="100">100</option>
                            <option value="200">200</option>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <nav>
                            <ul class="pagination" style="margin:0px !important;" data-grid="pager">
                                <li data-grid="pager-prev">
                                    <a href="javascript:" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                                <li data-grid="pager-item"><a href="javascript:">1</a></li>
                                <li data-grid="pager-next">
                                    <a href="javascript:" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                    <div class="col-md-4">
                        <form class="form-inline" data-grid="search">
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Query">
                            </div>
                            <button type="button" class="btn btn-default">Search</button>
                        </form>
                    </div>
                </div>
                <table class="table table-striped table-bordered table-hover" data-grid="grid">
                    <thead>
                    <tr>
                        <th class="col-xs-2" data-grid-header="name" data-grid-header-sortable="true">
                            <div class="pull-right order-by">
                                <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                   data-grid-header-sortable-up="up"></a>
                                <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                   data-grid-header-sortable-down="down"></a>
                            </div>
                            Name
                        </th>
                        <th class="col-xs-2" data-grid-header="surname" data-grid-header-sortable="true">
                            <div class="pull-right order-by">
                                <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                   data-grid-header-sortable-up="up"></a>
                                <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                   data-grid-header-sortable-down="down"></a>
                            </div>
                            Surname
                        </th>
                        <th class="col-xs-2" data-grid-header="email" data-grid-header-sortable="true">
                            <div class="pull-right order-by">
                                <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                   data-grid-header-sortable-up="up"></a>
                                <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                   data-grid-header-sortable-down="down"></a>
                            </div>
                            Email
                        </th>
                        <th class="col-xs-1" data-grid-header="phone" data-grid-header-sortable="true">
                            <div class="pull-right order-by">
                                <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                   data-grid-header-sortable-up="up"></a>
                                <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                   data-grid-header-sortable-down="down"></a>
                            </div>
                            Phone
                        </th>
                        <th class="col-xs-3" data-grid-header="address" data-grid-header-sortable="true">
                            <div class="pull-right order-by">
                                <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                   data-grid-header-sortable-up="up"></a>
                                <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                   data-grid-header-sortable-down="down"></a>
                            </div>
                            Address
                        </th>
                        <th class="col-xs-1" data-grid-header="role_id" data-grid-header-sortable="true">
                            <div class="pull-right order-by">
                                <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                   data-grid-header-sortable-up="up"></a>
                                <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                   data-grid-header-sortable-down="down"></a>
                            </div>
                            Role
                        </th>
                        <th class="col-xs-1" data-grid-header="action">
                            Action
                        </th>
                    </tr>
                    </thead>
                    <div data-grid="message"></div>
                    <tbody>
                    <tr data-grid="row">
                        <td data-cell="name"></td>
                        <td data-cell="surname"></td>
                        <td data-cell="email"></td>
                        <td data-cell="phone"></td>
                        <td data-cell="address"></td>
                        <td data-cell="role_id"></td>
                        <td data-cell="action"></td>

                    </tr>
                    </tbody>
                </table>
                <div class="row" style="margin: 20px 0;">
                    <div class="col-md-2">
                        <select class="form-control" data-grid="pager-length">
                            <option value="10">10</option>
                            <option value="50">50</option>
                            <option value="100">100</option>
                            <option value="200">200</option>
                        </select>
                    </div>
                    <div class="col-md-10">
                        <nav>
                            <ul class="pagination" style="margin:0px !important;" data-grid="pager">
                                <li data-grid="pager-prev">
                                    <a href="javascript:" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                                <li data-grid="pager-item"><a href="javascript:">1</a></li>
                                <li data-grid="pager-next">
                                    <a href="javascript:" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
