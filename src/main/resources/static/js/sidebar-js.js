document.addEventListener("DOMContentLoaded", function () {

    /* ===============================
       MOBILE SIDEBAR TOGGLE
    ================================ */

    const sidebar = document.querySelector(".sidebar-wrapper");
    const toggleBtn = document.getElementById("sidebarToggle");

    if (toggleBtn && sidebar) {
        toggleBtn.addEventListener("click", function () {
            sidebar.classList.toggle("show");
        });
    }

    /* ===============================
       ACTIVE LINK HANDLING (UX ONLY)
       Thymeleaf still controls real state
    ================================ */

    const sidebarLinks = document.querySelectorAll(".sidebar .nav-link");

    sidebarLinks.forEach(link => {
        link.addEventListener("click", function () {
            sidebarLinks.forEach(l => l.classList.remove("active"));
            this.classList.add("active");

            // Auto-close sidebar on mobile after click
            if (window.innerWidth < 768 && sidebar) {
                sidebar.classList.remove("show");
            }
        });
    });

});
