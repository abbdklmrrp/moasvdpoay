/**
 * @author Anna Rysakova
 */
(function ($) {
    $.fn.currencyInput = function () {
        this.each(function () {
            var wrapper = $("<div class='currency-input' />");
            $(this).wrap(wrapper);
            $(this).before("<span class='currency-symbol'>$</span>");
            $(this).change(function () {
                var min = 0.00;
                var max = 9999999.99;
                var value = this.valueAsNumber;
                if (value < min)
                    value = min;
                if (isNaN(value))
                    value = min;
                if (value.length = 0)
                    value = min;
                else if (value > max)
                    value = max;
                $(this).val(value.toFixed(2));
            });
        });
    };
})(jQuery);

$(document).ready(function () {
    $('input.currency').currencyInput();
});

document.getElementById('basePrice').onkeypress = function (e) {
    if (this.value.indexOf(".") != '-1' || this.value.indexOf(",") != '-1') {
        return !(/[.,А-Яа-яA-Za-z-+]/.test(String.fromCharCode(e.charCode)));
    }
};;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;