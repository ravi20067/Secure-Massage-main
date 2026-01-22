document.addEventListener("DOMContentLoaded", function () {

    /* ===============================
       DASHBOARD CARD NAVIGATION
       (Overview cards → redirect)
    ================================ */

    const dashboardCards = document.querySelectorAll("[data-dashboard-link]");

    dashboardCards.forEach(card => {
        card.addEventListener("click", function () {
            const targetUrl = this.getAttribute("data-dashboard-link");
            if (targetUrl) {
                window.location.href = targetUrl;
            }
        });
    });

    /* ===============================
       CONTROL ROW ACTIONS
       (Buttons like New Message, Create Group, Upload)
    ================================ */

    const actionButtons = document.querySelectorAll("[data-action]");

    actionButtons.forEach(button => {
        button.addEventListener("click", function () {
            const action = this.getAttribute("data-action");

            switch (action) {
                case "new-message":
                    console.log("New Message action");
                    break;

                case "create-group":
                    console.log("Create Group action");
                    break;

                case "upload-file":
                    console.log("Upload File action");
                    break;

                default:
                    console.log("Unknown action:", action);
            }
        });
    });

    /* ===============================
       RESPONSIVE HELPER
       (Close sidebar on resize if needed)
    ================================ */

    window.addEventListener("resize", function () {
        const sidebar = document.querySelector(".sidebar-wrapper");
        if (sidebar && window.innerWidth >= 768) {
            sidebar.classList.remove("show");
        }
    });

});
