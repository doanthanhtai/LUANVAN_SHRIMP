var enterHistoryUpdateId;

$(document).ready(function () {
    const btnConfirmUpdate = document.getElementById('submit-update-btn')
    const btnCancelUpdate = document.getElementById('cancel-update-btn')

    btnCancelUpdate.addEventListener('click', function () {
        if (confirm("Hãy chắc rằng mọi thông tin lịch sử đã nhập sẽ bị hủy bỏ!")) {
            hideUpdateEnterHistoryForm();
            Swal.fire({
                    icon: 'warning',
                    title: 'THÔNG BÁO',
                    text: 'Bạn đã hủy bỏ thao tác Cập nhật lịch sử nhập hàng!',
                    showConfirmButton: false,
                    showCancelButton: false,
                    timer: 2000
                }
            );
        }
    })

    btnConfirmUpdate.addEventListener('click', updateEnterHistory);

})

function updateEnterHistory() {
    let enterHistoryDTO = {};
    enterHistoryDTO["id"] = enterHistoryUpdateId;
    enterHistoryDTO["quantity"] = $("#enter-product-quantity").val();
    enterHistoryDTO["updatedTime"] = new Date().toISOString().substring(0, 10);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/enter-histories/update-enter-history",
        data: JSON.stringify(enterHistoryDTO),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            Swal.fire({
                title: 'Thông bóa',
                text: 'Cập nhật lịch sử nhập hàng thành công!',
                icon: 'warning',
                showCancelButton: false,
                showConfirmButton: true
            }).then((result) => {
                location.reload();
            });
        },
        error: function (e) {
            alert("Thông tin vừa nhập không hợp lệ!");
        }
    });
}

function hideUpdateEnterHistoryForm() {
    document.querySelector('.js-modal-enter-product').classList.remove('open')
}

function showUpdateEnterHistoryForm(enterHistoryId) {
    enterHistoryUpdateId = enterHistoryId;
    document.querySelector('.js-modal-enter-product').classList.add('open')
    loadDataOld();
}

function loadDataOld() {
    let idEnterHistory = "Mã lịch sử : " + enterHistoryUpdateId;
    let enterHistoryDTO = {};
    enterHistoryDTO["id"] = Number(enterHistoryUpdateId);
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        type: "POST",
        contentType: "application/json",
        url: "/enter-histories/get-enter-history-info",
        data: JSON.stringify(enterHistoryDTO),
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data) {
                document.querySelector('#enter-history-info').innerHTML = idEnterHistory;
                document.querySelector('#enter-product-quantity').setAttribute('value', Number(data.objects.quantity));
                getProductInfo(data.objects.productQuantityDTO.productId);
            }
        }
    });

}

function getProductInfo(productId) {
    let productDTO = {}
    productDTO["id"] = productId;
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        type: "POST",
        contentType: "application/json",
        url: "/products/get-product-info",
        data: JSON.stringify(productDTO),
        cache: false,
        timeout: 600000,
        success: function (data) {
            let productInfo = '<p>' + data.objects.id + ' - ' + data.objects.name + '</p>';
            document.querySelector('#product-info').innerHTML = productInfo;
            document.querySelector('.product-measure').innerHTML = 'Số lượng(' + data.objects.measure + ') :';
        }
    });
}

function onDelete(enterHistoryId) {
    let msg = "<pre>Cảnh báo!\n" +
        "Bạn đang thực hiện thao tác Lịch sử nhập hàng có mã : " + enterHistoryId + "\n" +
        "Hãy chắc chắn rằng bạn thật sự muốn xóa Lịch sử nhập hàng.</pre>"

    Swal.fire({
        title: 'Xác nhận xóa lịch sử nhập hàng',
        html: msg,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy'
    }).then((result) => {
        if (result.isConfirmed) {
            let enterHistoryDTO = {};
            enterHistoryDTO["id"] = enterHistoryId;
            enterHistoryDTO["updatedTime"] = new Date().toISOString().substring(0, 10);
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/enter-histories/delete-enter-history",
                data: JSON.stringify(enterHistoryDTO),
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function (data) {
                    Swal.fire({
                        title: 'Thông bóa',
                        text: 'Xóa lịch sử nhập hàng thành công!',
                        icon: 'warning',
                        showCancelButton: false,
                        showConfirmButton: true
                    }).then((result) => {
                        location.reload();
                    })
                },
                error: function (e) {
                    alert("Xóa lịch sử nhập hàng thất bại!")
                }
            });
        }
    })
}