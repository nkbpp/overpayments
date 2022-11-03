$(document).ready(function () {

    $("#numDistrict").selectize({
        create: true,
        //sortField: "text",
    });

    $("body").on('click', 'button', function () {

        // кнопка поиск по снилс
        if ($(this).attr('id') === "btnFindPensionerSNILS") {
            if(correctInputs()){findPensioner("SNILS")};
        }

        // кнопка поиск по фио
        if ($(this).attr('id') === "btnFindPensionerFIO") {
            if(correctInputs()){findPensioner("FIO")};
        }

        // кнопка поиск по району
        if ($(this).attr('id') === "btnFindPensionerDistrict") {
            if(correctInputs()){findPensioner("District")};
        }

    });

    //caption в таблице
    $("body").on('change', '#col', function () {
        // кнопка поиск по снилс
        if ($("#btnFindPensionerSNILS").length > 0) {
            if(correctInputs()){findPensioner("SNILS")};
        }

        // кнопка поиск по фио
        if ($("#btnFindPensionerFIO").length > 0) {
            if(correctInputs()){findPensioner("FIO")};
        }

        // кнопка поиск по району
        if ($("#btnFindPensionerDistrict").length > 0) {
            if(correctInputs()){findPensioner("District")};
        }
    });

    $("body").on('click', 'a', function () {
        //переключатели страниц pagination
        if ($(this).parents("#paginationFindPensioner").attr("id") === "paginationFindPensioner") {
            if(correctInputs()){
                clickPagination($(this), "#paginationFindPensioner");
                let howFind;
                if ($("#btnFindPensionerDistrict").length > 0) {
                    howFind = "District";
                }
                if ($("#btnFindPensionerSNILS").length > 0) {
                    howFind = "SNILS";
                }
                if ($("#btnFindPensionerFIO").length > 0) {
                    howFind = "FIO";
                }
                findPensioner(howFind);
            }
        }

        if ($(this).hasClass("linkInformationOverpayments")) {
            document.location.href = '/overpayment/vievInformationOverpayments/' + $(this).attr("id");
        }

        /*УДАЛИТЬ*/
        if ($(this).hasClass("deletePensioner")) {
            if (confirm("Удалить пенсионера с СНИЛС: " +
                $(this).parents('tr').children('td').eq(0).text())) {
                let id = $(this).attr("id");
                $.ajax({
                    url: "/overpayment/pensioner/" + id,
                    cache: false,
                    processData: false,
                    contentType: "application/json",
                    type: 'DELETE',
                    /*beforeSend: function (xhr) {
                        xhr.setRequestHeader($('#_csrf').attr('content'),
                                             $('#_csrf_header').attr('content'));
                    },*/
                    success: function (response) {
                        //поиск добавленного id

                        $(".deletePensioner[id=" + id + "]").parents('tr').remove();
                    },
                    error: function (jqXHR, textStatus) {
                        alert("ERROR");
                    }
                });
            }
        }

    });

});

function findPensioner(howFind) {
    let myform = document.getElementById("formFindPensioner");
    let fd = new FormData(myform);
    let object = {};
    fd.forEach((value, key) => object[key] = value);
    let json = JSON.stringify(object);
    let params = "col=" + $("#col").val() + "&pagination=" + activeList("#paginationFindPensioner");

    getSpinnerTable("tablefindresult");
    $.ajax({
        url: "/overpayment/findPensioner" + howFind + "?" + params,
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
            var trHTML = '';
            $('#tablefindresult tbody').html("");
            $.each(response, function (i, item) {
                trHTML +=
                    '<tr>' +
                    '<th>' + (+$("#col").val() * +activeList("#paginationFindPensioner") - +$("#col").val() + (+i + 1)) + '</th>' +
                    '<td>' + replaceNull(item.snils) + '</td>' +
                    '<td>' + replaceNull(item.surname) + '</td>' +
                    '<td>' + replaceNull(item.name) + '</td>' +
                    '<td>' + replaceNull(item.patronymic) + '</td>' +
                    '<td>' + replaceNull(item.rdat) + '</td>' +
                    '<td>' + replaceNull(item.adrreg) + '</td>' +
                    '<td><a class="linkInformationOverpayments" href="#" id="' + item.id + '">Выбрать</a></td>' +
                    '<td><a class="deletePensioner" href="#" id="' + item.id + '">Удалить</a></td>' +
                    '</tr>';
            });
            $('#tablefindresult').append(trHTML);
        },
        error: function (jqXHR, textStatus) {
            alert("ERROR")
            $('#tablefindresult tbody').html("");
        }
    });
}


function correctInputs() {
    if(($("#snils").length===1 && $("#snils").val()!=="") ||
        ($("#dateOfBirth").length===1 &&  $("#dateOfBirth").val()!=="") ||
        ($("#numDistrict").length===1 &&  $("#numDistrict").val()!=="")){
        return true;
    }
    else {
        alert("Заполните необходимые поля!");
        return false;
    }
}