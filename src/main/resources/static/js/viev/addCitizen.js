$(document).ready(function () {

    let BODY = $("body");

    BODY.on('click', 'a', function () {
        if ($(this).hasClass("pickPensioner")) {
            $('#divTableFindPensioner').addClass("d-none");

            let arrTd = $(this).parents('tr').children('td');
            $("#surname").val(arrTd.eq(1).text());
            $("#name").val(arrTd.eq(2).text());
            $("#patronymic").val(arrTd.eq(3).text());
            $("#adrreg").val(arrTd.eq(4).text());
            $("#tel").val(arrTd.eq(5).text());
            findOverpayments($(this).attr("id"));
        }
    });

    BODY.on('click', 'button', function () {

        //поиск по снилс
        if ($(this).hasClass("btnFindCitizenSNILS")) {
            //все чистим
            $("#surname").val("");
            $("#name").val("");
            $("#patronymic").val("");
            $("#adrreg").val("");
            $("#tel").val("");
            $('#tableOverpayments tbody').html("");
            let btnAddCitizen = $('#btnAddCitizen');
            btnAddCitizen.removeAttr("name");
            //поиск
            btnAddCitizen.attr("disabled","true");
            findCitizen();

        }


        //ДОБАВИТЬ
        if ($(this).attr('id') === "btnAddCitizen") {

            let id_ros = $('#btnAddCitizen').attr("name");
            let addPensioner = document.getElementById("formAddPensioner");
            let fd = new FormData(addPensioner);
            let object = {};
            fd.forEach((value, key) => object[key] = value);
            object['id_ros'] = id_ros;
            let data = JSON.stringify(object);
            //console.log(data)
            $.ajax({
                url: "/overpayment/pensioner",
                data: data,
                cache: false,
                processData: false,
                contentType: "application/json",
                type: "POST",
                /*beforeSend: function (xhr) {
                xhr.setRequestHeader($('#_csrf').attr('content'),
                    $('#_csrf_header').attr('content'));
                },*/
                success: function () {
                    document.location.href = '/overpayment/vievInformationOverpayments/IdRos/' + id_ros;
                },
                error: function (jqXHR) {
                    alert("Error: " + jqXHR.responseText + " !!! ")
                }
            });
        }

    });

})

function findCitizen() {
    let snils = $("#snils").val();
    let json = JSON.stringify({'snils': snils});


    $.ajax({
        url: "/overpayment/ros/findPensionerBySnils",
        //url: "/overpayment/ros/findAllBySnils", //несколько результатов 162-181-171 38
        data: json,
        cache: false,
        processData: false,
        contentType: "application/json",
        type: 'POST',
        /*beforeSend: function (xhr) {
            xhr.setRequestHeader($('#_csrf').attr('content'),
                                 $('#_csrf_header').attr('content'));
        },*/
        success: function (response) {
            if (response.length > 1) {

                let trHTML = '';
                let tableFindPensionerBody = $('#tableFindPensioner tbody');
                tableFindPensionerBody.html("");
                $.each(response, function (i, item) {
                    trHTML +=
                        '<tr>' +
                        '<th>' + (+i + 1) + '</th>' +
                        '<td>' + replaceNull(item.snils) + '</td>' +
                        '<td>' + replaceNull(item.surname) + '</td>' +
                        '<td>' + replaceNull(item.name) + '</td>' +
                        '<td>' + replaceNull(item.patronymic) + '</td>' +
                        '<td>' + replaceNull(item.adrreg) + '</td>' +
                        '<td>' + replaceNull(item.tel) + '</td>' +
                        '<td><a class="pickPensioner" href="#" id="' + item.id + '">Выбрать</a></td>' +
                        '</tr>';
                });
                tableFindPensionerBody.append(trHTML);
                $('#divTableFindPensioner').removeClass("d-none");

            } else if (response.length === 1) {
                $("#surname").val(response[0].surname);
                $("#name").val(response[0].name);
                $("#patronymic").val(response[0].patronymic);
                $("#adrreg").val(response[0].adrreg);
                $("#tel").val(response[0].tel);

                //$('#tableOverpayments tbody').html('<tr><th>' + getSpinner() + '</th><td></td><td></td><td></td><td></td><td></td></tr>');//спинер
                getSpinnerTable("tableOverpayments")
                findOverpayments(response[0].id);
            } else {
                alert("Нет результатов")
            }

        },
        error: function () {
            alert("ERROR");
        }
    });
}


function findOverpayments(id) {

    $.ajax({
        url: "/overpayment/ros/overpayments/" + id,
        cache: false,
        processData: false,
        contentType: "application/json",
        type: 'GET',
        /*beforeSend: function (xhr) {
            xhr.setRequestHeader($('#_csrf').attr('content'),
                                 $('#_csrf_header').attr('content'));
        },*/
        success: function (response) {
            let trHTML = '';
            let tableOverpaymentsBody = $('#tableOverpayments tbody');
            tableOverpaymentsBody.html("");
            $.each(response, function (i, item) {
                trHTML +=
                    '<tr>' +
                    '<th>' + (+i + 1) + '</th>' +
                    '<td>' + replaceNull(item.doc) + '</td>' +
                    '<td>' + replaceNull(item.sroks) + '</td>' +
                    '<td>' + replaceNull(item.srokpo) + '</td>' +
                    '<td>' + replaceNull(item.spe) + '</td>' +
                    '<td>' + replaceNull(item.close_date) + '</td>' +
                    //'<td><a href="#" id="' + item.id + '">Выбрать</a></td>' +
                    '</tr>';
            });
            tableOverpaymentsBody.append(trHTML);
            let btnAddCitizen = $('#btnAddCitizen');
            btnAddCitizen.attr("name", id);
            btnAddCitizen.removeAttr("disabled");
        },
        error: function () {
            alert("ERROR");
        }
    });

}
