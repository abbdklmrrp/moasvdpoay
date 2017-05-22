/**
 * Created by Anna Rysakova on 20.05.2017.
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
