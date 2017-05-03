function Selected(a) {
    var label = a.value;
    if (label == "Service") {
        var d = document.getElementById("Block1");
        d.style.display = 'block';
    } else {
        document.getElementById("Block1").style.display = 'none';
    }
}