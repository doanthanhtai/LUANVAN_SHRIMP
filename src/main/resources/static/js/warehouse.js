var idWareHouseUpdate;
var idWareHouseEnter;
var listProduct = {};

//Hàm hiển thị modal mua vé (thêm class open vào modal)
function showNewWareHouseForm() {
    document.querySelector('.js-modal').classList.add('open')
    getListZone();
}

function showUpdateWareHouseForm(wareHouseId) {
    idWareHouseUpdate = wareHouseId;
    document.querySelector('.js-modal-update').classList.add('open')
    getListZone();
    loadDataOld();
}

//Hàm ẩn modal
function hideNewWareHouseForm() {
    document.querySelector('.js-modal').classList.remove('open')
}

function hideUpdateWareHouseForm() {
    document.querySelector('.js-modal-update').classList.remove('open')
}

function hideEnterForm(){
    document.querySelector('.js-modal-enter-product').classList.remove('open')
}

function showEnterProductForm(wareHouseId) {
    document.querySelector('.js-modal-enter-product').classList.add('open')
    document.querySelector('#ware-house-info').innerHTML = 'Mã kho nhập : ' + wareHouseId;
    idWareHouseEnter = wareHouseId;
    getListProduct();
}

function getListProduct() {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        type: "POST",
        contentType: "application/json",
        url: "/products/get-products",
        cache: false,
        timeout: 600000,
        success: function (data) {
            listProduct = data.objects;
            let html = '';
            for (let item of data.objects) {
                html += '<option value="' + item.id + '">' + item.id + '-' + item.name + '</option>';
            }
            document.querySelector('#enter-product-id').innerHTML = html;
            document.querySelector('.product-measure').innerHTML = 'Số lượng(' + data.objects[0].measure + ') :';
        }
    });
}

$(document).ready(function () {
        const btnNewWareHouse = document.getElementById('new-warehouse-btn')
        const btnCancelCreate = document.getElementById('cancel-create-btn')
        const btnConfirmCreate = document.getElementById('submit-create-btn')
        const btnConfirmUpdate = document.getElementById('submit-update-btn')
        const btnCancelUpdate = document.getElementById('cancel-update-btn')
        const dropdownProduct = document.getElementById('enter-product-id');
        const btnCancelEnter = document.getElementById('cancel-enter-btn')
        const btnSubmitEnter = document.getElementById('submit-enter-btn');

        // Nghe hành vi click vào button new zone để gọi show new form
        btnNewWareHouse.addEventListener('click', showNewWareHouseForm)

        //Nghe hành vi click vào button cancel
        btnCancelCreate.addEventListener('click', function () {
            if (confirm("Hãy chắc rằng mọi thông tin kho đã nhập sẽ bị hủy bỏ!")) {
                hideNewWareHouseForm();
                Swal.fire({
                        icon: 'warning',
                        title: 'THÔNG BÁO',
                        text: 'Bạn đã hủy bỏ thao tác Tạo kho mới!',
                        showConfirmButton: false,
                        showCancelButton: false,
                        timer: 2000
                    }
                );
            }
        })

        btnCancelUpdate.addEventListener('click', function () {
            if (confirm("Hãy chắc rằng mọi thông tin kho đã nhập sẽ bị hủy bỏ!")) {
                hideUpdateWareHouseForm();
                Swal.fire({
                        icon: 'warning',
                        title: 'THÔNG BÁO',
                        text: 'Bạn đã hủy bỏ thao tác Cập nhật thông tin kho!',
                        showConfirmButton: false,
                        showCancelButton: false,
                        timer: 2000
                    }
                );
            }
        })

        btnCancelEnter.addEventListener('click', function () {
            if (confirm("Hãy chắc rằng mọi thông tin nhập kho đã nhập sẽ bị hủy bỏ!")) {
                hideEnterForm();
                Swal.fire({
                        icon: 'warning',
                        title: 'THÔNG BÁO',
                        text: 'Bạn đã hủy bỏ thao tác Nhập hàng vào kho!',
                        showConfirmButton: false,
                        showCancelButton: false,
                        timer: 2000
                    }
                );
            }
        });

        btnConfirmCreate.addEventListener('click', insertWareHouse);
        btnConfirmUpdate.addEventListener('click', updateWareHouse);
        dropdownProduct.addEventListener('change', productChangeSelect);
        btnSubmitEnter.addEventListener('click', submitEnter);
    }
)

function submitEnter() {
    let enterHistoryDTO = {};
    let productQuantityDTO = {};
    productQuantityDTO["wareHouseId"] = Number(idWareHouseEnter);
    productQuantityDTO["productId"] = Number($("#enter-product-id").val());
    enterHistoryDTO["createdTime"] = new Date().toISOString().substring(0, 10);
    enterHistoryDTO["updatedTime"] = new Date().toISOString().substring(0, 10);
    enterHistoryDTO["quantity"] = parseFloat($("#enter-product-quantity").val());
    enterHistoryDTO["productQuantityDTO"] = productQuantityDTO;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/enterHistory/enter-product",
        data: JSON.stringify(enterHistoryDTO),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data["msg"] === "insert-success") {
                alert("Nhập hàng thành công!");
                location.reload();
            } else {
                alert("Vui lòng kiểm tra lại thông tin đã nhập!");
            }
        },
        error: function (e) {
            alert("Thông tin vừa nhập không hợp lệ!");
        }
    });
}

function productChangeSelect() {
    let idProduct = Number($('#enter-product-id').val());
    let product = listProduct.filter(function (item) {
        return item.id === idProduct;
    });
    document.querySelector('.product-measure').innerHTML = 'Số lượng(' + product[0].measure + ') :';
}

function updateWareHouse() {
    let wareHouseDTO = {};
    wareHouseDTO["id"] = idWareHouseUpdate;
    wareHouseDTO["name"] = $("#ware-house-name-update").val();
    wareHouseDTO["zoneId"] = $("#ware-house-zone-id-update").val();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/warehouse/update-warehouse",
        data: JSON.stringify(wareHouseDTO),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data["msg"] === "update-success") {
                alert("Cập nhật thông tin kho thành công!");
                location.reload();
            } else {
                alert("Vui lòng kiểm tra lại thông tin đã nhập!");
            }
        },
        error: function (e) {
            alert("Thông tin vừa nhập không hợp lệ!");
        }
    });
}

function showConfirmDeleteForm(wareHouseId) {
    let msg = "<pre>Cảnh báo!\n" +
        "Bạn đang thực hiện thao tác Kho có mã : " + wareHouseId + "\n" +
        "Hãy chắc chắn rằng bạn thật sự muốn xóa kho.</pre>"

    Swal.fire({
        title: 'Xác nhận xóa kho',
        html: msg,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy'
    }).then((result) => {
        if (result.isConfirmed) {
            let wareHouseDTO = {};
            wareHouseDTO["id"] = wareHouseId;
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/warehouse/delete-warehouse",
                data: JSON.stringify(wareHouseDTO),
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function (data) {
                    if (data.msg === "inventory") {
                        Swal.fire({
                            title: 'Thông bóa',
                            text: 'Xóa kho thất bại.Trong kho hiện tại có sản phẩm tồn!',
                            icon: 'warning',
                            showCancelButton: false,
                            showConfirmButton: true
                        });
                        return;
                    }
                    Swal.fire({
                        title: 'Thông bóa',
                        text: 'Xóa kho thành công!',
                        icon: 'warning',
                        showCancelButton: false,
                        showConfirmButton: true
                    }).then((result) => {
                        location.reload();
                    })
                },
                error: function (e) {
                    alert("Xóa kho thất bại!")
                }
            });
        }
    })
}

function insertWareHouse() {
    let wareHouseDTO = {};
    wareHouseDTO["name"] = $("#ware-house-name").val();
    wareHouseDTO["zoneId"] = Number($("#ware-house-zone-id").val());

    $("#cancel-create-btn").prop("disabled", true);
    $("#submit-create-btn").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/warehouse/insert-warehouse",
        data: JSON.stringify(wareHouseDTO),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data["msg"] === "insert-success") {
                alert("Tạo kho mới thành công!");
                hideNewWareHouseForm();
                location.reload();
            } else {
                alert("Vui lòng kiểm tra lại thông tin đã nhập!");
                $("#cancel-create-btn").prop("disabled", true);
                $("#submit-create-btn").prop("disabled", true);
            }
        },
        error: function (e) {
            alert("Thông tin vừa nhập không hợp lệ!");
            $("#cancel-create-btn").prop("disabled", true);
            $("#submit-create-btn").prop("disabled", true);
        }
    });
}

function getListZone() {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/zones/get-zones",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            let html = '';
            for (let item of data.objects) {
                html += '<option value="' + item.id + '">' + item.id + '-' + item.name + '</option>';
            }
            document.querySelector('#ware-house-zone-id').innerHTML = html;
            document.querySelector('#ware-house-zone-id-update').innerHTML = html;
        }
    });
}

function loadDataOld() {
    let wareHouseDTO = {};
    wareHouseDTO["id"] = Number(idWareHouseUpdate);
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        type: "POST",
        contentType: "application/json",
        url: "/warehouse/get-warehouse-info",
        data: JSON.stringify(wareHouseDTO),
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data) {
                document.querySelector('#ware-house-name-update').setAttribute('value', data.objects.name);
                document.querySelector('#ware-house-zone-id-update').setAttribute('value', Number(data.objects.zoneId));
            }
        }
    });
}
