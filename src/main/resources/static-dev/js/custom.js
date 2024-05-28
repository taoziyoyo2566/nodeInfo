document.addEventListener('DOMContentLoaded', (event) => {
    const errorMessage = document.getElementById('error-message');
    const nodeInfoContainer = document.getElementById('node-info-container');

    // 绑定按钮事件
    document.getElementById('fetchInfoButton').addEventListener('click', () => {
        fetchNodeInfo(errorMessage, nodeInfoContainer);
    });
});
function fetchNodeInfo() {
    const instanceName = document.getElementById("instanceName").value;
    const errorMessage = document.getElementById("error-message");
    const nodeInfoContainer = document.getElementById("node-info-container");

    console.log("nodeInfoContainer:", nodeInfoContainer);

    // Clear previous results and error messages
    // errorMessage.textContent = "";
    // if (nodeInfoContainer) {
    //     nodeInfoContainer.innerHTML = "";
    // } else {
    //     console.error("nodeInfoContainer is null");
    //     return;
    // }

    // Validate input
    const regex = /^[a-zA-Z0-9_]{20}$/;
    if (!regex.test(instanceName)) {
        errorMessage.textContent = "Instance Name must be exactly 20 alphanumeric characters.";
        return;
    }

    // Fetch data from API
    $.ajax({
        url: `http://156.238.240.179:8080/api/v1/getNodeInfo`,
        method: 'GET',
        data: { instanceName: instanceName.substring(13,20) },
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
