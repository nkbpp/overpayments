package ru.pfr.overpayments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.overpayments.model.entity.FullOverpayment;
import ru.pfr.overpayments.model.ros.entity.OverpaymentRos;
import ru.pfr.overpayments.service.overpayment.OverpaymentService;
import ru.pfr.overpayments.service.ros.OverpaymentsRosService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FullOverpaymentsService {

    private final OverpaymentsRosService overpaymentsRosService;

    private final OverpaymentService overpaymentService;

    public List<FullOverpayment> findById(String id) {

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
        FullOverpayment fullOverpayments = null;
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

    }

}
