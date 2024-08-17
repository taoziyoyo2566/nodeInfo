function fetchNodeInfo() {
    const instanceName = document.getElementById("instanceName").value;
    const errorMessage = document.getElementById("error-message");
    const nodeInfoContainer = document.getElementById("node-info-container");

    console.log("nodeInfoContainer:", nodeInfoContainer);

    // Clear previous results and error messages
    errorMessage.textContent = "";
    if (nodeInfoContainer) {
        nodeInfoContainer.innerHTML = "";
    } else {
        console.error("nodeInfoContainer is null");
        return;
    }

    // Validate input
    const regex = /^[a-zA-Z0-9_]{22}$/;
    if (!regex.test(instanceName)) {
        errorMessage.textContent = "Instance Name must be exactly 22 alphanumeric characters.";
        return;
    }

    // Fetch data from API
    $.ajax({
        url: `/api/v1/getNodeInfo`,
        method: 'GET',
        data: { instanceName: instanceName },
        success: function(data) {
            console.log(data); // Log the returned data to console
            if (data.returnFlg !== 0) {
                errorMessage.textContent = data.returnMSG;
                return;
            }
            const nodeInfo = data.nodeInfo;
            if (!nodeInfo) {
                errorMessage.textContent = "No node information found.";
                return;
            }
            // Use Thymeleaf's fragment to render nodeInfo
            nodeInfoContainer.innerHTML = `
                <div th:replace="fragments/node-info :: node-info" th:with="nodeInfo=${nodeInfo}"></div>
            `;
        },
        error: function(jqXHR, textStatus, errorThrown) {
            errorMessage.textContent = "An error occurred while fetching data: " + errorThrown;
        }
    });
}

function copyToClipboard(text) {
    const input = document.createElement('textarea');
    input.value = text;
    document.body.appendChild(input);
    input.select();
    document.execCommand('copy');
    document.body.removeChild(input);
    alert('URL info copied to clipboard');
}

function generateQRCode(text, id) {
    const qrcodeDiv = document.getElementById('qrcode-' + id);
    qrcodeDiv.innerHTML = ""; // Clear previous QR code
    new QRCode(qrcodeDiv, {
        text: text,
        width: 128,
        height: 128
    });
}
