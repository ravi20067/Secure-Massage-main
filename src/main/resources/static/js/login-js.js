function loadCaptcha() {
    fetch("/captcha")
        .then(res => res.json())
        .then(data => {
            document.getElementById("captchaImg").src = data.imageUrl;
            document.getElementById("captchaId").value = data.captchaId;
        });
}
loadCaptcha();