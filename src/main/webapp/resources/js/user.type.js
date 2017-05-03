/**
 * Created by Petro on 26.04.2017.
 */
function Selected(a) {
    var label = a.value;
    if (label == "LEGAL") {
        var d=document.getElementById("Block1");
        d.style.display = 'block';
    } else {
        document.getElementById("Block1").style.display = 'none';
    }
}