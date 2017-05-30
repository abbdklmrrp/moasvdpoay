/**
 * @author Anna Rysakova
 */
function handleChange(input) {
    if (input.value < 0) input.value = 1;
    if (input.value > 365) input.value = 365;
}
