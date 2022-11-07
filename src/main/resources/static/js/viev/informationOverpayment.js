let ARRSELECTIZE;
$(document).ready(function () {

    ARRSELECTIZE = $("form select").selectize({
        create: true,
        //sortField: "text",
    });

    let BODY = $("body");

    let citizenData = $("#nav-CitizenData-tab");
    let ID = citizenData.attr("data-id");
    let findLink = "findPensionerById";
    if (ID === "") {
        ID = citizenData.attr("data-id-ros");
        findLink = "findPensionerByIdRos";
    }

    //Чистка select
    BODY.on('click', 'button', function () {
        if ($(this).attr('id') === "clearReasonsForOverpayments") {
            ARRSELECTIZE[0].selectize.clear();
        }
        if ($(this).attr('id') === "clearSelectSpecificationOfTheReasonsForOverpayments") {
            ARRSELECTIZE[1].selectize.clear();
        }
        if ($(this).attr('id') === "clearStructuralSubdivision") {
            ARRSELECTIZE[2].selectize.clear();
        }
    });

    $('#nav-OverpaymentData-tab').attr("disabled","true").addClass("text-muted");

    //первоначальная загрузка данных о пенсионере
    $.ajax({
        url: "/overpayment/" + findLink + "/" + ID,
        cache: false,
        processData: false,
        contentType: "application/json",
        type: 'POST',
        /*beforeSend: function (xhr) {
            xhr.setRequestHeader($('#_csrf').attr('content'),
                                 $('#_csrf_header').attr('content'));
        },*/
        success: function (response) {
            citizenData.attr("data-id", response.id);
            citizenData.attr("data-id-ros", response.id_ros);
            $("#snils").val(response.snils);
            $("#surname").val(response.surname);
            $("#name").val(response.name);
            $("#patronymic").val(response.patronymic);
            $("#adrreg").val(response.adrreg);
            $("#tel").val(response.tel);

            $('#nav-OverpaymentData-tab').removeAttr("disabled").removeClass("text-muted");
        },
        error: function () {
            alert("ERROR");
        }
    });

    BODY.on('click', 'button', function () {

        if ($(this).attr('id') === "btnUpdatePensioner") {
            let id = $("#nav-CitizenData-tab").attr("data-id");
            let updatePensioner = document.getElementById("formCitizenData");
            let fd = new FormData(updatePensioner);
            let object = {};
            fd.forEach((value, key) => object[key] = value);
            object['id'] = id;
            let data = JSON.stringify(object);
            $.ajax({
                url: "/overpayment/pensioner/" + id,
                data: data,
                cache: false,
                processData: false,
                contentType: "application/json",
                type: "PUT",
                /*beforeSend: function (xhr) {
                xhr.setRequestHeader($('#_csrf').attr('content'),
                    $('#_csrf_header').attr('content'));
                },*/
                success: function () {
                    alert("Данные изменены")
                    //document.location.href = '/overpayment/vievInformationOverpayments'
                },
                error: function (jqXHR, textStatus) {
                    alert("err " + textStatus + " !!! " + jqXHR)
                }
            });
        }

        //загрузка переплат
        if($(this).attr("id") === "nav-OverpaymentData-tab"){
            let id = $("#nav-CitizenData-tab").attr("data-id-ros");
            getSpinnerTable("tableOverpayments");
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
                    let tableBody = $('#tableOverpayments tbody');
                    tableBody.html("");
                    $.each(response, function (i, item) {
                        trHTML +=
                            '<tr>' +
                            '<th>' + (+i + 1) + '</th>' +
                            '<td>' + replaceNull(item.doc) + '</td>' +
                            '<td>' + replaceNull(item.sroks) + '</td>' +
                            '<td>' + replaceNull(item.srokpo) + '</td>' +
                            '<td>' + replaceNull(item.spe) + '</td>' +
                            '<td>' + replaceNull(item.close_date) + '</td>' +
                            '<td><a class="pickOverpayment" href="#" name="' + item.isId + '">Выбрать</a></td>' +
                            '</tr>';
                    });
                    tableBody.append(trHTML);
                    $("#formOverpaymentData").addClass("d-none");
                    $("#formFindCarer").addClass("d-none");
                    $("#formFindCarer button.btnFindCitizenSNILS").removeClass("d-none");
                    $("#formFindCarer #snils").val('');
                    $('#formFindCarer .btnFindCitizenSNILS').removeAttr("name");
                },
                error: function () {
                    alert("ERROR");
                }
            });
        }

    })

    BODY.on('click', 'a', function () {
        //выбор переплаты
        if($(this).hasClass("pickOverpayment")){
            $('#tableOverpayments tr.table-primary').removeClass("table-primary");
            let tr = $(this).parents('tr');
            tr.addClass("table-primary");

            let idIs = $(this).attr("name");

            clearInputOverpaymentData();
            //данные из базы overpayment
            $.ajax({
                url: "/overpayment/informationOverpayment/" + idIs,
                //data: data,
                cache: false,
                processData: false,
                contentType: "application/json",
                type: "GET",
                /*beforeSend: function (xhr) {
                xhr.setRequestHeader($('#_csrf').attr('content'),
                    $('#_csrf_header').attr('content'));
                },*/
                success: function (response) {
                    if(response===""){ //данных в базе overpayment нет
                        $("#btnSaveOverpaymentData").text("Сохранить").attr("data-type","save");
                    } else { //данные в базе overpayment есть
                        if(response.carer !== null) {
                            $("#formFindCarer #snils").val(replaceNull(response.carer.snils));
                            $("#formFindCarer #surname").val(replaceNull(response.carer.surname));
                            $("#formFindCarer #name").val(replaceNull(response.carer.name));
                            $("#formFindCarer #patronymic").val(replaceNull(response.carer.patronymic));
                            $("#formFindCarer #adrreg").val(replaceNull(response.carer.adrreg));
                            $("#formFindCarer #tel").val(replaceNull(response.carer.tel));
                            $('#formFindCarer .btnFindCitizenSNILS').attr("name",replaceNull(response.carer.id_ros));
                        }

                        if(response.reasonsForOverpaymentsDto !== null){
                            ARRSELECTIZE[0].selectize.setValue(response.reasonsForOverpaymentsDto.id);
                        }
                        if(response.specificationOfTheReasonsForOverpaymentsDto !== null){
                            ARRSELECTIZE[1].selectize.setValue(response.specificationOfTheReasonsForOverpaymentsDto.id);
                        }
                        if(response.departmentDto !== null){
                            ARRSELECTIZE[2].selectize.setValue(response.departmentDto.id);
                        }
                        $("#comment").val(replaceNull(response.comment));
                        if(response.isApplicationForVoluntaryRedemption===true){
                            $("#isApplicationForVoluntaryRedemption1").prop('checked', true);
                        } else {
                            $("#isApplicationForVoluntaryRedemption2").prop('checked', true);
                        }

                        $("#btnSaveOverpaymentData").text("Изменить").attr("data-type","update");
                    }

                    getSpinnerTable("tableUder");
                    //данные из базы рос
                    $.ajax({
                        url: "/overpayment/ros/overpayment/" + idIs,
                        cache: false,
                        processData: false,
                        contentType: "application/json",
                        type: 'GET',
                        /*beforeSend: function (xhr) {
                            xhr.setRequestHeader($('#_csrf').attr('content'),
                                                 $('#_csrf_header').attr('content'));
                        },*/
                        success: function (response) {
                            $("#documentNumber").val(response.doc);
                            $("#overpaymentPeriodFrom").val(response.sroks);
                            $("#overpaymentPeriodFor").val(response.srokpo);
                            $("#overpaymentAmount").val(response.spe);
                            $("#dateOfDetectionOfOverpayment").val(response.docdv);

                            let trUderHTML="";
                            let zad = +response.spe;
                            $.each(response.uderRosDto, function (i, uder) {
                                zad = (+zad - (+uder.summa + +uder.summaP)).toFixed(2);
                                trUderHTML +=
                                    '<tr>' +
                                    '<th>' + (+i + 1) + '</th>' +
                                    '<td>' + replaceNull(response.doc) + '</td>' +
                                    '<td>' + replaceNull(uder.god) + '</td>' +
                                    '<td>' + replaceNull(uder.mes) + '</td>' +
                                    '<td>' + replaceNull(response.spe) + '</td>' +
                                    //'<td>' + replaceNull(uder.usddpm) + '</td>' +
/*                                    '<td>' + (replaceNullDecimal(uder.us) +
                                        replaceNullDecimal(uder.ub) +
                                        replaceNullDecimal(uder.usddpm)) +
                                    '</td>' +
                                    '<td>' + replaceNull(uder.ouSddpm) + '</td>' +
                                    '<td>' + replaceNull(uder.uderPercent) + '</td>' +*/
                                    '<td>' + +replaceNull(uder.summa) + '</td>' +
                                    '<td>' + +replaceNull(uder.summaP) + '</td>' +
                                    '<td>' + replaceNull(zad) + '</td>' +
                                    '</tr>';
                            });
                            $("#tableUder tbody").html(trUderHTML);

/*                            let trVozHTML="";

                            $.each(response.vozPereRosDto, function (i, voz) {
                                trVozHTML +=
                                    '<tr>' +
                                    '<th>' + (+i + 1) + '</th>' +
                                    '<td>' + replaceNull(voz.god) + '</td>' +
                                    '<td>' + replaceNull(voz.mes) + '</td>' +
                                    //'<td>' + replaceNull(voz.doc) + '</td>' +
                                    '<td>' + replaceNullDecimal(voz.s) + '</td>' +
                                    '</tr>';
                            });
                            $("#tableVoz tbody").html(trVozHTML);*/

                            $("#paymentType").val(response.vidVpl.name);

                            //дата смерти
                            $.ajax({
                                url: "/overpayment/findPensionerById/" + $("#nav-CitizenData-tab").attr("data-id"),
                                cache: false,
                                processData: false,
                                contentType: "application/json",
                                type: 'POST',
                                /*beforeSend: function (xhr) {
                                    xhr.setRequestHeader($('#_csrf').attr('content'),
                                                         $('#_csrf_header').attr('content'));
                                },*/
                                success: function (response) {
                                    //$("#dateOfDeathOfPensioner").val(response.rdat);
                                    $("#dateOfDeathOfPensioner").val(response.dsm);
                                },
                                error: function () {
                                    alert("ERROR");
                                }
                            });

                            $("#formOverpaymentData").removeClass("d-none");
                            $("#formFindCarer").removeClass("d-none");
                        },
                        error: function () {
                            alert("ERROR");
                        }
                    });

                    $("#formOverpaymentData").removeClass("d-none");
                    $("#formFindCarer").removeClass("d-none");
                },
                error: function (jqXHR) {
                    $("#formOverpaymentData").addClass("d-none");
                    $("#formFindCarer").addClass("d-none");
                    alert("Error: " + jqXHR.responseText + " !!! ")
                }
            });

        }
    })

    //ухаживающее лицо
    BODY.on('click', 'a', function () {
        if ($(this).hasClass("pickPensioner")) {
            $('#formFindCarer #divTableFindPensioner').addClass("d-none");
            let arrTd = $(this).parents('tr').children('td');
            $("#formFindCarer #surname").val(arrTd.eq(1).text());
            $("#formFindCarer #name").val(arrTd.eq(2).text());
            $("#formFindCarer #patronymic").val(arrTd.eq(3).text());
            $("#formFindCarer #adrreg").val(arrTd.eq(4).text());
            $("#formFindCarer #tel").val(arrTd.eq(5).text());
            $('#formFindCarer .btnFindCitizenSNILS').attr("name",$(this).attr('id'));
        }
    });
    BODY.on('click', 'button', function () {
        //поиск по снилс
        if ($(this).hasClass("btnFindCitizenSNILS")) {
            //поиск
            let snils = $("#formFindCarer #snils").val();
            //все чистим
            clearFindCarer();
            let json = JSON.stringify({'snils': snils});
            $.ajax({
                //url: "/overpayment/ros/findPensionerBySnils",
                url: "/overpayment/ros/findAllBySnils", //несколько результатов 162-181-171 38
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
                        let tableFindPensionerBody = $('#formFindCarer #tableFindPensioner tbody');
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
                        $('#formFindCarer #divTableFindPensioner').removeClass("d-none");

                    } else if (response.length === 1) {
                        $("#formFindCarer #snils").val(replaceNull(response[0].snils));
                        $("#formFindCarer #surname").val(replaceNull(response[0].surname));
                        $("#formFindCarer #name").val(replaceNull(response[0].name));
                        $("#formFindCarer #patronymic").val(replaceNull(response[0].patronymic));
                        $("#formFindCarer #adrreg").val(replaceNull(response[0].adrreg));
                        $("#formFindCarer #tel").val(replaceNull(response[0].tel));
                        $('#formFindCarer .btnFindCitizenSNILS').attr("name",replaceNull(response[0].id));
                    } else {
                        alert("Нет результатов")
                    }

                },
                error: function () {
                    alert("ERROR");
                }
            });
        }

        //очистить ухаживающее лицо
        if ($(this).attr("id")==="clearCarer") {
            clearFindCarer();
        }

        //сохранение overpaymentData
        if ($(this).attr("id")==="btnSaveOverpaymentData") {

            let idPensioner = $("#nav-CitizenData-tab").attr("data-id");
            let idOverpayment = $('#tableOverpayments tr.table-primary a').attr("name");

            let carerId = "";
            let carerAdrreg = "";
            let carerTel = "";
            let btnFindCitizenSNILS = $("#formFindCarer button.btnFindCitizenSNILS");
            if(btnFindCitizenSNILS.attr('name') !== undefined){ //есть ухаживающего лица
                carerId = btnFindCitizenSNILS.attr('name');
                carerAdrreg = $("#formFindCarer #adrreg").val();
                carerTel = $("#formFindCarer #tel").val();
            }

            //Причина переплаты
            let selectReasonsForOverpayments = $("#selectReasonsForOverpayments").val();
            //Конкретизация причины переплаты
            let selectSpecificationOfTheReasonsForOverpayments = $("#selectSpecificationOfTheReasonsForOverpayments").val();
            //структурное подразделение
            let structuralSubdivision = $("#structuralSubdivision").val();
            //Дополнительная информация
            let comment = $("#comment").val();
            //Заявление о добровольном погашении
            let isApplicationForVoluntaryRedemption = $("#isApplicationForVoluntaryRedemption1").is(':checked')


            let data = JSON.stringify(
                {
                    "carer":{
                        "id_ros" : carerId,
                        "adrreg" : carerAdrreg,
                        "tel" : carerTel
                    },
                    "idPensioner" : idPensioner,
                    "idOverpayment" : idOverpayment,
                    "reasonsForOverpaymentsDto" : {
                        "id" : selectReasonsForOverpayments,
                    },
                    "specificationOfTheReasonsForOverpaymentsDto" : {
                        "id" : selectSpecificationOfTheReasonsForOverpayments,
                    },
                    "departmentDto" : {
                        "id" : structuralSubdivision,
                    },
                    "comment" : comment,
                    "isApplicationForVoluntaryRedemption":isApplicationForVoluntaryRedemption
                });

            if($("#btnSaveOverpaymentData").attr("data-type")==="save"){
                $.ajax({
                    url: "/overpayment/informationOverpayment",
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
                        alert("Данные сохранены")
                    },
                    error: function (jqXHR) {
                        alert("Error: " + jqXHR.responseText + " !!! ")
                    }
                });
            }else{
                $.ajax({
                    url: "/overpayment/informationOverpayment/" + idOverpayment,
                    data: data,
                    cache: false,
                    processData: false,
                    contentType: "application/json",
                    type: "PUT",
                    /*beforeSend: function (xhr) {
                    xhr.setRequestHeader($('#_csrf').attr('content'),
                        $('#_csrf_header').attr('content'));
                    },*/
                    success: function () {
                        alert("Данные изменены")
                    },
                    error: function (jqXHR) {
                        alert("Error: " + jqXHR.responseText + " !!! ")
                    }
                });
            }

        }

    });

    //погашение удержание
    /*    $("body").on('click','button',function(){
            if ($(this).attr('id')==="nav-OverpaymentData-tab") { //Данные о переплате
                if($("#redemptionOrHolding1").is(':checked')){//выбрано погашение
                    holding();
                }
                if($("#redemptionOrHolding2").is(':checked')){//выбрано удержание
                    redemption();
                }
            }
        });
        $("body").on('click','input',function(){
            if ($(this).attr('id')==="redemptionOrHolding1") { //Данные о переплате
                currentYearAndMounth();
                holding();
            }
            if ($(this).attr('id')==="redemptionOrHolding2") { //Данные о переплате
                currentYearAndMounth();
                redemption();
            }
        });*/

});

function clearInputOverpaymentData() {
    //очистить ухаживающее лицо
    clearFindCarer();

    $("#documentNumber").val('');
    $("#overpaymentPeriodFrom").val('');
    $("#overpaymentPeriodFor").val('');
    $("#overpaymentAmount").val('');
    ARRSELECTIZE[0].selectize.clear();
    ARRSELECTIZE[1].selectize.clear();
    ARRSELECTIZE[2].selectize.clear();
    $("#snilsUhod").val('');
    $("#paymentType").val('');
    $("#dateOfDetectionOfOverpayment").val('');
    $("#dateOfDeathOfPensioner").val('');
    $("#comment").val('');
    $("#isApplicationForVoluntaryRedemption1").prop('checked', true);

    $("#btnSaveOverpaymentData").text("").removeAttr("data-type");
}

function clearFindCarer() {
    //очистить ухаживающее лицо
    $("#formFindCarer #snils").val('');
    $("#formFindCarer #surname").val('');
    $("#formFindCarer #name").val('');
    $("#formFindCarer #patronymic").val('');
    $("#formFindCarer #adrreg").val('');
    $("#formFindCarer #tel").val('');
    $('#formFindCarer .btnFindCitizenSNILS').removeAttr('name');
}


/* //для погашения и удержания
function holding(){
    $(".datePayment").removeClass('visually-hidden');
    $(".holding").removeClass('visually-hidden');
    $(".redemption").addClass('visually-hidden');
}

function redemption(){
    $(".datePayment").removeClass('visually-hidden');
    $(".holding").addClass('visually-hidden');
    $(".redemption").removeClass('visually-hidden');
}

function currentYearAndMounth(){
    $("#currentYear").val((new Date()).getFullYear());
    let selectize = $('#mounth')[0].selectize;
    selectize.setValue((new Date()).getMonth() + 1,true) //SELECTIZE
}*/
