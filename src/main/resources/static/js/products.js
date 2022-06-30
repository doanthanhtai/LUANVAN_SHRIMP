//Hàm hiển thị modal mua vé (thêm class open vào modal)
function showNewProductForm() {
    document.querySelector('.js-modal').classList.add('open')
}

//Hàm ẩn modal
function hideNewProductForm() {
    document.querySelector('.js-modal').classList.remove('open')
}

$(document).ready(function () {
        const btnNewProduct = document.getElementById('new-product-btn')
        const btnCancelCreate = document.getElementById('cancel-create-btn')
        const btnConfirmCreate = document.getElementById('submit-create-btn')

        // Nghe hành vi click vào button new zone để gọi show new zone form
        btnNewProduct.addEventListener('click', showNewProductForm)

        //Nghe hành vi click vào button cancel
        btnCancelCreate.addEventListener('click', function () {
            if (confirm("Hãy chắc rằng mọi thông tin sản phẩm mới đã nhập sẽ bị hủy bỏ!")) {
                hideNewProductForm();
                Swal.fire({
                        icon: 'warning',
                        title: 'THÔNG BÁO',
                        text: 'Bạn đã hủy bỏ thao tác Tạo sản phẩm mới!',
                        showConfirmButton: false,
                        showCancelButton: false,
                        timer: 2000
                    }
                );
            }
        })

        btnConfirmCreate.addEventListener('click', insertProduct);
    }
)

function insertProduct() {

    let productDTO = {};
    productDTO["name"] = $("#product-name").val();
    productDTO["supplier"] = $("#product-supplier").val();
    productDTO["enterPrice"] = parseFloat($("#product-enter-price").val());
    productDTO["measure"] = $("#product-measure").val();

    $("#cancel-create-btn").prop("disabled", true);
    $("#submit-create-btn").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/products/insert-product",
        data: JSON.stringify(productDTO),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data["msg"] === "insert-success") {
                alert("Tạo sản phẩm thành công!");
                hideNewProductForm();
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

function showUpdateForm(id) {
    document.querySelector('.js-modal-update').classList.add('open');
    getOldData(id);
}

function getOldData(id) {
    let productDTO = {};
    productDTO["id"] = Number(id);
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
            if (data) {
                document.querySelector('#product-name-update').setAttribute('value', data.objects.name);
                document.querySelector('#product-supplier-update').setAttribute('value', data.objects.supplier);
                document.querySelector('#product-enter-price-update').setAttribute('value', data.objects.enterPrice);
                document.querySelector('#product-measure-update').setAttribute('value', data.objects.measure);
                return data.objects;
            }
        }
    });
}