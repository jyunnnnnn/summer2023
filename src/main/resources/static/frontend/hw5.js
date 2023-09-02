$(document).ready(function () {
    $('#JhongShan').click(function () {
        loadSights('中山');
    });
    $('#SinYi').click(function () {
        loadSights('信義');
    });
    $('#RenAi').click(function () {
        loadSights('仁愛');
    });
    $('#JhongJheng').click(function () {
        loadSights('中正');
    });
    $('#allen').click(function () {
        loadSights('安樂');
    });
    $('#ChiDu').click(function () {
        loadSights('七堵');
    });
    $('#NuanNuan').click(function () {
        loadSights('暖暖');
    });
});
function loadSights(zone) {
    var newZone= encodeURIComponent(zone+"區");
    $.ajax({
        url: '/api/sights?zone='+newZone,
        type: 'GET',
        //data: { zone: newZone },
        dataType: 'json',
        success: function (data) {
            showSights(data);
        },
        error: function (xhr, status, error) {
            console.error(error);
        }
    });
}

function showSights(sights) {
    var sightsContainer = $('#sightsContainer');
    sightsContainer.empty();

    sights.forEach(function (sight) {
        var encodedSightName = generateSightId(sight.sightName);
        var card = $('<div class="col-md-4 mb-4">' +
            '<div class="card">' +
            '<div class="card-body">' +
            '<h5 class="card-title">' + sight.sightName + '</h5>' +
            '<p class="card-text">區域：' + sight.zone + '</p>' +
            '<p class="card-text">分類：' + sight.category + '</p>' +
            '<button class="btn btn-primary" data-bs-toggle="collapse" data-bs-target="#collapse-' + encodedSightName + '">詳細資訊</button>' +
            '<div class="collapse" id="collapse-' + encodedSightName + '">' +
            '<div class="card card-body">' +
            '<img src="' + sight.photoURL + '" class="card-img-top" alt="圖片失效">' + '<br>' +
            '<p>' + sight.description + '</p>' +
            '<a href="https://www.google.com/maps/place/' + sight.sightName + '"target="_blank" class="btn btn-secondary">地址</a>'+
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>');

        sightsContainer.append(card);
    });
}
function generateSightId(sightName) {
  var hash = CryptoJS.SHA1(sightName).toString();
  var sightId = hash.substring(0, 8);
  return sightId;
}