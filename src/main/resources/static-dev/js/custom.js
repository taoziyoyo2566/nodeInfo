document.getElementById('fetchInfoButton').addEventListener('click', fetchNodeInfo);

function fetchNodeInfo() {
    const instanceName = document.getElementById("instanceName").value;
    const errorMessage = document.getElementById("error-message");
    const nodeInfoContainer = document.getElementById("node-info-container");

    // Clear previous results and error messages
    errorMessage.textContent = "";
    nodeInfoContainer.innerHTML = "";

    // Validate input
    const regex = /^[a-zA-Z0-9_]{20}$/;
    if (!regex.test(instanceName)) {
        errorMessage.textContent = "Instance Name must be exactly 20 alphanumeric characters.";
        return;
    }

    // Fetch data from API
    $.ajax({
        url: '/api/v1/getNodeInfo',
        method: 'GET',
        data: { instanceName: instanceName },
        success: function(data) {
            if (data.returnFlg !== 0) {
                errorMessage.textContent = data.returnMSG;
                return;
            }
            renderNodeInfo(data.nodeInfo);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            errorMessage.textContent = "An error occurred while fetching data: " + textStatus + ", " + errorThrown;
        }
    });
}

function renderNodeInfo(nodeInfo) {
    const nodeInfoContainer = document.getElementById("node-info-container");

    const nodeInfoHtml = `
        <div class="node-info">
            <p>ID: <span>${nodeInfo.URL_ID}</span></p>
            <p>URL Info: <span class="url-info">${truncateUrl(nodeInfo.URL_IPV4)}</span>
                <button class="toggle-url-info" onclick="toggleUrlInfo(this)">Show More</button>
            </p>
            <div id="url-info-detail-${nodeInfo.URL_ID}" class="url-info-detail" style="display: none;">
                ${nodeInfo.URL_IPV4}
            </div>
            <button onclick="copyToClipboard('${nodeInfo.URL_IPV4}')"><i class="fas fa-copy"></i> Copy URL Info</button>
            <button id="toggleQRCodeButton-${nodeInfo.URL_ID}" onclick="toggleQRCode('${nodeInfo.URL_IPV4}', '${nodeInfo.URL_ID}')"><i class="fas fa-qrcode"></i> Generate QR Code</button>
            <div id="qrcode-${nodeInfo.URL_ID}" class="qrcode" style="display: none;"></div>
            <p>Create Date: <span>${nodeInfo.CREATE_DATETIME}</span></p>
            <p>Create Date: <span>${nodeInfo.EXPIRE_DATETIME}</span></p>
            
            <p>Region: <span>${nodeInfo.REGION}</span></p>
        </div>
        <hr/>
    `;
    nodeInfoContainer.innerHTML = nodeInfoHtml;
}

function truncateUrl(url) {
    const maxLength = 30;
    if (url.length > maxLength) {
        return url.substring(0, maxLength) + '...';
    }
    return url;
}

function toggleUrlInfo(button) {
    // 向上查找直到找到包含 .url-info-detail 的容器
    const detailContainer = button.closest('.node-info').querySelector('.url-info-detail');
    if (!detailContainer) {
        console.error('Error: .url-info-detail element not found.');
        return;
    }

    // 移除对 .url-info-detail-content 的引用

    if (detailContainer.style.display === 'none') {
        detailContainer.style.display = 'block';
        button.textContent = 'Show Less';
    } else {
        detailContainer.style.display = 'none';
        button.textContent = 'Show More';
    }
}


//
// function renderNodeInfo(nodeInfo) {
//     const nodeInfoContainer = document.getElementById("node-info-container");
//     const nodeInfoHtml = `
//         <div class="node-info">
//             <p>ID: <span>${nodeInfo.URL_ID}</span></p>
//             <p>URL Info: <span>${nodeInfo.URL_IPV4}</span></p>
//             <button onclick="copyToClipboard('${nodeInfo.URL_IPV4}')"><i class="fas fa-copy"></i> Copy URL Info</button>
//             <button id="toggleQRCodeButton-${nodeInfo.URL_ID}" onclick="toggleQRCode('${nodeInfo.URL_IPV4}', '${nodeInfo.URL_ID}')"><i class="fas fa-qrcode"></i> Generate QR Code</button>
//             <div id="qrcode-${nodeInfo.URL_ID}" class="qrcode" style="display: none;"></div>
//             <p>Create Time: <span>${nodeInfo.CREATE_DATETIME}</span></p>
//             <p>Region: <span>${nodeInfo.REGION}</span></p>
//         </div>
//         <hr/>
//     `;
//     nodeInfoContainer.innerHTML = nodeInfoHtml;
// }

function copyToClipboard(text) {
    if (navigator.clipboard) {
        navigator.clipboard.writeText(text).then(() => {
            showCopyMessage();
        }).catch(err => {
            console.error('Failed to copy text: ', err);
        });
    } else {
        // Fallback for browsers that do not support navigator.clipboard
        const tempInput = document.createElement("textarea");
        tempInput.value = text;
        document.body.appendChild(tempInput);
        tempInput.select();
        document.execCommand("copy");
        document.body.removeChild(tempInput);
        showCopyMessage();
    }
}

function showCopyMessage() {
    const messageElement = document.getElementById('copy-message');
    messageElement.classList.add('show');
    setTimeout(() => {
        messageElement.classList.remove('show');
    }, 3000);
}

function toggleQRCode(text, id) {
    const qrcodeDiv = document.getElementById('qrcode-' + id);
    const toggleButton = document.getElementById('toggleQRCodeButton-' + id);

    if (qrcodeDiv.style.display === 'none') {
        qrcodeDiv.style.display = 'block';
        toggleButton.textContent = 'hide qrcode';
        new QRCode(qrcodeDiv, {
            text: text,
            width: 128,
            height: 128
        });
    } else {
        qrcodeDiv.style.display = 'none';
        toggleButton.textContent = 'show qrcode';
        qrcodeDiv.innerHTML = ""; // Clear previous QR code
    }
}
