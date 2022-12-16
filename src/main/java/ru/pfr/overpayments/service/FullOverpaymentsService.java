package ru.pfr.overpayments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.overpayments.model.dto.FilterFullOverpaymentDto;
import ru.pfr.overpayments.model.entity.FullOverpayment;
import ru.pfr.overpayments.model.ros.entity.OverpaymentRos;
import ru.pfr.overpayments.service.overpayment.OverpaymentService;
import ru.pfr.overpayments.service.overpayment.PensionerService;
import ru.pfr.overpayments.service.ros.OverpaymentsRosService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FullOverpaymentsService {

    private final OverpaymentsRosService overpaymentsRosService;

    private final OverpaymentService overpaymentService;

    private final PensionerService pensionerService;


    public List<FullOverpayment> findAll() {
        List<FullOverpayment> fullOverpayments = new ArrayList<>();
        for (var overpayment :
                overpaymentService.findAll()) {
            fullOverpayments.add(findByIdIs(overpayment.getIsId()));
        }
        return fullOverpayments;
    }

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

    public List<FullOverpayment> findById(String id, FilterFullOverpaymentDto filterFullOverpaymentDto) {
        List<FullOverpayment> fullOverpayments = null;
        try{
            fullOverpayments = findById(id)
                    .stream()
                    .filter(fullOverpaymentDto ->
                            filterFullOverpaymentDto.getReasonsForOverpayments() == null
                                    || fullOverpaymentDto.getOverpayment()
                                    .getReasonsForOverpayments()
                                    .getId().equals(filterFullOverpaymentDto.getNumDistrict().getId())
                    )
                    .filter(fullOverpaymentDto ->
                            filterFullOverpaymentDto.getSelectSpecificationOfTheReasonsForOverpayments() == null
                                    || fullOverpaymentDto.getOverpayment()
                                    .getSpecificationOfTheReasonsForOverpayments()
                                    .getId().equals(filterFullOverpaymentDto.getSelectSpecificationOfTheReasonsForOverpayments().getId())
                    )
                    .filter(fullOverpaymentDto ->
                            filterFullOverpaymentDto.getDateDetectionS() == null
                                    || fullOverpaymentDto.getDocdv().isAfter(filterFullOverpaymentDto.getDateDetectionS())
                                    || fullOverpaymentDto.getDocdv().isEqual(filterFullOverpaymentDto.getDateDetectionS())

                    )
                    .filter(fullOverpaymentDto ->
                            filterFullOverpaymentDto.getDateDetectionPo() == null
                                    || fullOverpaymentDto.getDocdv().isBefore(filterFullOverpaymentDto.getDateDetectionPo())
                                    || fullOverpaymentDto.getDocdv().isEqual(filterFullOverpaymentDto.getDateDetectionPo())
                    )
                    .filter(fullOverpaymentDto ->
                            filterFullOverpaymentDto.getSumS() == null
                                    || fullOverpaymentDto.getOverpayment().getWriteOffSum().compareTo(filterFullOverpaymentDto.getSumS()) > 0
                                    || fullOverpaymentDto.getOverpayment().getWriteOffSum().compareTo(filterFullOverpaymentDto.getSumS()) == 0
                    )
                    .filter(fullOverpaymentDto ->
                            filterFullOverpaymentDto.getSumPo() == null
                                    || fullOverpaymentDto.getOverpayment().getWriteOffSum().compareTo(filterFullOverpaymentDto.getSumPo()) < 0
                                    || fullOverpaymentDto.getOverpayment().getWriteOffSum().compareTo(filterFullOverpaymentDto.getSumPo()) == 0
                    )

                    .filter(fullOverpaymentDto ->
                            filterFullOverpaymentDto.getDateWriteOffS() == null
                                    || fullOverpaymentDto.getOverpayment().getWriteOffProtocolDate().isAfter(filterFullOverpaymentDto.getDateWriteOffS())
                                    || fullOverpaymentDto.getOverpayment().getWriteOffProtocolDate().isEqual(filterFullOverpaymentDto.getDateWriteOffS())
                    )
                    .filter(fullOverpaymentDto ->
                            filterFullOverpaymentDto.getDateWriteOffPo() == null
                                    || fullOverpaymentDto.getOverpayment().getWriteOffProtocolDate().isBefore(filterFullOverpaymentDto.getDateWriteOffPo())
                                    || fullOverpaymentDto.getOverpayment().getWriteOffProtocolDate().isEqual(filterFullOverpaymentDto.getDateWriteOffPo())
                    )
                    .toList();
        } catch (Exception e){
            System.out.println("sfsdf");
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
    }

}
