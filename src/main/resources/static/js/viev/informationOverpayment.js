let ARRSELECTIZE;
let SELECTIZEREASONS;
let SELECTIZESPECIFICATIONREASONS;
let CHANGE = true;
$(document).ready(function () {

    ARRSELECTIZE = $("#structuralSubdivision").selectize({
        create: true,
        //sortField: "text",
    });
    SELECTIZEREASONS = $("#selectReasonsForOverpayments").selectize({
        create: true,
        //sortField: "text",
    });
    SELECTIZESPECIFICATIONREASONS = $("#selectSpecificationOfTheReasonsForOverpayments").selectize({
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

    //обработка ENTER
    $("#tel").keyup(function (event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            $("#formCitizenData #btnUpdatePensioner").click();
        }
    });
    $("#formFindCarer #snils").keyup(function (event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            $("#formFindCarer .btnFindCitizenSNILS").click();
        }
    });
    /*    $("#formOverpaymentData #comment").keyup(function(event){
            if(event.keyCode === 13){
                event.preventDefault();
                $("#formOverpaymentData #btnSaveOverpaymentData").click();
            }
        });*/

    //Чистка select
    BODY.on('click', 'button', function () {
        if ($(this).attr('id') === "clearStructuralSubdivision") {
            ARRSELECTIZE[0].selectize.clear();
        }
        if ($(this).attr('id') === "clearReasonsForOverpayments") {
            SELECTIZEREASONS[0].selectize.clear();
            clearSELECTIZESPECIFICATIONREASONS();
        }
        if ($(this).attr('id') === "clearSelectSpecificationOfTheReasonsForOverpayments") {
            SELECTIZESPECIFICATIONREASONS[0].selectize.clear();
        }
    });

    $('#nav-OverpaymentData-tab').attr("disabled", "true").addClass("text-muted");
    $('#nav-NotificationLetters-tab').attr("disabled", "true").addClass("text-muted");

    //первоначальная загрузка данных о пенсионере
    $.ajax({
        url: "/overpayment/" + findLink + "/" + ID,
        cache: false,
        processData: false,
        contentType: "application/json",
        type: 'POST',
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
            $('#nav-NotificationLetters-tab').removeAttr("disabled").removeClass("text-muted");
        },
        error: function (response) {
            initialToats("Ошибка!", response.responseJSON.message, "err").show();
        }
    });

    BODY.on('click', 'button', function () {

        if ($(this).attr('name') === "delLegalManagement") {
            $(this).closest("[name='LegalManagementContainer']").remove()
        }

        if ($(this).attr('id') === "btnLegalManagement") {
            getLegalManagement()
        }


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
                success: function () {
                    initialToats("Успешно!", "Данные изменены!", "success").show();
                },
                error: function (response) {
                    initialToats("Ошибка!", response.responseJSON.message, "err").show();
                }
            });
        }

        //загрузка переплат
        if ($(this).attr("id") === "nav-OverpaymentData-tab") {
            let id = $("#nav-CitizenData-tab").attr("data-id-ros");
            getSpinnerTable("tableOverpayments");
            $.ajax({
                url: "/overpayment/ros/overpayments/" + id,
                cache: false,
                processData: false,
                contentType: "application/json",
                type: 'GET',
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
                error: function (response) {
                    initialToats("Ошибка!", response.responseJSON.message, "err").show();
                }
            });
        }

        //загрузка уведомительные письма
        if ($(this).attr("id") === "nav-NotificationLetters-tab") {
            let id = $("#nav-CitizenData-tab").attr("data-id-ros");
            getSpinnerTable("tableNotificationLetters");
            $.ajax({
                //url: "/overpayment/ros/overpayments/" + id,
                url: "/overpayment/fullInformationOverpayment/" + id,
                cache: false,
                processData: false,
                contentType: "application/json",
                type: 'GET',
                success: function (responseFull) {
                    let trHTML = '';
                    let tableBody = $('#tableNotificationLetters tbody');
                    tableBody.html("");
                    $.each(responseFull, function (i, item) {
                        let pensionerLetter = "";
                        let carerLetter = "";
                        let overpayment = item.overpayment;
                        if (overpayment === "" || overpayment === undefined) { //данных в базе overpayment нет
                        } else { //данные в базе overpayment есть
                            let specific = overpayment.specificationOfTheReasonsForOverpaymentsDto;
                            if (specific !== undefined && specific !== null) {
                                if (specific.documentPensioner.nameFile !== undefined && specific.documentPensioner.nameFile !== null) {
                                    pensionerLetter = '<a class="btn btn-link" ' +
                                        'href="/overpayment/fullInformationOverpayment/download?who=pensioner' +
                                        '&isId=' + item.isId +
                                        '">' + specific.documentPensioner.nameFile + '</a>';
                                }
                                if (specific.documentCarer.nameFile !== undefined && specific.documentCarer.nameFile !== null) {
                                    carerLetter = '<a class="btn btn-link" ' +
                                        'href="/overpayment/fullInformationOverpayment/download?who=carer' +
                                        '&isId=' + item.isId +
                                        '">' + specific.documentCarer.nameFile + '</a>';
                                }
                            }
                        }
                        trHTML +=
                            '<tr>' +
                            '<th>' + (+i + 1) + '</th>' +
                            '<td>' + replaceNull(item.doc) + '</td>' +
                            '<td>' + replaceNull(item.sroks) + '</td>' +
                            '<td>' + replaceNull(item.srokpo) + '</td>' +
                            '<td>' + replaceNull(item.spe) + '</td>' +
                            '<td>' + replaceNull(item.close_date) + '</td>' +
                            '<td>' + pensionerLetter + '</td>' +
                            '<td>' + carerLetter + '</td>' +
                            '</tr>';
                    })
                    tableBody.append(trHTML);
                },
                error: function (response) {
                    initialToats("Ошибка!", response.responseJSON.message, "err").show();
                }
            });
        }



    })

    BODY.on('click', 'a', function () {
        //выбор переплаты
        if ($(this).hasClass("pickOverpayment")) {
            $('#tableOverpayments tr.table-primary').removeClass("table-primary");
            let tr = $(this).parents('tr');
            tr.addClass("table-primary");

            let idIs = $(this).attr("name");

            clearInputOverpaymentData();
            //данные из базы overpayment
            $.ajax({
                url: "/overpayment/informationOverpayment/" + idIs,
                cache: false,
                processData: false,
                contentType: "application/json",
                type: "GET",
                success: function (response) {
                    if (response === "") { //данных в базе overpayment нет
                        $("#btnSaveOverpaymentData").text("Сохранить").attr("data-type", "save");
                    } else { //данные в базе overpayment есть
                        if (response.carer !== null) {
                            $("#formFindCarer #snils").val(replaceNull(response.carer.snils));
                            $("#formFindCarer #surname").val(replaceNull(response.carer.surname));
                            $("#formFindCarer #name").val(replaceNull(response.carer.name));
                            $("#formFindCarer #patronymic").val(replaceNull(response.carer.patronymic));
                            $("#formFindCarer #adrreg").val(replaceNull(response.carer.adrreg));
                            $("#formFindCarer #tel").val(replaceNull(response.carer.tel));
                            $('#formFindCarer .btnFindCitizenSNILS').attr("name", replaceNull(response.carer.id_ros));
                        }

                        if (response.reasonsForOverpaymentsDto !== null) {
                            CHANGE = false;
                            SELECTIZEREASONS[0].selectize.setValue(response.reasonsForOverpaymentsDto.id);

                            clearSELECTIZESPECIFICATIONREASONS();
                            $.each(response.reasonsForOverpaymentsDto.specificationOfTheReasonsForOverpaymentsDtos, function (i, item) {
                                SELECTIZESPECIFICATIONREASONS[0].selectize.addOption({
                                    value: item.id,
                                    text: item.specificationOfTheReasonsForOverpayments
                                });
                            });
                        }
                        if (response.specificationOfTheReasonsForOverpaymentsDto !== null) {
                            SELECTIZESPECIFICATIONREASONS[0].selectize.setValue(
                                response.specificationOfTheReasonsForOverpaymentsDto.id
                            );
                        }
                        if (response.departmentDto !== null) {
                            ARRSELECTIZE[0].selectize.setValue(response.departmentDto.id);
                        }
                        $("#comment").val(replaceNull(response.comment));
                        if (response.isApplicationForVoluntaryRedemption === true) {
                            $("#isApplicationForVoluntaryRedemption1").prop('checked', true);
                        } else {
                            $("#isApplicationForVoluntaryRedemption2").prop('checked', true);
                        }

                        //взыскание
                        if (response.controlUFSSP === true) {
                            $("#controlUFSSPYes").prop('checked', true);
                        } else {
                            $("#controlUFSSPNo").prop('checked', true);
                        }
                        if (response.theFactThatTheDebtorHasAJob === true) {
                            $("#theFactThatTheDebtorHasAJobYes").prop('checked', true);
                        } else {
                            $("#theFactThatTheDebtorHasAJobNo").prop('checked', true);
                        }
                        let legalDepartment = response.legalDepartment;
                        if (legalDepartment.length > 0) {
                            for (let i = 0; i < legalDepartment.length; i++) {
                                getLegalManagement(
                                    replaceNull(legalDepartment[i].transferDate),
                                    replaceNull(legalDepartment[i].returnDate)
                                )
                            }
                        }
                        /*if(){

                        }
                        */

                        $("#dateOfCourtDecision").val(replaceNull(response.dateOfCourtDecision)),
                            $("#dateUFSSP").val(replaceNull(response.dateUFSSP)),
                            $("#dateUVPSV").val(replaceNull(response.dateUVPSV)),

                            $("#writeOffProtocolDate").val(replaceNull(response.writeOffProtocolDate)),
                            $("#writeOffOrderNumber").val(replaceNull(response.writeOffOrderNumber)),
                            $("#writeOffOrderDate").val(replaceNull(response.writeOffOrderDate)),
                            $("#writeOffSum").val(replaceNull(response.writeOffSum))

                        $("#btnSaveOverpaymentData").text("Изменить").attr("data-type", "update");
                    }

                    getSpinnerTable("tableUder");
                    //данные из базы рос
                    $.ajax({
                        url: "/overpayment/ros/overpayment/" + idIs,
                        cache: false,
                        processData: false,
                        contentType: "application/json",
                        type: 'GET',
                        success: function (response) {
                            $("#documentNumber").val(response.doc);
                            $("#overpaymentPeriodFrom").val(response.sroks);
                            $("#overpaymentPeriodFor").val(response.srokpo);
                            $("#overpaymentAmount").val(response.spe);
                            $("#dateOfDetectionOfOverpayment").val(response.docdv);

                            let trUderHTML = "";
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
                                    '<td>' + +replaceNull(uder.summa) + '</td>' +
                                    '<td>' + +replaceNull(uder.summaP) + '</td>' +
                                    '<td>' + replaceNull(zad) + '</td>' +
                                    '</tr>';
                            });
                            $("#tableUder tbody").html(trUderHTML);

                            $("#paymentType").val(response.vidVpl.name);

                            //дата смерти
                            $.ajax({
                                url: "/overpayment/findPensionerById/" + $("#nav-CitizenData-tab").attr("data-id"),
                                cache: false,
                                processData: false,
                                contentType: "application/json",
                                type: 'POST',
                                success: function (response) {
                                    $("#dateOfDeathOfPensioner").val(response.dsm);
                                },
                                error: function (response) {
                                    initialToats("Ошибка!", response.responseJSON.message, "err").show();
                                }
                            });

                            $("#formOverpaymentData").removeClass("d-none");
                            $("#formFindCarer").removeClass("d-none");
                        },
                        error: function (response) {
                            initialToats("Ошибка!", response.responseJSON.message, "err").show();
                        }
                    });

                    $("#formOverpaymentData").removeClass("d-none");
                    $("#formFindCarer").removeClass("d-none");
                },
                error: function (response) {
                    initialToats("Ошибка!", response.responseJSON.message, "err").show();
                    $("#formOverpaymentData").addClass("d-none");
                    $("#formFindCarer").addClass("d-none");
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
            $('#formFindCarer .btnFindCitizenSNILS').attr("name", $(this).attr('id'));
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
                url: "/overpayment/ros/findAllBySnils", //несколько результатов 162-181-171 38
                data: json,
                cache: false,
                processData: false,
                contentType: "application/json",
                type: 'POST',
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
                        $('#formFindCarer .btnFindCitizenSNILS').attr("name", replaceNull(response[0].id));
                    } else {
                        initialToats("Успешно!", "Нет результатов!", "success").show();
                    }

                },
                error: function (response) {
                    initialToats("Ошибка!", response.responseJSON.message, "err").show();
                }
            });
        }

        //очистить ухаживающее лицо
        if ($(this).attr("id") === "clearCarer") {
            clearFindCarer();
        }

        //сохранение overpaymentData
        if ($(this).attr("id") === "btnSaveOverpaymentData") {

            let idPensioner = $("#nav-CitizenData-tab").attr("data-id");
            let idOverpayment = $('#tableOverpayments tr.table-primary a').attr("name");

            let carerId = "";
            let carerAdrreg = "";
            let carerTel = "";
            let btnFindCitizenSNILS = $("#formFindCarer button.btnFindCitizenSNILS");
            if (btnFindCitizenSNILS.attr('name') !== undefined) { //есть ухаживающего лица
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

            //взыскание
            let controlUFSSP = $("#controlUFSSPYes").is(':checked')
            let theFactThatTheDebtorHasAJob = $("#theFactThatTheDebtorHasAJobYes").is(':checked')

            let arrLegalDepartment = []; // объявляем массив
            let lmi = $("#legalManagement input");
            for (let i = 0, j = 0; i < lmi.length; i = i + 2, j++) {
                let legalDepartment = {
                    "transferDate": lmi.eq(i).val(),
                    "returnDate": lmi.eq(i + 1).val()
                };
                arrLegalDepartment[j] = legalDepartment;
            };

            let data = JSON.stringify(
                {
                    "carer": {
                        "id_ros": carerId,
                        "adrreg": carerAdrreg,
                        "tel": carerTel
                    },
                    "idPensioner": idPensioner,
                    "idOverpayment": idOverpayment,
                    "reasonsForOverpaymentsDto": {
                        "id": selectReasonsForOverpayments,
                    },
                    "specificationOfTheReasonsForOverpaymentsDto": {
                        "id": selectSpecificationOfTheReasonsForOverpayments,
                    },
                    "departmentDto": {
                        "id": structuralSubdivision,
                    },
                    "comment": comment,
                    "isApplicationForVoluntaryRedemption": isApplicationForVoluntaryRedemption,
                    "writeOffProtocolDate": $("#writeOffProtocolDate").val(),
                    "writeOffOrderNumber": $("#writeOffOrderNumber").val(),
                    "writeOffOrderDate": $("#writeOffOrderDate").val(),
                    "writeOffSum": $("#writeOffSum").val(),
                    "controlUFSSP": controlUFSSP,
                    "theFactThatTheDebtorHasAJob": theFactThatTheDebtorHasAJob,
                    "dateOfCourtDecision": $("#dateOfCourtDecision").val(),
                    "dateUFSSP": $("#dateUFSSP").val(),
                    "dateUVPSV": $("#dateUVPSV").val(),
                    "legalDepartment": arrLegalDepartment,
                });

            if ($("#btnSaveOverpaymentData").attr("data-type") === "save") {
                $.ajax({
                    url: "/overpayment/informationOverpayment",
                    data: data,
                    cache: false,
                    processData: false,
                    contentType: "application/json",
                    type: "POST",
                    success: function () {
                        initialToats("Успешно!", "Данные сохранены!", "success").show();
                    },
                    error: function (response) {
                        initialToats("Ошибка!", response.responseJSON.message, "err").show();
                    }
                });
            } else {
                $.ajax({
                    url: "/overpayment/informationOverpayment/" + idOverpayment,
                    data: data,
                    cache: false,
                    processData: false,
                    contentType: "application/json",
                    type: "PUT",
                    success: function () {
                        initialToats("Успешно!", "Данные изменены!", "success").show();
                    },
                    error: function (response) {
                        initialToats("Ошибка!", response.responseJSON.message, "err").show();
                    }
                });
            }

        }
    });

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

});

function clearInputOverpaymentData() {
    //очистить ухаживающее лицо
    clearFindCarer();

    $("#documentNumber").val('');
    $("#overpaymentPeriodFrom").val('');
    $("#overpaymentPeriodFor").val('');
    $("#overpaymentAmount").val('');
    ARRSELECTIZE[0].selectize.clear();
    SELECTIZEREASONS[0].selectize.clear();
    SELECTIZESPECIFICATIONREASONS[0].selectize.clear();
    $("#snilsUhod").val('');
    $("#paymentType").val('');
    $("#dateOfDetectionOfOverpayment").val('');
    $("#dateOfDeathOfPensioner").val('');
    $("#comment").val('');
    $("#isApplicationForVoluntaryRedemption1").prop('checked', true);

    $("#writeOffProtocolDate").val('');
    $("#writeOffOrderNumber").val('');
    $("#writeOffOrderDate").val('');
    $("#writeOffSum").val('');

    //взыскание
    $("#controlUFSSPNo").prop('checked', true);
    $("#theFactThatTheDebtorHasAJobNo").prop('checked', true);
    $("#dateOfCourtDecision").val('');
    $("#dateUFSSP").val('');
    $("#dateUVPSV").val('');
    $("#legalManagement").html("")

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

//для погашения и удержания
function clearSELECTIZESPECIFICATIONREASONS() {
    let items = SELECTIZESPECIFICATIONREASONS[0].selectize.options;
    for (let i in items) {
        SELECTIZESPECIFICATIONREASONS[0].selectize.removeOption(items[i].value);
    }
}

function getLegalManagement(val1 = "", val2 = "") {

    $("#legalManagement").append(
        "<div class='col-12 row' name='LegalManagementContainer'>" +
        "     <div class='col-3'>" +
        "          <label class='form-label m-0'>Дата передачи</label>" +
        "          <input type='text' class='form-control datepicker' placeholder='дд.мм.гггг' value='" + val1 + "'>" +
        "     </div>" +
        "     <div class='col-3'>" +
        "          <label class='form-label m-0'>Дата возврата</label>" +
        "          <input type='text' class='form-control datepicker' placeholder='дд.мм.гггг' value='" + val2 + "'>" +
        "     </div>" +
        "     <div class='col-3 d-flex align-items-end'>" +
        "          <button class='btn btn-secondary' type='button' name='delLegalManagement'>Удалить</button>" +
        "     </div>" +
        "</div>"
    );
    $('#legalManagement .datepicker').datepicker({
        format: 'dd.mm.yyyy',
        language: "ru"
    });
}