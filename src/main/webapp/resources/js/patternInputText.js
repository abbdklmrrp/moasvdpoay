var input = document.getElementById('name')
    , value = input.value;

input.addEventListener('input', onInput);

function onInput(e) {
    var newValue = e.target.value;
    if (newValue.match(/[^a-zA-Z0-9\s\/]/)) {
        input.value = value;
        return;
    }
    value = newValue;
}
var inputCD = document.getElementById('newCategoryDesc-inpt')
    , valueCD = inputCD.value;

inputCD.addEventListener('input', onInputCD);

function onInputCD(e) {
    var newValue = e.target.value;
    if (newValue.match(/[^a-zA-Z0-9\s\\/]/)) {
        inputCD.value = valueCD;
        return;
    }
    valueCD = newValue;
}
var inputCN = document.getElementById('newCategory-inpt')
    , valueCN = inputCN.value;

inputCN.addEventListener('input', onInputCN);

function onInputCN(e) {
    var newValue = e.target.value;
    if (newValue.match(/[^a-zA-Z0-9\s]/)) {
        inputCN.value = valueCN;
        return;
    }
    valueCN = newValue;
}
var inputD = document.getElementById('description')
    , valueD = inputD.value;

inputD.addEventListener('input', onInputDesc);

function onInputDesc(e) {
    var newValue = e.target.value;
    if (newValue.match(/[^a-zA-Z0-9\s\\/]/)) {
        inputD.value = valueD;
        return;
    }
    valueD = newValue;
}
