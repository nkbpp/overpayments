let SELECTIZEREASONFOROVERPAYMENT;
let SELECTIZEPENSIONER;
let SELECTIZECARER;
$(document).ready(function () {

    SELECTIZEREASONFOROVERPAYMENT = $("#selectReasonsForOverpayments").selectize({
        create: true,
        //sortField: "text",
    });
    SELECTIZEPENSIONER = $("#selectDocumentsPensioner").selectize({
        create: true,
        //sortField: "text",
    });
    SELECTIZECARER = $("#selectDocumentsCarer").selectize({
        create: true,
        //sortField: "text",
    });


    $(".addSpecificationOfTheReasonsForOverpayments").addClass("d-none");

    let BODY = $("body");

    //обработка ENTER
/*    $("#formSpecificationOfTheReasonsForOverpayments").on("submit", function(event){
        event.preventDefault();
        $("#formSpecificationOfTheReasonsForOverpayments #updateOrAddSpecificationOfTheReasonsForOverpayments").click()
    })*/

    //Чистка select
    BODY.on('click', 'button', function () {
        if ($(this).attr('id') === "clearSelectDocumentsPensioner") {
            SELECTIZEPENSIONER[0].selectize.clear();
        }
        if ($(this).attr('id') === "clearSelectDocumentsCarer") {
            SELECTIZECARER[0].selectize.clear();
        }
    });

    //выбор причины
    BODY.on('change', '#selectReasonsForOverpayments', function () {
        ajaxReasonsForOverpayments($("#selectReasonsForOverpayments").val());
        $(".addSpecificationOfTheReasonsForOverpayments").removeClass("d-none");
    });

    BODY.on('click', 'button', function () {

        //кнопка удалить
        if ($(this).hasClass("deleteSpecificationOfTheReasonsForOverpaymentsBtn")) {
            let id = $(this).attr('name');
            if (confirm("Удалить запись с ID = " +
                $("button.deleteSpecificationOfTheReasonsForOverpaymentsBtn[name='" + id + "']").parents('tr').children('td').eq(0).text())) {
                $.ajax({
                    url: "/overpayment/referenceBook/specificationOfTheReasonsForOverpayments/" + id,
                    cache: false,
                    processData: false,
                    type: 'DELETE',
                    success: function () {
                        initialToats("Успешно!", "Данные удалены!" , "success").show();
                        ajaxReasonsForOverpayments($("#selectReasonsForOverpayments").val());
                    },
                    error: function (response) {
                        initialToats("Ошибка!", response.responseJSON.message , "err").show();
                    }
                });
            }
        }

        //изменить модаль
        if ($(this).hasClass("updateSpecificationOfTheReasonsForOverpaymentsBtn")) {
            $("#idModalSpecificationOfTheReasonsForOverpayments").val($(this).parents('tr').children('td').eq(0).text());
            $("#nameModalSpecificationOfTheReasonsForOverpayments").val($(this).parents('tr').children('td').eq(1).text());
            SELECTIZEPENSIONER[0].selectize.setValue($(this).parents('tr').children('td').eq(2).children('a').attr("data-pensioner-document-id"));
            SELECTIZECARER[0].selectize.setValue($(this).parents('tr').children('td').eq(3).children('a').attr("data-carer-document-id"));
            $("#updateOrAddSpecificationOfTheReasonsForOverpayments").text('Изменить');
        }

        //Добавить открыть модаль
        if ($(this).hasClass("addSpecificationOfTheReasonsForOverpayments")) {
            $("#idModalSpecificationOfTheReasonsForOverpayments").val("");
            $("#nameModalSpecificationOfTheReasonsForOverpayments").val("");
            SELECTIZEPENSIONER[0].selectize.clear();
            SELECTIZECARER[0].selectize.clear();
            $("#updateOrAddSpecificationOfTheReasonsForOverpayments").text('Добавить');
        }

        //изменить
        if ($(this).attr('id') === "updateOrAddSpecificationOfTheReasonsForOverpayments") {
            let id = $("#idModalSpecificationOfTheReasonsForOverpayments").val();
            let specificationOfTheReasonsForOverpayments = $("#nameModalSpecificationOfTheReasonsForOverpayments").val();
            let data = JSON.stringify({
                'id': id,
                'idReasonsForOverpayment': $("#selectReasonsForOverpayments").val(),
                'specificationOfTheReasonsForOverpayments': specificationOfTheReasonsForOverpayments,
                'documentPensioner': {
                    'id': $("#selectDocumentsPensioner").val()
                },
                'documentCarer': {
                    'id': $("#selectDocumentsCarer").val()
                }
            });

            let type = $("#updateOrAddSpecificationOfTheReasonsForOverpayments").text() === "Изменить" ? 'PUT' : 'POST';

            $('#tableSpecificationOfTheReasonsForOverpayments tbody').html('<tr><th>' + getSpinner() + '</th><td></td><td></td><td></td></tr>');
            $.ajax({
                url: "/overpayment/referenceBook/specificationOfTheReasonsForOverpayments",
                data: data,
                cache: false,
                processData: false,
                contentType: "application/json",
                type: type,
                success: function () {
                    initialToats("Успешно!", $("#updateOrAddSpecificationOfTheReasonsForOverpayments").text()==="Изменить"?"Данные изменены!":"Данные сохранены!" , "success").show();
                    ajaxReasonsForOverpayments($("#selectReasonsForOverpayments").val());
                },
                error: function (response) {
                    initialToats("Ошибка!", response.responseJSON.message , "err").show();
                }
            });
        }
    });

});

function ajaxReasonsForOverpayments(idReasons) {
    getSpinnerTable("tableSpecificationOfTheReasonsForOverpayments");
    $.ajax({
        url: "/overpayment/referenceBook/reasonsForOverpayments/" + idReasons,
        data: "",
        cache: false,
        processData: false,
        contentType: "application/json",
        dataType: 'json',
        type: 'GET',
        success: function (response) {
            let trHTML = '';
            $('#tableSpecificationOfTheReasonsForOverpayments tbody').html("");
            $.each(response.specificationOfTheReasonsForOverpaymentsDtos, function (i, item) {
                let documentPensioner = "";
                if (item.documentPensioner.id !== null) {
                    documentPensioner = '<a class="btn btn-link" data-pensioner-document-id="'+item.documentPensioner.id+'" href="/overpayment/documents/download/' + item.documentPensioner.id + '">' + item.documentPensioner.nameFile + '</a>';
                }
                let documentCarer = "";
                if (item.documentCarer.id !== null) {
                    documentCarer = '<a class="btn btn-link" data-carer-document-id="'+item.documentCarer.id+'" href="/overpayment/documents/download/' + item.documentCarer.id + '">' + item.documentCarer.nameFile + '</a>';
                }
                trHTML +=
                    '<tr>' +
                    '<th>' + (+i + 1) + '</th>' +
                    '<td>' + item.id + '</td>' +
                    '<td>' + item.specificationOfTheReasonsForOverpayments + '</td>' +
                    '<td>' +
                    documentPensioner +
                    '</td>' +
                    '<td>' +
                    documentCarer +
                    '</td>' +
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
        error: function (response) {
            initialToats("Ошибка!", response.responseJSON.message , "err").show();
        }
    });
}
