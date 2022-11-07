$(document).ready(function () {

    let BODY = $("body");

    let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationSpecificationOfTheReasonsForOverpayments");
    ajaxSpecificationOfTheReasonsForOverpaymentsAll(params, "");

    BODY.on('change','#col',function(){
        let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationSpecificationOfTheReasonsForOverpayments");
        ajaxSpecificationOfTheReasonsForOverpaymentsAll(params, "");
    });

    BODY.on('click', 'button', function () {

        //кнопка удалить
        if ($(this).hasClass("deleteSpecificationOfTheReasonsForOverpaymentsBtn")) {
            let id = $(this).attr('name');
            if(confirm("Удалить запись с ID = " +
                $("button.deleteSpecificationOfTheReasonsForOverpaymentsBtn[name='" + id + "']").parents('tr').children('td').eq(0).text())){
                $.ajax({
                    url: "/overpayment/referenceBook/specificationOfTheReasonsForOverpayments/" + id,
                    cache: false,
                    processData: false,
                    type: 'DELETE',
                    /*beforeSend: function (xhr) {
                    xhr.setRequestHeader($('#_csrf').attr('content'),
                             $('#_csrf_header').attr('content'));
                    },*/
                    success: function () {
                        $("button.deleteSpecificationOfTheReasonsForOverpaymentsBtn[name='" + id + "']").parents('tr').remove()
                    },
                    error: function (jqXHR, textStatus) {
                    }
                });
            }
        }
        //изменить модаль
        if ($(this).hasClass("updateSpecificationOfTheReasonsForOverpaymentsBtn")) {
            $("#idModalSpecificationOfTheReasonsForOverpayments").val($(this).parents('tr').children('td').eq(0).text());
            $("#nameModalSpecificationOfTheReasonsForOverpayments").val($(this).parents('tr').children('td').eq(1).text());
            $("#updateOrAddSpecificationOfTheReasonsForOverpayments").text('Изменить');
        }
        //Добавить модаль
        if ($(this).hasClass("addSpecificationOfTheReasonsForOverpayments")) {
            $("#idModalSpecificationOfTheReasonsForOverpayments").val("");
            $("#nameModalSpecificationOfTheReasonsForOverpayments").val("");
            $("#updateOrAddSpecificationOfTheReasonsForOverpayments").text('Добавить');
        }

        //изменить
        if ($(this).attr('id')==="updateOrAddSpecificationOfTheReasonsForOverpayments") {
            let id = $("#idModalSpecificationOfTheReasonsForOverpayments").val();
            let specificationOfTheReasonsForOverpayments = $("#nameModalSpecificationOfTheReasonsForOverpayments").val();
            let data = JSON.stringify({ 'id': id, 'specificationOfTheReasonsForOverpayments': specificationOfTheReasonsForOverpayments });

            let type = $("#updateOrAddSpecificationOfTheReasonsForOverpayments").text()==="Изменить"?'PUT':'POST';

            $('#tableSpecificationOfTheReasonsForOverpayments tbody').html('<tr><th>' + getSpinner() + '</th><td></td><td></td><td></td></tr>');
            $.ajax({
                url: "/overpayment/referenceBook/specificationOfTheReasonsForOverpayments",
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
                    //$("#modalSpecificationOfTheReasonsForOverpayments").hide();
                    let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationSpecificationOfTheReasonsForOverpayments");
                    ajaxSpecificationOfTheReasonsForOverpaymentsAll(params);
                },
                error: function (jqXHR, textStatus) {
                    alert("err " + textStatus + " !!! " +  jqXHR)
                }
            });
        }
    });

    BODY.on('click', 'a', function () {
        //переключатели страниц pagination
        if ($(this).parents("#paginationSpecificationOfTheReasonsForOverpayments").attr("id") === "paginationSpecificationOfTheReasonsForOverpayments") {
            clickPagination($(this), "#paginationSpecificationOfTheReasonsForOverpayments");
            let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationSpecificationOfTheReasonsForOverpayments");
            ajaxSpecificationOfTheReasonsForOverpaymentsAll(params, "");
        }

    });
});

function ajaxSpecificationOfTheReasonsForOverpaymentsAll(params){
    $('#tableSpecificationOfTheReasonsForOverpayments tbody').html('<tr><th>' + getSpinner() + '</th><td></td><td></td><td></td></tr>');
    $.ajax({
        url: "/overpayment/referenceBook/specificationOfTheReasonsForOverpayments/All?" + params,
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
            $('#tableSpecificationOfTheReasonsForOverpayments tbody').html("");
            $.each(response, function (i, item) {
                trHTML +=
                    '<tr>' +
                    '<th>' + (+i+1) + '</th>' +
                    '<td>' + item.id + '</td>' +
                    '<td>' + item.specificationOfTheReasonsForOverpayments + '</td>' +
                    '<td>' +
                    '<div class="btn-group" role="group">' +

                    '<button ' +
                    'class="btn btn-secondary btn-sm deleteSpecificationOfTheReasonsForOverpaymentsBtn" ' +
                    'type="button" ' +
                    'name="' + item.id + '" ' +
                    '>Удалить</button>' +
                    '<button ' +
                    'class="btn btn-secondary btn-sm updateSpecificationOfTheReasonsForOverpaymentsBtn mx-1" ' +
                    'data-bs-toggle="modal" ' +
                    'data-bs-target="#modalSpecificationOfTheReasonsForOverpayments" ' +
                    'type="button" ' +
                    'name="' + item.id + '" ' +
                    '>Изменить</button>' +

                    '</div>' +
                    '</td>' +
                    '</tr>';
            });
            $('#tableSpecificationOfTheReasonsForOverpayments').append(trHTML);
        },
        error: function (jqXHR, textStatus) {
            alert("ERROR " + jqXHR + " " + textStatus)
        }
    });
}
