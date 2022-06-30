var pondId;
var zoneId;
$(document).ready(function () {
    pondId = $('#pond-contain-id').val();
    zoneId = $('#zone-contain-id').val();
    document.querySelector('.back-to-zones').setAttribute('href','/zones/id='+ zoneId +'/ponds' )

    document.getElementById('show-modal-create-btn').addEventListener('click', showNewForm)
    document.getElementById('cancel-create-btn').addEventListener('click', hideNewProductSpendForm)
    document.getElementById('submit-create-btn').addEventListener('click', insertProductSpend)
    document.getElementById('product-apply').addEventListener('change',productChangeSelect)
})

function showNewForm() {
    document.querySelector('.js-modal').classList.add('open')
    loadListProduct();
}

function hideNewProductSpendForm () {
    if (confirm("Hãy chắc rằng mọi thông tin sử dụng sản phẩm đã nhập sẽ bị hủy bỏ!")) {
        document.querySelector('.js-modal').classList.remove('open');
        Swal.fire({
                icon: 'warning',
                title: 'THÔNG BÁO',
                text: 'Bạn đã hủy bỏ thao tác Tạo sử sử dụng sản phẩm!',
                showConfirmButton: false,
                showCancelButton: false,
                timer: 2000
            }
        );
    }
}

function insertProductSpend() {
    let productHistoryDTO = {};
    productHistoryDTO["productId"] = $('#product-apply').val();
    productHistoryDTO["quantity"] = $('#quantity').val();
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