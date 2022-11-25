$(document).ready(function () {

    let BODY = $("body");

    let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationReasonsForOverpayments");
    ajaxReasonsForOverpaymentsAll(params, "");

    //обработка ENTER
/*    $("#formReasonsForOverpayments").on("submit", function(event){
        event.preventDefault();
        $("#formReasonsForOverpayments #updateOrAddReasonsForOverpayments").click()
    })*/

    BODY.on('change','#col',function(){
        let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationReasonsForOverpayments");
        ajaxReasonsForOverpaymentsAll(params, "");
    });

    BODY.on('click', 'button', function () {

        //кнопка удалить
        if ($(this).hasClass("deleteReasonsForOverpaymentsBtn")) {
            let id = $(this).attr('name');
            if(confirm("Удалить запись с ID = " +
                $("button.deleteReasonsForOverpaymentsBtn[name='" + id + "']").parents('tr').children('td').eq(0).text())) {
                $.ajax({
                    url: "/overpayment/referenceBook/reasonsForOverpayments/" + id,
                    cache: false,
                    processData: false,
                    type: 'DELETE',
                    /*beforeSend: function (xhr) {
                    xhr.setRequestHeader($('#_csrf').attr('content'),
                             $('#_csrf_header').attr('content'));
                    },*/
                    success: function () {
                        $("button.deleteReasonsForOverpaymentsBtn[name='" + id + "']").parents('tr').remove()
                        initialToats("Успешно!", "Данные удалены!" , "success").show();
                    },
                    error: function (response) {
                        initialToats("Ошибка!", response.responseJSON.message , "err").show();
                    }
                });
            }

        }
        //изменить модаль
        if ($(this).hasClass("updateReasonsForOverpaymentsBtn")) {
            $("#idModalReasonsForOverpayments").val($(this).parents('tr').children('td').eq(0).text());
            $("#nameModalReasonsForOverpayments").val($(this).parents('tr').children('td').eq(1).text());
            $("#updateOrAddReasonsForOverpayments").text('Изменить');
        }
        //Добавить модаль
        if ($(this).hasClass("addReasonsForOverpayments")) {
            $("#idModalReasonsForOverpayments").val("");
            $("#nameModalReasonsForOverpayments").val("");
            $("#updateOrAddReasonsForOverpayments").text('Добавить');
        }

        //изменить
        if ($(this).attr('id')==="updateOrAddReasonsForOverpayments") {
            let id = $("#idModalReasonsForOverpayments").val();
            let reasonsForOverpayments = $("#nameModalReasonsForOverpayments").val();
            let data = JSON.stringify({ 'id': id, 'reasonsForOverpayments': reasonsForOverpayments });

            let type = $("#updateOrAddReasonsForOverpayments").text()==="Изменить"?'PUT':'POST';

            $('#tableReasonsForOverpayments tbody').html('<tr><th>' + getSpinner() + '</th><td></td><td></td><td></td></tr>');
            $.ajax({
                url: "/overpayment/referenceBook/reasonsForOverpayments",
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
                    //$("#modalReasonsForOverpayments").hide();
                    let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationReasonsForOverpayments");
                    initialToats("Успешно!", $("#updateOrAddReasonsForOverpayments").text()==="Изменить"?"Данные изменены!":"Данные сохранены!" , "success").show();
                    ajaxReasonsForOverpaymentsAll(params);
                },
                error: function (response) {
                    initialToats("Ошибка!", response.responseJSON.message , "err").show();
                    $('#tableReasonsForOverpayments tbody').html("");
                }
            });
        }
    });

    BODY.on('click', 'a', function () {
        //переключатели страниц pagination
        if ($(this).parents("#paginationReasonsForOverpayments").attr("id") === "paginationReasonsForOverpayments") {
            clickPagination($(this), "#paginationReasonsForOverpayments");
            let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationReasonsForOverpayments");
            ajaxReasonsForOverpaymentsAll(params, "");
        }
    });
});

function ajaxReasonsForOverpaymentsAll(params){
    getSpinnerTable("tableReasonsForOverpayments")

    $.ajax({
        url: "/overpayment/referenceBook/reasonsForOverpayments/All?" + params,
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
            $('#tableReasonsForOverpayments tbody').html("");
            $.each(response, function (i, item) {
                trHTML +=
                    '<tr>' +
                    '<th>' + (+i+1) + '</th>' +
                    '<td>' + item.id + '</td>' +
                    '<td>' + item.reasonsForOverpayments + '</td>' +
                    '<td>' +
                    '<div class="btn-group" role="group">' +
                    '<button ' +
                    'class="btn  btn-secondary btn-sm deleteReasonsForOverpaymentsBtn" ' +
                    'type="button" ' +
                    'name="' + item.id + '" ' +
                    '>Удалить</button>' +

                    '<button ' +
                    'class="btn  btn-secondary btn-sm updateReasonsForOverpaymentsBtn mx-1" ' +
                    'data-bs-toggle="modal" ' +
                    'data-bs-target="#modalReasonsForOverpayments" ' +
                    'type="button" ' +
                    'name="' + item.id + '" ' +
                    '>Изменить</button>' +


                    '</div>' +
                    '</td>' +
                    '</tr>';
            });
            $('#tableReasonsForOverpayments').append(trHTML);
        },
        error: function (response) {
            initialToats("Ошибка!", response.responseJSON.message , "err").show();
            $('#tableReasonsForOverpayments tbody').html("");
        }
    });
}
