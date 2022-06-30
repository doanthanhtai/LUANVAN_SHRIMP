var idPondUpdate;
$(document).ready(function () {
    document.getElementById('show-modal-create-btn').addEventListener('click', showNewPondForm)
    document.getElementById('cancel-create-btn').addEventListener('click', hideNewPondForm)
    document.getElementById('submit-create-btn').addEventListener('click', insertPond)
    document.getElementById('submit-update-btn').addEventListener('click', updatePond)
    document.getElementById('cancel-update-btn').addEventListener('click', hideUpdatePond)
})

//Hàm call api update pond
function updatePond() {
    let pondDTO = {};
    pondDTO["id"] = Number(idPondUpdate);
    pondDTO["name"] = $("#pond-name-update").val();
    pondDTO["area"] = parseFloat($("#pond-area-update").val());
    pondDTO["pondType"] = $("#pond-type-update").val();
    pondDTO["waterHeight"] = $("#pond-water-height-update").val();
    pondDTO["createdTime"] = $("#pond-create-time-update").val();

    $("#cancel-update-btn").prop("disabled", true);
    $("#submit-update-btn").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/ponds/update-pond",
        data: JSON.stringify(pondDTO),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data["msg"] === "insert-success") {
                alert("Cập nhật thông tin ao thành công!");
                location.reload();
            } else {
                alert("Vui lòng kiểm tra lại thông tin đã nhập!");
                $("#cancel-update-btn").prop("disabled", true);
                $("#submit-update-btn").prop("disabled", true);
            }
        },
        error: function (e) {
            alert("Thông tin vừa nhập không hợp lệ!");
            $("#cancel-update-btn").prop("disabled", true);
            $("#submit-update-btn").prop("disabled", true);
        }
    });
}

//Hàm hủy cập nhật ao
function hideUpdatePond() {
    if (confirm("Hãy chắc rằng mọi thông tin ao đã nhập sẽ bị hủy bỏ!")) {
        document.querySelector('.js-modal-update').classList.remove('open')
        Swal.fire({
                icon: 'warning',
                title: 'THÔNG BÁO',
                text: 'Bạn đã hủy bỏ thao tác Cập nhật thông tin ao!',
                showConfirmButton: false,
                showCancelButton: false,
                timer: 2000
            }
        );
    }
}

//Hàm hiển thị modal update
function showUpdatePondForm(id) {
    idPondUpdate = id;
    document.querySelector('.js-modal-update').classList.add('open')
    loadDataOld();
    document.querySelector('#pond-id').innerText = id;
}

function loadDataOld() {
    let ponDT0 = {};
    ponDT0["id"] = Number(idPondUpdate);
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        type: "POST",
        contentType: "application/json",
        url: "/ponds/get-pond-info",
        data: JSON.stringify(ponDT0),
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data) {
                document.querySelector('#pond-name-update').setAttribute('value', data.objects.name);
                document.querySelector('#pond-type-update').setAttribute('value', data.objects.pondType);
                document.querySelector('#pond-create-time-update').setAttribute('value', data.objects.createdTime);
                document.querySelector('#pond-area-update').setAttribute('value', data.objects.area);
                document.querySelector('#pond-water-height-update').setAttribute('value', data.objects.waterHeight);
            }
        }
    });
}

function insertPond() {
    let pondDTO = {};
    pondDTO["zoneId"] = Number($("#zone-contain-id").val());
    pondDTO["name"] = $("#pond-name").val();
    pondDTO["pondType"] = parseInt($("#pond-type").val());
    pondDTO["createdTime"] = $("#pond-create-time").val();
    pondDTO["area"] = parseFloat($("#pond-area").val());
    pondDTO["waterHeight"] = parseFloat($("#pond-water-height").val());

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/ponds/insert-pond",
        data: JSON.stringify(pondDTO),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data["msg"] === "insert-success") {
                alert("Tạo ao thành công!");
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

function showNewPondForm() {
    document.querySelector('.js-modal').classList.add('open')
    $('#pond-create-time').val(new Date().toISOString().substring(0, 10));
}

function hideNewPondForm() {
    if (confirm("Hãy chắc rằng mọi thông tin ao đã nhập sẽ bị hủy bỏ!")) {
        document.querySelector('.js-modal').classList.remove('open');
        Swal.fire({
                icon: 'warning',
                title: 'THÔNG BÁO',
                text: 'Bạn đã hủy bỏ thao tác Tạo ao mới!',
                showConfirmButton: false,
                showCancelButton: false,
                timer: 2000
            }
        );
    }
}

//Call API delete pond
function onDelete(pondId) {
    let msg = "<pre>Cảnh báo!\n" +
        "Bạn đang thực hiện thao tác Ao có mã : " + pondId + "\n" +
        "Hãy chắc chắn rằng bạn thật sự muốn xóa ao.</pre>"

    Swal.fire({
        title: 'Xác nhận xóa ao',
        html: msg,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy'
    }).then((result) => {
        if (result.isConfirmed) {
            let pondDTO = {};
            pondDTO["id"] = pondId;
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/ponds/delete-ponds",
                data: JSON.stringify(pondDTO),
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function (data) {
                    Swal.fire({
                        title: 'Thông bóa',
                        text: 'Xóa ao thành công!',
                        icon: 'warning',
                        showCancelButton: false,
                        showConfirmButton: true
                    }).then((result) => {
                        location.reload();
                    })
                },
                error: function (e) {
                    alert("Xóa ao thất bại!")
                }
            });
        }
    })
    // if (confirm("Hãy chắc rằng bạn thật sự muốn xóa ao có mã ao là : " + pondId + "\nKhỏi danh sách ao của bạn!")) {
    //     let pondDTO = {};
    //     pondDTO["id"] = pondId;
    //     $.ajax({
    //         type: "POST",
    //         contentType: "application/json",
    //         url: "/ponds/delete-ponds",
    //         data: JSON.stringify(pondDTO),
    //         dataType: 'json',
    //         cache: false,
    //         timeout: 600000,
    //         success: function (data) {
    //             if (data["msg"] === "delete-success") {
    //                 /*Swal.fire({
    //                         icon: 'warning',
    //                         title: 'THÔNG BÁO',
    //                         text: 'Xóa ao thành công!',
    //                         showConfirmButton: true,
    //                         showCancelButton: false,
    //                         timer: 6000
    //                     }, function () {
    //                         location.reload();
    //                     }
    //                 );*/
    //                 location.reload();
    //             } else {
    //             }
    //         },
    //         error: function (e) {
    //         }
    //     });
    // }
}
