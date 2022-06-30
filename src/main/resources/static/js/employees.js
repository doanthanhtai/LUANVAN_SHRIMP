var idEmployeeUpdate;

//Hàm hiển thị modal mua vé (thêm class open vào modal)
function showNewZoneForm() {
    document.querySelector('.js-modal').classList.add('open')
}

//Hàm ẩn modal
function hideNewEmployeeForm() {
    document.querySelector('.js-modal').classList.remove('open')
}

//Hàm ẩn modal
function hideUpdateEmployeeForm() {
    document.querySelector('.js-modal-update').classList.remove('open')
}

$(document).ready(function () {
        const btnNewZone = document.getElementById('new-employee-btn')
        const btnCancel = document.getElementById('cancel-btn')
        const btnConfirm = document.getElementById('create-employee-btn')
        const btnCancelUpdate = document.getElementById('cancel-update-btn')
        const btnSubmitUpdate = document.getElementById('submit-update-btn')

        $('#employee-onboard-date').val(new Date().toISOString().substring(0, 10));

        // Nghe hành vi click vào button new zone để gọi show new zone form
        btnNewZone.addEventListener('click', showNewZoneForm)

        //Nghe hành vi click vào button cancel
        btnCancel.addEventListener('click', function () {
            if (confirm("Hãy chắc rằng mọi thông tin nhân sự đã nhập sẽ bị hủy bỏ!")) {
                hideNewEmployeeForm();
                Swal.fire({
                        icon: 'warning',
                        title: 'THÔNG BÁO',
                        text: 'Bạn đã hủy bỏ thao tác Thêm nhân sự mới!',
                        showConfirmButton: false,
                        showCancelButton: false,
                        timer: 2000
                    }
                );
            }
        })

        btnCancelUpdate.addEventListener('click', function () {
            if (confirm("Hãy chắc rằng mọi thông tin nhân sự đã nhập sẽ bị hủy bỏ!")) {
                hideUpdateEmployeeForm();
                Swal.fire({
                        icon: 'warning',
                        title: 'THÔNG BÁO',
                        text: 'Bạn đã hủy bỏ thao tác Cập nhật thông tin nhân sự!',
                        showConfirmButton: false,
                        showCancelButton: false,
                        timer: 2000
                    }
                );
            }
        })

        btnConfirm.addEventListener('click', insertEmployee);
        btnSubmitUpdate.addEventListener('click', updateEmployee)
    }
)

function insertEmployee() {

    let employeeDTO = {};
    employeeDTO["name"] = $("#employee-name").val();
    employeeDTO["phone"] = $("#employee-phone").val();
    employeeDTO["onboardDate"] = $("#employee-onboard-date").val();
    employeeDTO["saliry"] = parseFloat($("#employee-saliry").val());
    employeeDTO["bankNo"] = $("#employee-bank-no").val();
    employeeDTO["bankAccountName"] = $("#employee-bank-account-name").val();
    employeeDTO["bankName"] = $("#employee-bank-name").val();
    employeeDTO["emailAndroidApp"] = $("#employee-email-account-app").val();

    $("#cancel-btn").prop("disabled", true);
    $("#create-employee-btn").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/employees/insert-employee",
        data: JSON.stringify(employeeDTO),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data["msg"] === "insert-success") {
                alert("Thêm nhân sự mới thành công!");
                hideNewEmployeeForm();
                location.reload();
            } else {
                alert("Vui lòng kiểm tra lại thông tin đã nhập!");
                $("#cancel-btn").prop("disabled", false);
                $("#create-employee-btn").prop("disabled", false);
            }
        },
        error: function (e) {
            alert("Thông tin vừa nhập không hợp lệ!");
            $("#cancel-btn").prop("disabled", false);
            $("#create-employee-btn").prop("disabled", false);
        }
    });
}

function loadDataOld() {
    let employeeDTO = {};
    employeeDTO["id"] = Number(idEmployeeUpdate);
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        type: "POST",
        contentType: "application/json",
        url: "/employees/get-employee-info",
        data: JSON.stringify(employeeDTO),
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data) {
                document.querySelector('#employee-name-update').setAttribute('value', data.objects.name);
                document.querySelector('#employee-phone-update').setAttribute('value', data.objects.phone);
                document.querySelector('#employee-onboard-date-update').setAttribute('value', data.objects.onboardDate);
                document.querySelector('#employee-saliry-update').setAttribute('value', data.objects.saliry);
                document.querySelector('#employee-bank-no-update').setAttribute('value', data.objects.bankNo);
                document.querySelector('#employee-bank-name-update').setAttribute('value', data.objects.bankName);
                document.querySelector('#employee-bank-account-name-update').setAttribute('value', data.objects.bankAccountName);
            }
        }
    });
}

function showUpdateEmployeeForm(id) {
    idEmployeeUpdate = id;
    document.querySelector('.js-modal-update').classList.add('open')
    loadDataOld();
    document.querySelector('#employee-id').innerText = id;
}

function deleteEmployee(id) {
    let msg = "<pre>Cảnh báo!\n" +
        "Bạn đang thực hiện thao tác xóa nhân sự có mã : " + id + "\n" +
        "Hãy chắc chắn rằng bạn thật sự muốn nhân sự đó.</pre>"

    Swal.fire({
        title: 'Xác nhận xóa nhân sự',
        html: msg,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy'
    }).then((result) => {
        if (result.isConfirmed) {
            callAPIDelete(id)
        }
    });
}

function updateEmployee () {
    let employeeDTO = {};
    employeeDTO["id"] = idEmployeeUpdate;
    employeeDTO["name"] = $("#employee-name-update").val();
    employeeDTO["phone"] = $("#employee-phone-update").val();
    employeeDTO["onboardDate"] = $("#employee-onboard-date-update").val();
    employeeDTO["saliry"] = parseFloat($("#employee-saliry-update").val());
    employeeDTO["bankNo"] = $("#employee-bank-no-update").val();
    employeeDTO["bankAccountName"] = $("#employee-bank-account-name-update").val();
    employeeDTO["bankName"] = $("#employee-bank-name-update").val();

    $("#cancel-btn").prop("disabled", true);
    $("#create-zone-btn").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/employees/update-employee",
        data: JSON.stringify(employeeDTO),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data["msg"] === "update-success") {
                alert("Cập nhật thông tin nhân sự thành công!");
                location.reload();
            } else {
                alert("Vui lòng kiểm tra lại thông tin đã nhập!");
                $("#cancel-btn").prop("disabled", false);
                $("#create-zone-btn").prop("disabled", false);
            }
        },
        error: function (e) {
            alert("Thông tin vừa nhập không hợp lệ, vui lòng kiểm tra lại thông tin!");
            $("#cancel-btn").prop("disabled", false);
            $("#create-zone-btn").prop("disabled", false);
        }
    });
}

function callAPIDelete(employeeId) {
    let employeeDTO = {};
    employeeDTO["id"] = employeeId;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/employees/delete-employee",
        data: JSON.stringify(employeeDTO),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data["msg"] === "delete-success") {
                Swal.fire({
                    title: 'Thông bóa',
                    text: 'Xóa nhân sự thành công!',
                    icon: 'warning',
                    showCancelButton: false,
                    showConfirmButton: true
                }).then((result) => {
                    location.reload();
                })
            }
        },
        error: function (e) {
        }
    });
}