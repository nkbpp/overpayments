package ru.pfr.overpayments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.overpayments.model.dto.FilterFullOverpaymentDto;
import ru.pfr.overpayments.model.entity.FullOverpayment;
import ru.pfr.overpayments.model.entity.FullPensioner;
import ru.pfr.overpayments.service.overpayment.PensionerService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FullPensionerService {


    private final FullOverpaymentsService fullOverpaymentsService;

    private final PensionerService pensionerService;


    public List<FullPensioner> findAll() {
        List<FullPensioner> fullPensioners = new ArrayList<>();
        for (var p :
                pensionerService.findAll()) {
            List<FullOverpayment> fullOverpayments = new ArrayList<>();
            fullPensioners.add(new FullPensioner(p, fullOverpaymentsService.findById(p.getIdRos())));
        }
        return fullPensioners;
    }

    public List<FullPensioner> findAll(FilterFullOverpaymentDto filterFullOverpaymentDto) {
        List<FullPensioner> fullPensioners = new ArrayList<>();
        for (var p :
                pensionerService.findAll()
                        .stream()
                        .filter(pensioner ->
                                filterFullOverpaymentDto.getNumDistrict() == null
                                        ||
                                        pensioner
                                                .getDistrict()
                                                .getKod()
                                                .equals(
                                                        filterFullOverpaymentDto.getNumDistrict().getKod()
                                                )
                        ).toList()
        ) {
            fullPensioners.add(
                    new FullPensioner(
                            p,
                            fullOverpaymentsService.findById(p.getIdRos(), filterFullOverpaymentDto)
                    )
            );
        }
        return fullPensioners;
    }

/*    public List<FullOverpayment> findById(String id) {

        List<FullOverpayment> fullOverpayments = new ArrayList<>();
        var overpaymentsRos = overpaymentsRosService.findById(id);
        for (OverpaymentRos overpaymentRos :
                overpaymentsRos) {
            fullOverpayments.add(new FullOverpayment()
                    .builder()
                    .id(overpaymentRos.getId())
                    .is_id(overpaymentRos.getIs_id())
                    .doc(overpaymentRos.getDoc())
                    .close_date(overpaymentRos.getClose_date())
                    .spe(overpaymentRos.getSpe())
                    .sroks(overpaymentRos.getSroks())
                    .srokpo(overpaymentRos.getSrokpo())
                    .vinap(overpaymentRos.getVinap())
                    .docdv(overpaymentRos.getDocdv())
                    .vidVpl(overpaymentRos.getVidVpl())
                    .uderRos(overpaymentRos.getUderRos())
                    .vozPereRos(overpaymentRos.getVozPereRos())
                    .overpayment(overpaymentService.findByIsId(overpaymentRos.getIs_id()))
                    .build()
            );
        }

        return fullOverpayments;
    }

    public FullOverpayment findByIdIs(Long idIs) {

        var overpaymentRos = overpaymentsRosService.findByIdIs(idIs);

        return (new FullOverpayment()
                .builder()
                .id(overpaymentRos.getId())
                .is_id(overpaymentRos.getIs_id())
                .doc(overpaymentRos.getDoc())
                .close_date(overpaymentRos.getClose_date())
                .spe(overpaymentRos.getSpe())
                .sroks(overpaymentRos.getSroks())
                .srokpo(overpaymentRos.getSrokpo())
                .vinap(overpaymentRos.getVinap())
                .docdv(overpaymentRos.getDocdv())
                .vidVpl(overpaymentRos.getVidVpl())
                .uderRos(overpaymentRos.getUderRos())
                .vozPereRos(overpaymentRos.getVozPereRos())
                .overpayment(overpaymentService.findByIsId(overpaymentRos.getIs_id()))
                .build()
        );
    }*/

}
