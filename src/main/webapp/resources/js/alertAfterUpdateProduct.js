window.onload = function () {
    if (document.getElementById('resultMessage').innerHTML.trim() != '') {
        sweetAlert({
            title: document.getElementById('resultMessage').innerHTML,
            type: "success"
        });
    }
};
setTimeout(function () {
    document.getElementById("message").style.display = "none";
}, 4000);