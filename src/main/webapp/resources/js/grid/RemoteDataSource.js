var RemoteDataSource = function (options) {
  this.data = function (request, callback) {
    $.ajax({
      cache: false,
      url: (typeof options.url === 'string') ? options.url : options.url(request),
      dataType: 'json',
      contentType: 'application/json',
      type: "GET",
      data: (typeof options.url === 'string') ? request : null,
      success: function (data, textStatus) {
        callback(true, data);
      },
      error: function () {
        callback(false, {total: 0, data: []});
      }
    });
  }
};
