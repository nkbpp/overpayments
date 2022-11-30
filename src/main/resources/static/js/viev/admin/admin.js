$(document).ready(function () {

    let BODY = $("body");
    ajaxLogiAll(getLogiParams());

    BODY.on('change', '#col', function () {
        ajaxLogiAll(getLogiParams());
    });

    BODY.on('click', 'a', function () {
        //переключатели страниц pagination
        if ($(this).parents("#paginationLogi").attr("id") === "paginationLogi") {
            clickPagination($(this), "#paginationLogi");
            ajaxLogiAll(getLogiParams());
        }
    });

    BODY.on('click', 'button', function () {
        //кнопка поиска
        if ($(this).attr("id") === "findLog") {
            ajaxLogiAll(getLogiParams());
        }
    });

    BODY.on('click', 'button', function () {
        if ($(this).attr("id") === "clearLog") {
            if(confirm("Удалить все записи?")) {
                $.ajax({
                    url: "/admin/log/deleteAll",
                    data: "",
                    cache: false,
                    processData: false,
                    type: 'DELETE',
                    success: function (response) {
                        ajaxLogiAll(getLogiParams());
                        clearPagination($("#paginationLogi a"))
                        initialToats("Все записи удалены!", response, "success").show();
                    },
                    error: function (response) {
                        initialToats("Ошибка!", response.responseJSON.message, "err").show();
                        $('#tableLogi tbody').html("");
                    }
                });
            }
        }
    });

});

function getLogiParams() {
    return "col=" + $("#col").val() + "&pagination=" + activeList("#paginationLogi");
}

function getLogiData() {
    let fd = new FormData(document.getElementById("formFindLog"));
    let object = {};
    fd.forEach((value, key) => object[key] = value);
    object.dateS = object.dateS === "" ? "01.01.1800" : object.dateS;
    object.datePo = object.datePo === "" ? "31.12.2113" : object.datePo;
    return JSON.stringify(object);
}

function ajaxLogiAll(params) {
    getSpinnerTable("tableLogi")
    $.ajax({
        url: "/admin/log/find?" + params,
        data: getLogiData(),
        cache: false,
        processData: false,
        contentType: "application/json",
        dataType: 'json',
        type: 'POST',
        success: function (response) {
            let trHTML = '';
            $('#tableLogi tbody').html("");
            $.each(response, function (i, item) {
                trHTML +=
                    '<tr class="' + (
                        item.type === "Danger" ? "table-danger" :
                            item.type === "Warning" ? "table-warning" :
                                item.type === "Success" ? "table-success" :
                                    "table-info"
                    ) + '">' +
                    '<th>' + (+i + 1) + '</th>' +
                    '<td>' + item.date + '</td>' +
                    '<td>' + item.user + '</td>' +
                    '<td>' + item.type + '</td>' +
                    '<td>' + item.text + '</td>' +
                    '</tr>';
            });
            $('#tableLogi').append(trHTML);
        },
        error: function (response) {
            initialToats("Ошибка!", response.responseJSON.message, "err").show();
            $('#tableLogi tbody').html("");
        }
    });
}
