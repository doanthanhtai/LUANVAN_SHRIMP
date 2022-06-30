var idShrimpUpdate;

$(document).ready(function () {
        const btnNewShrimp = document.getElementById('new-shrimp-btn')
        const btnCancel = document.getElementById('cancel-btn')
        const btnCancelUpdate = document.getElementById('cancel-update-btn')
        const btnConfirm = document.getElementById('create-zone-btn')
        const btnSubmitUpdate = document.getElementById('submit-update-btn')
        $('#shrimp-created-time').val(new Date().toISOString().substring(0, 10));

        // Nghe hành vi click vào button new zone để gọi show new zone form
        btnNewShrimp.addEventListener('click', showNewShrimpForm)

        //Nghe hành vi click vào button cancel
        btnCancel.addEventListener('click', function () {
            if (confirm("Hãy chắc rằng mọi thông tin lô tôm đã nhập sẽ bị hủy bỏ!")) {
                hideNewShrimpForm();
                Swal.fire({
                        icon: 'warning',
                        title: 'THÔNG BÁO',
                        text: 'Bạn đã hủy bỏ thao tác Tạo và thả lô tôm mới!',
                        showConfirmButton: false,
                        showCancelButton: false,
                        timer: 2000
                    }
                );
            } else {

            }
        })

        btnCancelUpdate.addEventListener('click', function () {
            if (confirm("Hãy chắc rằng mọi thông tin lô tôm đã nhập sẽ bị hủy bỏ!")) {
                hideUpdateShrimpForm();
                Swal.fire({
                        icon: 'warning',
                        title: 'THÔNG BÁO',
                        text: 'Bạn đã hủy bỏ thao tác Cập nhật thông tin lô tôm!',
                        showConfirmButton: false,
                        showCancelButton: false,
                        timer: 2000
                    }
                );
            } else {

            }
        })

        btnConfirm.addEventListener('click', insertZone);

        btnSubmitUpdate.addEventListener('click', updateShrimp)

    }
)

//Hàm hiển thị modal mua vé (thêm class open vào modal)
function showNewShrimpForm() {
    document.querySelector('.js-modal').classList.add('open')
    getListPond();
}

//Hàm ẩn modal
function hideNewShrimpForm() {
    document.querySelector('.js-modal').classList.remove('open')
}

function hideUpdateShrimpForm() {
    document.querySelector('.js-modal-update').classList.remove('open')
}

function showUpdateShrimpForm(id) {
    idShrimpUpdate = id;
    document.querySelector('.js-modal-update').classList.add('open')
    loadDataOld();
    document.querySelector('#shrimp-id').innerText = id;
}

function loadDataOld() {
    let shrimpDTO = {};
    shrimpDTO["id"] = Number(idShrimpUpdate);
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        type: "POST",
        contentType: "application/json",
        url: "/shrimps/get-shrimp-info",
        data: JSON.stringify(shrimpDTO),
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data) {
                document.querySelector('#shrimp-supplier-update').setAttribute('value', data.objects.supplier);
                document.querySelector('#shrimp-name-update').setAttribute('value', data.objects.name);
                document.querySelector('#shrimp-quantity-update').setAttribute('value', data.objects.quantity);
                document.querySelector('#shrimp-price-update').setAttribute('value', data.objects.price);
                document.querySelector('#shrimp-created-time-update').setAttribute('value', data.objects.createdTime);
                getListPond()
                return data.objects;
            }
        }
    });
}

function getListPond() {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        type: "POST",
        contentType: "application/json",
        url: "/ponds/get-ponds",
        cache: false,
        timeout: 600000,
        success: function (data) {
            let html = '';
            for (let item of data.objects) {
                html += '<option value="' + item.id + '">' + item.id + '-' + item.name + '</option>';
            }
            document.querySelector('#shrimp-pond-id-update').innerHTML = html;
            document.querySelector('#shrimp-pond-id-new').innerHTML = html;
        }
    });
}

function updateShrimp() {
    let shrimpDTO = {};
    shrimpDTO["id"] = idShrimpUpdate;
    shrimpDTO["name"] = $("#shrimp-name-update").val();
    shrimpDTO["supplier"] = $("#shrimp-supplier-update").val();
    shrimpDTO["quantity"] = parseInt($("#shrimp-quantity-update").val());
    shrimpDTO["price"] = parseFloat($("#shrimp-price-update").val());
    shrimpDTO["createdTime"] = $("#shrimp-created-time-update").val();
    shrimpDTO["pondId"] = parseInt($("#shrimp-pond-id-update").val());

    $("#cancel-btn").prop("disabled", true);
    $("#create-zone-btn").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/shrimps/update-shrimp",
        data: JSON.stringify(shrimpDTO),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data["msg"] === "update-success") {
                alert("Cập nhật thông tin lô tôm thành công!");
                location.reload();
            } else {
                alert("Vui lòng kiểm tra lại thông tin đã nhập!");
                $("#cancel-btn").prop("disabled", false);
                $("#create-zone-btn").prop("disabled", false);
            }
        },
        error: function (e) {
            alert("Thông tin vừa nhập không hợp lệ hoặc ao chọn thả đang có tôm!");
            $("#cancel-btn").prop("disabled", false);
            $("#create-zone-btn").prop("disabled", false);
        }
    });
}

function insertZone() {

    const shrimpDTO = {};
    shrimpDTO["name"] = $("#shrimp-name").val();
    shrimpDTO["supplier"] = $("#shrimp-supplier").val();
    shrimpDTO["quantity"] = parseInt($("#shrimp-quantity").val());
    shrimpDTO["price"] = parseFloat($("#shrimp-price").val());
    shrimpDTO["createdTime"] = $("#shrimp-created-time").val();
    shrimpDTO["pondId"] = parseInt($("#shrimp-pond-id-new").val());

    $("#cancel-btn").prop("disabled", true);
    $("#create-zone-btn").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/shrimps/insert-shrimp",
        data: JSON.stringify(shrimpDTO),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data["msg"] === "insert-success") {
                alert("Tạo lô tôm và thả tôm thành công!");
                hideNewShrimpForm();
                location.reload();
            } else {
                alert("Vui lòng kiểm tra lại thông tin đã nhập!");
                $("#cancel-btn").prop("disabled", false);
                $("#create-zone-btn").prop("disabled", false);
            }
        },
        error: function (e) {
            Swal.fire({
                title: 'Thông bóa',
                text: 'Thông tin vừa nhập không hợp lệ hoặc ao chọn thả đang có tôm!',
                icon: 'warning',
                showCancelButton: false,
                showConfirmButton: true
            })
            $("#cancel-btn").prop("disabled", false);
            $("#create-zone-btn").prop("disabled", false);
        }
    });
}

function showConfirmDeleteForm(id) {

    let msg = "<pre>Cảnh báo!\n" +
        "Bạn đang thực hiện thao tác Xóa lô tôm có mã : " + id + "\n" +
        "Hãy chắc chắn rằng bạn thật sự muốn xóa lô tôm.</pre>"

    Swal.fire({
        title: 'Xác nhận xóa lô tôm',
        html: msg,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy'
    }).then((result) => {
        if (result.isConfirmed) {
            let shrimpDTO = {};
            shrimpDTO["id"] = id;
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/shrimps/delete-shrimp",
                data: JSON.stringify(shrimpDTO),
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function (data) {
                    Swal.fire({
                        title: 'Thông bóa',
                        text: 'Xóa lô tôm thành công!',
                        icon: 'warning',
                        showCancelButton: false,
                        showConfirmButton: true
                    }).then((result) => {
                        location.reload();
                    })
                },
                error: function (e) {
                    alert("Xóa tôm thất bại!")
                }
            });
        }
    })
}

function showConfirmHarvestForm(id) {
    let msg = "<pre>Cảnh báo!\n" +
        "Bạn đang thực hiện thao tác Thu hoạch lô tôm: " + id +
        "\nSau khi thu hoạch thành công,bạn sẽ không còn thao " +
        "\ntác được bất kì thao tác nào liên quan đến quy" +
        "\ntrình nuôi của lô tôm nữa.\n" +
        "\nMọi dữ liệu của ao nuôi liên quan trong thời gian" +
        "\nnuôisẽ được xóa hết.Thay vào đó bạn có thể xem báo" +
        "\ncáo chi tiết và mã QR chứa đường dẫn báo cáo ngắn " +
        "\nvề lịch sử nuôi của lô tôm ở tab Đã thu." +
        "\nHãy chắc chắn rằng bạn thật sự muốn thu hoạch.</pre>"
    Swal.fire({
        title: 'Xác nhận thu hoạch lô tôm',
        html: msg,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy'
    }).then((result) => {
        if (result.isConfirmed) {
            let shrimpDTO = {};
            shrimpDTO["id"] = id;
            shrimpDTO["harvestTime"] = new Date().toISOString().substring(0, 10);
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/shrimps/harvest-shrimp",
                data: JSON.stringify(shrimpDTO),
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function (data) {
                    alert('Thu hoạch tôm thành công!')
                    location.reload();
                },
                error: function (e) {
                    alert("Thu hoạch tôm thất bại!")
                    $("#cancel-btn").prop("disabled", false);
                    $("#create-zone-btn").prop("disabled", false);
                }
            });
        }
    })
}