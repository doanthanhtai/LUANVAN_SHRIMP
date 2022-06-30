var idZoneUpdate;

//Hàm hiển thị modal
function showNewZoneForm() {
    document.querySelector('.js-modal').classList.add('open')
    $('#zone-create-time').val(new Date().toISOString().substring(0, 10))
}

//Hàm hiển thị modal update
function showUpdateZoneForm(id) {
    idZoneUpdate = id;
    document.querySelector('.js-modal-update').classList.add('open')
    loadDataOld();
    document.querySelector('#zone-id').innerText = id;
}

function loadDataOld() {
    let zoneDTO = {};
    zoneDTO["id"] = Number(idZoneUpdate);
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        type: "POST",
        contentType: "application/json",
        url: "/zones/get-zone-info",
        data: JSON.stringify(zoneDTO),
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data) {
                document.querySelector('#zone-name-update').setAttribute('value', data.objects.name);
                document.querySelector('#zone-area-update').setAttribute('value', data.objects.area);
                document.querySelector('#zone-create-time-update').setAttribute('value', data.objects.createdTime);
                document.querySelector('#zone-address-update').setAttribute('value', data.objects.address);
            }
        }
    });
}

//Hàm ẩn modal
function hideNewZoneForm() {
    document.querySelector('.js-modal').classList.remove('open')
}

//Hàm ẩn modal
function hideUpdateZoneForm() {
    document.querySelector('.js-modal-update').classList.remove('open')
}

$(document).ready(function () {
        const btnNewZone = document.getElementById('new-zone-btn')
        const btnCancelInsert = document.getElementById('cancel-insert-btn')
        const btnCancelUpdate = document.getElementById('cancel-update-btn')
        const btnConfirmInsert = document.getElementById('create-zone-btn')
        const btnConfirmUpdate = document.getElementById('update-zone-btn')

        // Nghe hành vi click vào button new zone để gọi show new zone form
        btnNewZone.addEventListener('click', showNewZoneForm)

        //Nghe hành vi click vào button cancel
        btnCancelInsert.addEventListener('click', function () {
            if (confirm("Hãy chắc rằng mọi thông tin khu nuôi đã nhập sẽ bị hủy bỏ!")) {
                hideNewZoneForm();
                Swal.fire({
                        icon: 'warning',
                        title: 'THÔNG BÁO',
                        text: 'Bạn đã hủy bỏ thao tác Tạo khu nuôi mới!',
                        showConfirmButton: false,
                        showCancelButton: false,
                        timer: 2000
                    }
                );
            } else {

            }
        })

        //Nghe hành vi click vào button cancel
        btnCancelUpdate.addEventListener('click', function () {
            if (confirm("Hãy chắc rằng mọi thông tin khu nuôi đã nhập sẽ bị hủy bỏ!")) {
                hideUpdateZoneForm();
                Swal.fire({
                        icon: 'warning',
                        title: 'THÔNG BÁO',
                        text: 'Bạn đã hủy bỏ thao tác Cập nhật thông tin khu nuôi!',
                        showConfirmButton: false,
                        showCancelButton: false,
                        timer: 2000
                    }
                );
            } else {

            }
        })

        btnConfirmInsert.addEventListener('click', insertZone);

        btnConfirmUpdate.addEventListener('click', updateZone);
    }
)

function onDelete(id) {
    let msg = "<pre>Cảnh báo!\n" +
        "Bạn đang thực hiện thao tác Khu nuôi có mã : " + id + "\n" +
        "Hãy chắc chắn rằng bạn thật sự muốn xóa khu nuôi.</pre>"

    Swal.fire({
        title: 'Xác nhận xóa khu nuôi',
        html: msg,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy'
    }).then((result) => {
        if (result.isConfirmed) {
            let zoneDTO = {};
            zoneDTO["id"] = id;
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/zones/delete-zone",
                data: JSON.stringify(zoneDTO),
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function (data) {
                    Swal.fire({
                        title: 'Thông bóa',
                        text: 'Xóa khu nuôi thành công!',
                        icon: 'warning',
                        showCancelButton: false,
                        showConfirmButton: true
                    }).then((result) => {
                        location.reload();
                    })
                },
                error: function (e) {
                    alert("Xóa khu nuôi thất bại!")
                }
            });
        }
    })
}

//Call api insert zone
function insertZone() {

    let zoneDTO = {};
    zoneDTO["name"] = $("#zone-name").val();
    zoneDTO["area"] = parseFloat($("#zone-area").val());
    zoneDTO["createdTime"] = $("#zone-create-time").val();
    zoneDTO["address"] = $("#zone-address").val();

    $("#cancel-btn").prop("disabled", true);
    $("#create-zone-btn").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/zones/insert-zone",
        data: JSON.stringify(zoneDTO),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data["msg"] === "insert-success") {
                alert("Tạo khu nuôi thành công!");
                hideNewZoneForm();
                location.reload();
            } else {
                alert("Vui lòng kiểm tra lại thông tin đã nhập!");
                $("#cancel-btn").prop("disabled", false);
                $("#create-zone-btn").prop("disabled", false);
            }
        },
        error: function (e) {
            alert("Thông tin vừa nhập không hợp lệ!");
            $("#cancel-btn").prop("disabled", false);
            $("#create-zone-btn").prop("disabled", false);
        }
    });

}

//Call api update zone
function updateZone() {

    let zoneDTO = {};
    zoneDTO["id"] = Number(document.querySelector('#zone-id').innerHTML);
    zoneDTO["name"] = $("#zone-name-update").val();
    zoneDTO["area"] = parseFloat($("#zone-area-update").val());
    zoneDTO["createdTime"] = $("#zone-create-time-update").val();
    zoneDTO["address"] = $("#zone-address-update").val();

    $("#cancel-update-btn").prop("disabled", true);
    $("#update-zone-btn").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/zones/update-zone",
        data: JSON.stringify(zoneDTO),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data["msg"] === "insert-success") {
                alert("Cập nhật khu nuôi thành công!");
                location.reload();
            } else {
                alert("Vui lòng kiểm tra lại thông tin đã nhập!");
                $("#cancel-update-btn").prop("disabled", true);
                $("#update-zone-btn").prop("disabled", true);
            }
        },
        error: function (e) {
            alert("Thông tin vừa nhập không hợp lệ!");
            $("#cancel-update-btn").prop("disabled", true);
            $("#update-zone-btn").prop("disabled", true);
        }
    });

}