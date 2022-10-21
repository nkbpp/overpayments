$(document).ready(function () {

    let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationReasonsForOverpayments");
    ajaxReasonsForOverpaymentsAll(params, "");

    $("body").on('change','#col',function(){
        let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationReasonsForOverpayments");
        ajaxReasonsForOverpaymentsAll(params, "");
    });

    $("body").on('click', 'button', function () {

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
                    success: function (response) {
                        $("button.deleteReasonsForOverpaymentsBtn[name='" + id + "']").parents('tr').remove()
                    },
                    error: function (jqXHR, textStatus) {
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
                success: function (response) {
                    //$("#modalReasonsForOverpayments").hide();
                    let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationReasonsForOverpayments");
                    ajaxReasonsForOverpaymentsAll(params);
                },
                error: function (jqXHR, textStatus) {
                    alert("err " + textStatus + " !!! " +  jqXHR)
                    $('#tableReasonsForOverpayments tbody').html("");
                }
            });
        }
    });

    $("body").on('click', 'a', function () {
        //переключатели страниц pagination
        if ($(this).parents("#paginationReasonsForOverpayments").attr("id") === "paginationReasonsForOverpayments") {
            clickPagination($(this), "#paginationReasonsForOverpayments");
            let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationReasonsForOverpayments");
            ajaxReasonsForOverpaymentsAll(params, "");
        }
    });
});

function ajaxReasonsForOverpaymentsAll(params){
    $('#tableReasonsForOverpayments tbody').html('<tr><th>' + getSpinner() + '</th><td></td><td></td><td></td></tr>');
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
        error: function (jqXHR, textStatus) {
            alert("ERROR")
            $('#tableReasonsForOverpayments tbody').html("");
        }
    });
}
