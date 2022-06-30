var listProduct = {};
var dietUpdateId =0;

$(document).ready(function () {
    document.getElementById('show-modal-create-btn').addEventListener('click', showNewForm)
    document.getElementById('cancel-create-btn').addEventListener('click', cancelCreate)
    document.getElementById('submit-create-btn').addEventListener('click', insertDiet)
    document.getElementById('cancel-update-btn').addEventListener('click', cancelUpdate)
    document.getElementById('submit-update-btn').addEventListener('click', updateDiet)
    const dropdownPond = document.getElementById('pond-apply');
    const dropdownProduct = document.getElementById('product-apply');

    dropdownProduct.addEventListener('change', productChangeSelect);
})

function cancelUpdate() {
    if (confirm("Hãy chắc rằng mọi thông tin chế độ ăn đã nhập sẽ bị hủy bỏ!")) {
        document.querySelector('.js-modal-update').classList.remove('open');
        Swal.fire({
                icon: 'warning',
                title: 'THÔNG BÁO',
                text: 'Bạn đã hủy bỏ thao tác Cập nhật chế độ ăn!',
                showConfirmButton: false,
                showCancelButton: false,
                timer: 2000
            }
        );
    }
}

function updateDiet() {
    let dietDTO = {};
    dietDTO["id"] = dietUpdateId;
    dietDTO["productId"] = Number($('#product-apply-update').val());
    dietDTO["quantity"] = Number($('#quantity-update').val());
    dietDTO["longTime"] = Number($('#long-time-update').val());

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/diets/update-diet",
        data: JSON.stringify(dietDTO),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data["msg"] === "update-success") {
                Swal.fire({
                    title: 'Thông bóa',
                    text: 'Cập nhật chế độ ăn thành công!',
                    icon: 'warning',
                    showCancelButton: false,
                    showConfirmButton: true
                }).then((result) => {
                    location.reload();
                })
            } else {
                alert("Vui lòng kiểm tra lại thông tin đã nhập!");
            }
        },
        error: function (e) {
            alert("Thông tin vừa nhập không hợp lệ!");
        }
    });
}

function insertDiet() {
    let dietDTO = {};
    dietDTO["productId"] = Number($('#product-apply').val());
    dietDTO["quantity"] = Number($('#quantity').val());
    dietDTO["longTime"] = Number($('#long-time').val());
    dietDTO["pondId"] = Number($('#pond-apply').val());

    $("#cancel-create-btn").prop("disabled", true);
    $("#submit-create-btn").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/diets/insert-diet",
        data: JSON.stringify(dietDTO),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data["msg"] === "insert-success") {
                alert("Tạo chế độ ăn thành công!");
                document.querySelector('.js-modal').classList.remove('open');
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

function productChangeSelect() {
    let idProduct = Number($('#product-apply').val());
    let product = listProduct.filter(function (item) {
        return item.id === idProduct;
    });
    document.querySelector('#measure-product').innerHTML = '' + product[0].measure + '';
}

function showNewForm() {
    document.querySelector('.js-modal').classList.add('open')
    loadListPond();
    loadListProduct()
}

function loadListProduct() {
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
            document.querySelector('#measure-product').innerHTML = data.objects[0].measure;
            let html = '';
            for (let item of data.objects) {
                html += '<option value="' + item.id + '">' + item.id + '-' + item.name + '</option>';
            }
            document.querySelector('#product-apply').innerHTML = html;
            document.querySelector('#product-apply-update').innerHTML = html;
        }
    });
}

function loadListPond() {
    let zoneDTO = {};
    zoneDTO["id"] = Number($('#zone-contain-id').val());
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        type: "POST",
        contentType: "application/json",
        url: "/ponds/get-ponds-for-new-diet",
        data: JSON.stringify(zoneDTO),
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data.objects.length === 0) {
                Swal.fire({
                    title: 'Thông bóa',
                    text: 'Không tìm thấy ao khả dụng để tạo chế độ ăn mới!',
                    icon: 'warning',
                    showCancelButton: false,
                    showConfirmButton: true
                }).then((result) => {
                    document.querySelector('.js-modal').classList.remove('open')
                })
            }
            let html = '';
            for (let item of data.objects) {
                html += '<option value="' + item.id + '">' + item.id + '-' + item.name + '</option>';
            }
            document.querySelector('#pond-apply').innerHTML = html;
        }
    });
}

function cancelCreate() {
    if (confirm("Hãy chắc rằng mọi thông tin chế độ ăn đã nhập sẽ bị hủy bỏ!")) {
        document.querySelector('.js-modal').classList.remove('open');
        Swal.fire({
                icon: 'warning',
                title: 'THÔNG BÁO',
                text: 'Bạn đã hủy bỏ thao tác Tạo chế độ ăn mới!',
                showConfirmButton: false,
                showCancelButton: false,
                timer: 2000
            }
        );
    }
}

//Call API delete pond
function onDelete(dietId) {
    let msg = "<pre>Cảnh báo!\n" +
        "Bạn đang thực hiện thao tác Chế độ ăn có mã : " + dietId + "\n" +
        "Hãy chắc chắn rằng bạn thật sự muốn xóa chế độ ăn.</pre>"

    Swal.fire({
        title: 'Xác nhận xóa chế độ ăn',
        html: msg,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy'
    }).then((result) => {
        if (result.isConfirmed) {
            let dietDTO = {};
            dietDTO["id"] = dietId;
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/diets/delete-diet",
                data: JSON.stringify(dietDTO),
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function (data) {
                    Swal.fire({
                        title: 'Thông bóa',
                        text: 'Xóa Chế độ ăn thành công!',
                        icon: 'warning',
                        showCancelButton: false,
                        showConfirmButton: true
                    }).then((result) => {
                        location.reload();
                    })
                },
                error: function (e) {
                    alert("Xóa Chế độ ăn thất bại!")
                }
            });
        }
    })
}

function showUpdateDietForm(dietId) {
    dietUpdateId = dietId;
    document.querySelector('.js-modal-update').classList.add('open')
    loadDataOld(dietId)
    loadListProduct()
}

function loadDataOld(dietId){
    let dietDTO = {};
    dietDTO["id"] = dietId;
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        type: "POST",
        contentType: "application/json",
        url: "/diets/get-diet-info",
        data: JSON.stringify(dietDTO),
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data) {
                document.querySelector('#pond-apply-update').innerHTML = 'Mã ao áp dụng : ' + data.objects.pondId;
                document.querySelector('#long-time-update').setAttribute('value', data.objects.longTime);
                document.querySelector('#quantity-update').setAttribute('value', Number(data.objects.quantity));
            }
        }
    });
}
