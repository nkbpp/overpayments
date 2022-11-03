$(document).ready(function () {

    let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationDocuments");
    ajaxDocumentsAll(params, "");

    $("body").on('change','#col',function(){
        let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationDocuments");
        ajaxDocumentsAll(params, "");
    });

    $("body").on('click', 'button', function () {

        //кнопка удалить
        if ($(this).hasClass("deleteDocumentsBtn")) {
            let id = $(this).attr('name');
            if(confirm("Удалить запись с ID = " +
                $("button.deleteDocumentsBtn[name='" + id + "']").parents('tr').children('td').eq(0).text())) {
                $.ajax({
                    url: "/overpayment/documents/" + id,
                    cache: false,
                    processData: false,
                    type: 'DELETE',
                    /*beforeSend: function (xhr) {
                    xhr.setRequestHeader($('#_csrf').attr('content'),
                             $('#_csrf_header').attr('content'));
                    },*/
                    success: function (response) {
                        $("button.deleteDocumentsBtn[name='" + id + "']").parents('tr').remove()
                    },
                    error: function (jqXHR, textStatus) {
                    }
                });
            }

        }
        //изменить модаль
        if ($(this).hasClass("updateDocumentsBtn")) {
            $("#formDocuments #id").val($(this).parents('tr').children('td').eq(0).text());
            $("#formDocuments #nameFile").val($(this).parents('tr').children('td').eq(1).text());
            $("#formDocuments #dokument").val("");
            $("#updateOrAddDocuments").text('Изменить');
        }
        //Добавить модаль
        if ($(this).hasClass("addDocuments")) {
            $("#formDocuments #id").val("");
            $("#formDocuments #nameFile").val("");
            $("#formDocuments #dokument").val("");
            $("#updateOrAddDocuments").text('Добавить');
        }

        //изменить
        if ($(this).attr('id')==="updateOrAddDocuments") {

            let formData = new FormData();
            let fileField = document.querySelector('input[type="file"]');

            formData.append('id', $("#formDocuments #id").val());
            formData.append('nameFile', $("#formDocuments #nameFile").val());
            formData.append('dokument', fileField.files[0]);

            //let param = new FormData(document.getElementById("formDocuments"));

            let type = $("#updateOrAddDocuments").text()==="Изменить"?'PUT':'POST';

            getSpinnerTable("tableDocuments");
            //$('#tableDocuments tbody').html('<tr><th>' + getSpinner() + '</th><td></td><td></td><td></td></tr>');
            $.ajax({
                url: "/overpayment/documents",//?" + params,
                data: formData,
                cache: false,// кэш и прочие настройки писать именно так (для файлов) (связано это с кодировкой и всякой лабудой)
                processData: false, // для передачи картинки(файла) нужно false
                contentType: false, // Так jQuery скажет серверу что это строковой запрос, нужно указать тип контента false для картинки(файла)
                //contentType: "multipart/form-data",
                type: type,
                /*beforeSend: function (xhr) {
                    xhr.setRequestHeader($('#_csrf').attr('content'),
                                         $('#_csrf_header').attr('content'));
                },*/
                success: function (response) {
                    //$("#modalDocuments").hide();
                    let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationDocuments");
                    ajaxDocumentsAll(params);
                },
                error: function (jqXHR, textStatus) {
                    alert("err " + textStatus + " !!! " +  jqXHR)
                    $('#tableDocuments tbody').html("");
                }
            });
        }
    });

    $("body").on('click', 'a', function () {
        //переключатели страниц pagination
        if ($(this).parents("#paginationDocuments").attr("id") === "paginationDocuments") {
            clickPagination($(this), "#paginationDocuments");
            let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationDocuments");
            ajaxDocumentsAll(params, "");
        }
    });
});

function ajaxDocumentsAll(params){
    getSpinnerTable("tableDocuments");
    //$('#tableDocuments tbody').html('<tr><th>' + getSpinner() + '</th><td></td><td></td><td></td></tr>');
    $.ajax({
        url: "/overpayment/documents/All?" + params,
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
            $('#tableDocuments tbody').html("");
            $.each(response, function (i, item) {
                trHTML +=
                    '<tr>' +
                    '<th>' + (+i+1) + '</th>' +
                    '<td>' + item.id + '</td>' +
                    //'<td>' + item.nameFile + '</td>' +
                    '<td>' +
                    '<a className="btn btn-link" href="/overpayment/documents/download/'+ item.id +'">' + item.nameFile + '</a>' +
                    '</td>' +
                    '<td>' +
                    '<div class="btn-group" role="group">' +
                    '<button ' +
                    'class="btn  btn-secondary btn-sm deleteDocumentsBtn" ' +
                    'type="button" ' +
                    'name="' + item.id + '" ' +
                    '>Удалить</button>' +

                    '<button ' +
                    'class="btn  btn-secondary btn-sm updateDocumentsBtn mx-1" ' +
                    'data-bs-toggle="modal" ' +
                    'data-bs-target="#modalDocuments" ' +
                    'type="button" ' +
                    'name="' + item.id + '" ' +
                    '>Изменить</button>' +
                    '</div>' +

                    '</td>' +
                    '</tr>';
            });
            $('#tableDocuments').append(trHTML);
        },
        error: function (jqXHR, textStatus) {
            alert("ERROR")
            $('#tableDocuments tbody').html("");
        }
    });

}
