/*let SELECTIZEDSTRUCTURAL;*/
let SELECTIZEREASONS;
let SELECTIZESPECIFICATIONREASONS;
let CHANGE = true;
$(document).ready(function () {

    $('.datepicker').datepicker({
        format: 'dd.mm.yyyy',
        language: "ru"
    });

    let BODY = $("body");

    $("#numDistrict").selectize({
        create: true
    });
    /*    SELECTIZEDSTRUCTURAL = $("#structuralSubdivision").selectize({
            create: true
        });*/
    SELECTIZEREASONS = $("#selectReasonsForOverpayments").selectize({
        create: true
    });
    SELECTIZESPECIFICATIONREASONS = $("#selectSpecificationOfTheReasonsForOverpayments").selectize({
        create: true
    });
    clearSELECTIZESPECIFICATIONREASONS();

    //выбор причины
    BODY.on('change', '#selectReasonsForOverpayments', function () {
        clearSELECTIZESPECIFICATIONREASONS();
        let selectReasonsForOverpayments = $("#selectReasonsForOverpayments").val();
        if (selectReasonsForOverpayments !== '' && CHANGE) {
            $.ajax({
                url: "/overpayment/referenceBook/reasonsForOverpayments/" + selectReasonsForOverpayments,
                data: "",
                cache: false,
                processData: false,
                contentType: "application/json",
                dataType: 'json',
                type: 'GET',
                success: function (response) {
                    $.each(response.specificationOfTheReasonsForOverpaymentsDtos, function (i, item) {
                        SELECTIZESPECIFICATIONREASONS[0].selectize.addOption({
                            value: item.id,
                            text: item.specificationOfTheReasonsForOverpayments
                        });
                    });
                },
                error: function (response) {
                    initialToats("Ошибка!", response.responseJSON.message, "err").show();
                }
            });
        }
        CHANGE = true;
    });

    //Чистка select
    BODY.on('click', 'button', function () {
        if ($(this).attr('id') === "clearStructuralSubdivision") {
            SELECTIZEDTRUCTURAL[0].selectize.clear();
        }
        if ($(this).attr('id') === "clearReasonsForOverpayments") {
            SELECTIZEREASONS[0].selectize.clear();
            clearSELECTIZESPECIFICATIONREASONS();
        }
        if ($(this).attr('id') === "clearSelectSpecificationOfTheReasonsForOverpayments") {
            SELECTIZESPECIFICATIONREASONS[0].selectize.clear();
        }
    });

    BODY.on('click', 'button', function () {
        if ($(this).attr('id') === "btnFindStatistics") {

            let myform = document.getElementById("formStatistics");
            let fd = new FormData(myform);
            let object = {};
            fd.forEach((value, key) => object[key] = value);
            let json = JSON.stringify(object);

            console.log(json)

            getSpinnerTable("tableStatistics");
            $.ajax({
                url: "/overpayment/statistics/find",
                data: json,
                cache: false,
                processData: false,
                contentType: "application/json",
                type: 'POST',
                success: function (response) {
                    console.log("r = " + response)
                    $('#tableStatistics tbody').html("");
                    var trHTML = '';

                    $.each(response, function (i, item) {
                        let trHTML2 = "";

                        //console.log("i = " + item.statistics[i])
                        for (let i2 = 0; i2 < item.statistics.length; i2++) {

                            if(i2 !== 0) {
                                trHTML2 += '<tr>' +
                                '<th></th>' +
                                '<td></td>';
                            }
                            trHTML2 +=
                                '<td>' +
                                (item.statistics[i2].reason === "" ? "Причина не указана" :
                                replaceNull(item.statistics[i2].reason)) +
                                '</td>' +
                                '<td>' + replaceNull(item.statistics[i2].v1) + '</td>' +
                                '<td>' + replaceNull(item.statistics[i2].d1) + '</td>' +
                                '</tr>';
                        };

                        //console.log(trHTML2)

                        trHTML +=
                            '<tr>' +
                            '<th>' + (+i + 1) + '</th>' +
                            '<td>' + replaceNull(item.district.name) + '</td>' +
                            trHTML2;
                    });
                    $('#tableStatistics').append(trHTML);
                },
                error: function (response) {
                    initialToats("Ошибка!", response.responseJSON.message, "err").show();
                    $('#tablefindresult tbody').html("");
                }
            });
        }
    });

    //обработка ENTER
    /*    $("#formFindPensioner").on("submit", function(event){
            event.preventDefault();
            $("#formFindPensioner button").click();
        })

        BODY.on('click', 'button', function () {

            // кнопка поиск по снилс
            if ($(this).attr('id') === "btnFindPensionerSNILS") {
                if(correctInputs()){findPensioner("SNILS")}
            }

            // кнопка поиск по фио
            if ($(this).attr('id') === "btnFindPensionerFIO") {
                if(correctInputs()){findPensioner("FIO")}
            }

            // кнопка поиск по району
            if ($(this).attr('id') === "btnFindPensionerDistrict") {
                if(correctInputs()){findPensioner("District")}
            }

        });

        //caption в таблице
        BODY.on('change', '#col', function () {
            // кнопка поиск по снилс
            if ($("#btnFindPensionerSNILS").length > 0) {
                if(correctInputs()){findPensioner("SNILS")}
            }

            // кнопка поиск по фио
            if ($("#btnFindPensionerFIO").length > 0) {
                if(correctInputs()){findPensioner("FIO")}
            }

            // кнопка поиск по району
            if ($("#btnFindPensionerDistrict").length > 0) {
                if(correctInputs()){findPensioner("District")}
            }
        });

        BODY.on('click', 'a', function () {
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

            /!*УДАЛИТЬ*!/
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
                        success: function () {
                            //поиск добавленного id
                            $(".deletePensioner[id=" + id + "]").parents('tr').remove();
                        },
                        error: function (response) {
                            initialToats("Ошибка!", response.responseJSON.message , "err").show();
                        }
                    });
                }
            }

        });*/

});

/*
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
        success: function (response) {
            var trHTML = '';
            $('#tablefindresult tbody').html("");
            $.each(response, function (i, item) {
                let col = $("#col");
                trHTML +=
                    '<tr>' +
                    '<th>' + (+col.val() * +activeList("#paginationFindPensioner") - +col.val() + (+i + 1)) + '</th>' +
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
        error: function (response) {
            initialToats("Ошибка!", response.responseJSON.message , "err").show();
            $('#tablefindresult tbody').html("");
        }
    });
}

function correctInputs() {
    let snils = $("#snils");
    let dateOfBirth = $("#dateOfBirth");
    let numDistrict = $("#numDistrict");
    if((snils.length===1 && snils.val()!=="") ||
        (dateOfBirth.length===1 &&  dateOfBirth.val()!=="") ||
        (numDistrict.length===1 &&  numDistrict.val()!=="")){
        return true;
    }
    else {
        initialToats("Ошибка!", "Заполните необходимые поля!", "err").show();
        return false;
    }
}*/

//для погашения и удержания
function clearSELECTIZESPECIFICATIONREASONS() {
    let items = SELECTIZESPECIFICATIONREASONS[0].selectize.options;
    for (let i in items) {
        SELECTIZESPECIFICATIONREASONS[0].selectize.removeOption(items[i].value);
    }
}