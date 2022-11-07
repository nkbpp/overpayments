$(document).ready(function () {

    let BODY = $("body");

    let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationDepartment");
    ajaxDepartmentAll(params, "");

    BODY.on('change','#col',function(){
        let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationDepartment");
        ajaxDepartmentAll(params, "");
    });

    BODY.on('click', 'button', function () {

        //кнопка удалить
        if ($(this).hasClass("deleteDepartmentBtn")) {
            let id = $(this).attr('name');
            if(confirm("Удалить запись с ID = " +
                $("button.deleteDepartmentBtn[name='" + id + "']").parents('tr').children('td').eq(0).text())) {
                $.ajax({
                    url: "/overpayment/referenceBook/department/" + id,
                    cache: false,
                    processData: false,
                    type: 'DELETE',
                    /*beforeSend: function (xhr) {
                    xhr.setRequestHeader($('#_csrf').attr('content'),
                             $('#_csrf_header').attr('content'));
                    },*/
                    success: function () {
                        $("button.deleteDepartmentBtn[name='" + id + "']").parents('tr').remove()
                    },
                    error: function (jqXHR, textStatus) {
                    }
                });
            }

        }
        //изменить модаль
        if ($(this).hasClass("updateDepartmentBtn")) {
            $("#idModalDepartment").val($(this).parents('tr').children('td').eq(0).text());
            $("#nameModalDepartment").val($(this).parents('tr').children('td').eq(1).text());
            $("#updateOrAddDepartment").text('Изменить');
        }
        //Добавить модаль
        if ($(this).hasClass("addDepartment")) {
            $("#idModalDepartment").val("");
            $("#nameModalDepartment").val("");
            $("#updateOrAddDepartment").text('Добавить');
        }

        //изменить
        if ($(this).attr('id')==="updateOrAddDepartment") {
            let id = $("#idModalDepartment").val();
            let department = $("#nameModalDepartment").val();
            let data = JSON.stringify({ 'id': id, 'name': department });

            let type = $("#updateOrAddDepartment").text()==="Изменить"?'PUT':'POST';

            $('#tableDepartment tbody').html('<tr><th>' + getSpinner() + '</th><td></td><td></td><td></td></tr>');
            $.ajax({
                url: "/overpayment/referenceBook/department",
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
                    //$("#modalDepartment").hide();
                    let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationDepartment");
                    ajaxDepartmentAll(params);
                },
                error: function (jqXHR, textStatus) {
                    alert("err " + textStatus + " !!! " +  jqXHR)
                    $('#tableDepartment tbody').html("");
                }
            });
        }
    });

    BODY.on('click', 'a', function () {
        //переключатели страниц pagination
        if ($(this).parents("#paginationDepartment").attr("id") === "paginationDepartment") {
            clickPagination($(this), "#paginationDepartment");
            let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationDepartment");
            ajaxDepartmentAll(params, "");
        }
    });
});

function ajaxDepartmentAll(params){
    $('#tableDepartment tbody').html('<tr><th>' + getSpinner() + '</th><td></td><td></td><td></td></tr>');
    $.ajax({
        url: "/overpayment/referenceBook/department/All?" + params,
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
            $('#tableDepartment tbody').html("");
            $.each(response, function (i, item) {
                trHTML +=
                    '<tr>' +
                    '<th>' + (+i+1) + '</th>' +
                    '<td>' + item.id + '</td>' +
                    '<td>' + item.name + '</td>' +
                    '<td>' +
                    '<div class="btn-group" role="group">' +
                    '<button ' +
                    'class="btn  btn-secondary btn-sm deleteDepartmentBtn" ' +
                    'type="button" ' +
                    'name="' + item.id + '" ' +
                    '>Удалить</button>' +

                    '<button ' +
                    'class="btn  btn-secondary btn-sm updateDepartmentBtn mx-1" ' +
                    'data-bs-toggle="modal" ' +
                    'data-bs-target="#modalDepartment" ' +
                    'type="button" ' +
                    'name="' + item.id + '" ' +
                    '>Изменить</button>' +


                    '</div>' +
                    '</td>' +
                    '</tr>';
            });
            $('#tableDepartment').append(trHTML);
        },
        error: function () {
            alert("ERROR")
            $('#tableDepartment tbody').html("");
        }
    });
}
