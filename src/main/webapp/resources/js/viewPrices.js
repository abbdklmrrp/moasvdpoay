function viewPrices(id){
    $('#myTable').empty();
    $('#myPager').empty();
    $('#myTable').pageMe({
        pagerSelector: '#myPager',
        showPrevNext: true,
        hidePageNumbers: false,
        perPage: 5
    }, 'getPrices',id);
    show('block');

}
