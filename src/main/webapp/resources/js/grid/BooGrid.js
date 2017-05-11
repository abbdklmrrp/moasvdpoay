(function ($) {
  var EmptyDataSource = function (callback) {
    this.data = function (request, callback) {
      callback(true, {total: 0, data: []});
    }
  };

  $.fn.BooGrid = function (options) {
    var Monitor = function () {
      var m = false;
      this.value = function () {
        return m;
      };
      this.start = function () {
        m = true;
      };
      this.stop = function () {
        m = false;
      };
    };

    var self = this;
    var root = $('#' + options.id);
    var grid = findGrid(root);

    // templates
    var pagerItemTemplate = findPagerTemplate(root).first().remove();
    var rowTemplate = findRowTemplate(root);

    // state
    var length = 10;
    var currentPage = 1;
    var lastLength;
    var lastResponse;
    var refreshClicked;
    var first = true;

    var monitor = new Monitor();

    var gridHeaders = readIds(findHeaders(root));
    var ds = options.ds || new EmptyDataSource();
    var listeners = options.listeners || [];

    findPagers(root).remove();
    findRefresh(root).click(function(){refreshClicked = true; refresh();});
    findFilter(root).keydown(filter);
    findFilterButton(root).click(refresh);
    findPageLength(root).change(setLength);
    findPagerNext(root).click(pagerNext);
    findPagerPrev(root).click(pagerPrev);

    if (!options.manual) {
      refresh();
    }

    function sortData() {
      var atLeastOneAdded = false;
      var sort = '';
      for (var gridHeader in gridHeaders) {
        if (gridHeaders.hasOwnProperty(gridHeader)) {
          var sorting = gridHeaders[gridHeader];
         if (sorting != null) {
            if(sorting==true){
                sort = gridHeader + ' ASC';
            }else{
            sort = gridHeader + ' DESC';}
            atLeastOneAdded = true;
         }
        }
      }
      if (!atLeastOneAdded) {
        return null;
      }
      return sort;
    }

    function data() {
      var data = {
        start: currentPage - 1,
        length: length
      };
      var sorting = sortData();
      if (sorting) {
        data.sort = sorting;
      }
      var condition = (options.condition) ? options.condition() : null;
      if (condition) {
        data.condition = condition;
      }
      var search = findFilter(root).val();
      if (search) {
        data.search = search;
      }
      return data;
    }

    function setLength() {
      if (monitor.value()) {
        return;
      }
      length = $(this).val();
      currentPage = 1;
      refresh();
    }

    function refresh() {
      if (monitor.value()) {
        return;
      }

      monitor.start();

      var request = data();
      
      // ship with UI activities
      if (refreshClicked) {
        request.refreshed = true;
      }
      refreshClicked = false;
      
      if(first) {
        request.first = true;
        first = false;
      }
      
      var needToStop = false;

      for (var ind = 0; ind < listeners.length; ind++) {
        var result = listeners[ind].start(request);
        // stop?
        if (result === true) {
          needToStop = true;
          return;
        }
      }

      if (!needToStop) {
        ds.data(request, function (success, response) {
          lastResponse = response;
          if (success) {
            if(lastLength && lastLength != lastResponse.total && currentPage * length > lastResponse.total) {
              currentPage = 1;
            }
            lastLength = lastResponse.total;

            renderPager(lastResponse, request.length);
            renderData(lastResponse);
          } else {
            alert('Failed. Try again later');
          }

          monitor.stop();

          for (var ind = 0; ind < listeners.length; ind++) {
            listeners[ind].stop(success, response);
          }

          if (options.refreshCallback instanceof Function) {
            options.refreshCallback();
          }
        });
      }
    }

    function filter(e) {
      if (e.keyCode == 13) {
        refresh();
      }
    }

    function pagerNext() {
      if (monitor.value()) {
        return;
      }
      var count = Math.ceil(lastResponse.total / length);
      if (lastResponse.length == 0 || currentPage == count) {
        return;
      }
      currentPage++;
      refresh();

    }

    function pagerPrev() {
      if (monitor.value()) {
        return;
      }
      if (lastResponse.length == 0 || currentPage == 1) {
        return;
      }
      currentPage--;
      refresh();
    }

    function readIds(headers) {
      var ids = {};
      headers.each(function (i, e) {
        var element = $(e);
        var header = element.attr('data-grid-header');
        ids[header] = null;
        if (!!element.attr('data-grid-header-sortable')) {
          var upElement = element.find('[data-grid-header-sortable-up]');
          var downElement = element.find('[data-grid-header-sortable-down]');
          sortUp(header, upElement, downElement);
          sortDown(header, upElement, downElement);
        }
      });
      return ids;
    }

    function sortUp(header, upElement, downElement) {
      upElement.click(function () {
          for (var gridHeader in gridHeaders) {
            gridHeaders[gridHeader]===null;
          }
        if (gridHeaders[header] === false || gridHeaders[header] === null) {
          gridHeaders[header] = true;
        } else {
          gridHeaders[header] = null;
        }
        upElement.removeClass("active");
        downElement.removeClass("active");
        if (gridHeaders[header] === true) {
          upElement.addClass("active");
        }
        refresh();
      });
    }

    function sortDown(header, upElement, downElement) {
      downElement.click(function () {
          for (var gridHeader in gridHeaders) {
              gridHeaders[gridHeader]===null;
          }
        if (gridHeaders[header] === true || gridHeaders[header] === null) {
          gridHeaders[header] = false;
        } else {
          gridHeaders[header] = null;
        }
        upElement.removeClass("active");
        downElement.removeClass("active");
        if (gridHeaders[header] === false) {
          downElement.addClass("active");
        }
        refresh();
      });
    }

    function renderPager(lastResponse, length) {
      findPagers(root).remove();

      var count = Math.ceil(lastResponse.total / length);
      var prev = findPagerPrev(root);

      if(2 * length >= lastResponse.total) {
        for (var i = count; i >= 1; i--) {
          var li = pagerItemTemplate.clone().addClass((currentPage == i) ? 'active' : '').insertAfter(prev);
          li.find('a').click(assignPage(i)).html(i);
        }
      } else {
        var start;
        var end;
        if(currentPage <= 10) {
          start = 1;
          end = (count > 20) ? 20 : count;
        } else {
          start = currentPage - 9;
          end = (count > currentPage + 10) ? currentPage + 10 : count;
        }

        for (var i = end; i >= start; i--) {
          var li = pagerItemTemplate.clone().addClass((currentPage == i) ? 'active' : '').insertAfter(prev);
          li.find('a').click(assignPage(i)).html(i);
        }
      }
      findPageLength(root).val(length);
    }

    function assignPage(i) {
      return function () {
        if (monitor.value()) {
          return;
        }
        if (currentPage == i) {
          return;
        }
        currentPage = i;
        refresh();
      }
    }

    function renderData(lastResponse) {
      var table;
      if (grid[0].tagName == "TABLE") {
        table = grid.find("TBODY").empty();
      } else {
        table = grid.empty();
      }

      var dataLength = lastResponse.data.length;

      for (var i = 0; i < length; i++) {
        var row = rowTemplate.clone().appendTo(table);

        for (var gridHeader in gridHeaders) {
          if (gridHeaders.hasOwnProperty(gridHeader)) {
            var div = $('<div></div>').appendTo(row.find('[data-cell=' + gridHeader + ']'));

            if (i < dataLength) {
              var wholeValue = lastResponse.data[i];
              var propValue = wholeValue[gridHeader];
              var renderer = decideRenderer(gridHeader);
              div.html(renderer(propValue, wholeValue, self));
            }
          }
        }
      }
    }

    function decideRenderer(prop) {
      return (options.renderers && options.renderers[prop]) ? options.renderers[prop] : function (value, model) {
        return (value) ? '' + value : '';
      };
    }

    //
    // ui
    //

    function findPagers(root) {
      return root.find('[data-grid=pager] [data-grid=pager-item]');
    }

    function findPageLength(root) {
      return root.find('[data-grid=pager-length]');
    }

    function findFilter(root) {
      return root.find('[data-grid=search] input');
    }

    function findFilterButton(root) {
      return root.find('[data-grid=search] button');
    }

    function findRefresh(root) {
      return root.find('[data-grid=pager-refresh]');
    }

    function findPagerNext(root) {
      return root.find('[data-grid=pager-next]');
    }

    function findPagerPrev(root) {
      return root.find('[data-grid=pager-prev]');
    }

    function findHeaders(root) {
      return root.find('[data-grid-header]');
    }

    function findRowTemplate(root) {
      return root.find('[data-grid=row]');
    }

    function findPagerTemplate(root) {
      return root.find('[data-grid=pager-item]');
    }

    function findGrid(root) {
      return root.find('[data-grid=grid]');
    }

    //
    // API
    //
    this.refresh = function () {
      refresh();
    };
    this.setTitle = function (title) {
      // TODO
    };
    this.getMonitor = function () {
      return monitor;
    };

    return this;
  };
}(jQuery));

