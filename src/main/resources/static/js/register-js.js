function sendOtp() {
    hide();
    const email = document.getElementById("email").value;
    const name = document.getElementById("name").value;
    const password = document.getElementById("password").value;
    const captchaId = document.getElementById("captchaId").value;
    const captcha = document.getElementById("captcha").value;

    if (!email || !name || !password || !captchaId) {
        showError("All feilds are required");
        return;
    }

    fetch("/register/send-otp", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email , captchaId , captcha })
    })
    .then(res => {
        if (!res.ok) {
            loadCaptcha();
            return res.text().then(msg => { throw new Error(msg); });
        }

        hide();
        document.getElementById("otpSection").classList.remove("d-none");
        showSuccess("OTP sent to your email");
    })
    .catch(err => {
        showError(err.message || "Failed to send OTP");
    });
}
function showSuccess(message) {
    const box = document.getElementById("successBox");
    document.getElementById("success").innerText = message;
    box.classList.remove("d-none");
}

function verifyOtpAndSubmit() {
    hide();
    const otp = document.getElementById("otp").value;
    const email = document.getElementById("email").value;

    if (!otp || !email) {
        showError("Email or OTP missing")
        return;
    }

    fetch("/register/verify-otp", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: email,
            otp: otp
        })
    })
    .then(res => {
        if (res.ok) {
            showSuccess("Registrartion sucessful");
            document.forms[0].action = "/register/perform_register";
            document.forms[0].method = "post";
            document.forms[0].submit();
        } else {
            return res.text().then(msg => {
                showError(msg);
                throw new Error(msg);
            });
        }
    })
    .catch(err => {
    });
}
function showError(message) {
    const box = document.getElementById("errorBox");
    const span = document.getElementById("error");

    span.innerText = message;
    box.classList.remove("d-none");
}

function hide() {
    const box = document.getElementById("errorBox");
    box.classList.add("d-none");
    const abox = document.getElementById("successBox");
    abox.classList.add("d-none");
}
function loadCaptcha() {
    fetch("/captcha")
        .then(res => res.json())
        .then(data => {
            document.getElementById("captchaImg").src = data.imageUrl;
            document.getElementById("captchaId").value = data.captchaId;
        });
}
loadCaptcha();