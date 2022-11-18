$(document).ready(function () {

    let BODY = $("body");

    let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationDistrict");
    ajaxDistrictAll(params, "");

    //обработка ENTER
/*    $("#formDistrict").on("submit", function(event){
        event.preventDefault();
        $("#formDistrict #updateOrAddDistrict").click()
    })*/

    BODY.on('change','#col',function(){
        let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationDistrict");
        ajaxDistrictAll(params, "");
    });

    BODY.on('click', 'button', function () {

        //кнопка удалить
        if ($(this).hasClass("deleteDistrictBtn")) {
            let id = $(this).attr('name');
            if(confirm("Удалить запись с ID = " +
                $("button.deleteDistrictBtn[name='" + id + "']").parents('tr').children('td').eq(0).text())) {
                $.ajax({
                    url: "/overpayment/referenceBook/district/" + id,
                    cache: false,
                    processData: false,
                    type: 'DELETE',
                    /*beforeSend: function (xhr) {
                    xhr.setRequestHeader($('#_csrf').attr('content'),
                             $('#_csrf_header').attr('content'));
                    },*/
                    success: function () {
                        $("button.deleteDistrictBtn[name='" + id + "']").parents('tr').remove()
                        initialToats("Успешно!", "Данные удалены!" , "success").show();
                    },
                    error: function (response) {
                        initialToats("Ошибка!", response.responseJSON.message , "err").show();
                    }
                });
            }

        }
        //изменить модаль
        if ($(this).hasClass("updateDistrictBtn")) {
            $("#idModalDistrict").val($(this).parents('tr').children('td').eq(0).text());
            $("#modalKod").val($(this).parents('tr').children('td').eq(1).text());
            $("#nameModalDistrict").val($(this).parents('tr').children('td').eq(2).text());
            $("#updateOrAddDistrict").text('Изменить');
        }
        //Добавить модаль
        if ($(this).hasClass("addDistrict")) {
            $("#idModalDistrict").val("");
            $("#modalKod").val("");
            $("#nameModalDistrict").val("");
            $("#updateOrAddDistrict").text('Добавить');
        }

        //изменить
        if ($(this).attr('id')==="updateOrAddDistrict") {
            let id = $("#idModalDistrict").val();
            let district = $("#nameModalDistrict").val();
            let kod = $("#modalKod").val();
            let data = JSON.stringify({ 'id': id, 'name': district, 'kod': kod });

            let type = $("#updateOrAddDistrict").text()==="Изменить"?'PUT':'POST';

            $('#tableDistrict tbody').html('<tr><th>' + getSpinner() + '</th><td></td><td></td><td></td></tr>');
            $.ajax({
                url: "/overpayment/referenceBook/district",
                data: data,
                cache: false,
                processData: false,
                contentType: "application/json",
                type: type,
                /*beforeSend: function (xhr) {
                    xhr.setRequestHeader($('#_csrf').attr('content'),
                                         $('#_csrf_header').attr('content'));
                },*/
                success: function () {
                    //$("#modalDistrict").hide();
                    let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationDistrict");
                    initialToats("Успешно!", $("#updateOrAddDistrict").text()==="Изменить"?"Данные изменены!":"Данные сохранены!" , "success").show();
                    ajaxDistrictAll(params);
                },
                error: function (response) {
                    initialToats("Ошибка!", response.responseJSON.message , "err").show();
                    $('#tableDistrict tbody').html("");
                }
            });
        }
    });

    BODY.on('click', 'a', function () {
        //переключатели страниц pagination
        if ($(this).parents("#paginationDistrict").attr("id") === "paginationDistrict") {
            clickPagination($(this), "#paginationDistrict");
            let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationDistrict");
            ajaxDistrictAll(params, "");
        }
    });
});

function ajaxDistrictAll(params){
    $('#tableDistrict tbody').html('<tr><th>' + getSpinner() + '</th><td></td><td></td><td></td></tr>');
    $.ajax({
        url: "/overpayment/referenceBook/district/All?" + params,
        data: "",
        cache: false,
        processData: false,
        contentType: "application/json",
        dataType: 'json',
        type: 'POST',
        /*beforeSend: function (xhr) {
            xhr.setRequestHeader($('#_csrf').attr('content'),
                                 $('#_csrf_header').attr('content'));
        },*/
        success: function (response) {
            let trHTML = '';
            $('#tableDistrict tbody').html("");
            $.each(response, function (i, item) {
                trHTML +=
                    '<tr>' +
                    '<th>' + (+i+1) + '</th>' +
                    '<td>' + item.id + '</td>' +
                    '<td>' + item.kod + '</td>' +
                    '<td>' + item.name + '</td>' +
                    '<td>' +
                    '<div class="btn-group" role="group">' +
                    '<button ' +
                    'class="btn  btn-secondary btn-sm deleteDistrictBtn" ' +
                    'type="button" ' +
                    'name="' + item.id + '" ' +
                    '>Удалить</button>' +

                    '<button ' +
                    'class="btn  btn-secondary btn-sm updateDistrictBtn mx-1" ' +
                    'data-bs-toggle="modal" ' +
                    'data-bs-target="#modalDistrict" ' +
                    'type="button" ' +
                    'name="' + item.id + '" ' +
                    '>Изменить</button>' +


                    '</div>' +
                    '</td>' +
                    '</tr>';
            });
            $('#tableDistrict').append(trHTML);
        },
        error: function (response) {
            initialToats("Ошибка!", response.responseJSON.message , "err").show();
            $('#tableDistrict tbody').html("");
        }
    });
}
