var pondId;
var zoneId;
$(document).ready(function () {
    pondId = $('#pond-contain-id').val();
    zoneId = $('#zone-contain-id').val();
    document.querySelector('.back-to-zones').setAttribute('href','/zones/id='+ zoneId +'/ponds' )
})