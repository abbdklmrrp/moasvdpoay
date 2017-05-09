var ElementListener = function(element) {
    this.start = function(request) {
        element.css({display: 'block'});
    };
    this.stop = function(success, response) {
        element.css({display: 'none'});
    }
};
