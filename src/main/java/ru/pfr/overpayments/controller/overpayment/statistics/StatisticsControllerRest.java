package ru.pfr.overpayments.controller.overpayment.statistics;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pfr.overpayments.model.dto.FilterFullOverpaymentDto;
import ru.pfr.overpayments.model.dto.FullOverpaymentDto;
import ru.pfr.overpayments.model.dto.StatisticsDto;
import ru.pfr.overpayments.model.entity.FullOverpayment;
import ru.pfr.overpayments.model.mapper.FullOverpaymentsMapper;
import ru.pfr.overpayments.model.overpayment.mapper.DistrictMapper;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.ReasonsForOverpaymentsMapper;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.SpecificationOfTheReasonsForOverpaymentsMapper;
import ru.pfr.overpayments.service.FullOverpaymentsService;
import ru.pfr.overpayments.service.overpayment.DistrictService;
import ru.pfr.overpayments.service.overpayment.PensionerService;
import ru.pfr.overpayments.service.overpayment.referenceBook.ReasonsForOverpaymentsService;
import ru.pfr.overpayments.service.overpayment.referenceBook.SpecificationOfTheReasonsForOverpaymentsService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = {"/overpayment/statistics"})
public class StatisticsControllerRest {

    private final ReasonsForOverpaymentsService reasonsForOverpaymentsService;
    private final ReasonsForOverpaymentsMapper reasonsForOverpaymentsMapper;
    private final SpecificationOfTheReasonsForOverpaymentsService specificationOfTheReasonsForOverpaymentsService;
    private final SpecificationOfTheReasonsForOverpaymentsMapper specificationOfTheReasonsForOverpaymentsMapper;
    private final DistrictService districtService;
    private final DistrictMapper districtMapper;

    private final PensionerService pensionerService;
    private final FullOverpaymentsService fullOverpaymentsService;
    private final FullOverpaymentsMapper fullOverpaymentsMapper;

    @PostMapping(value = {"/find"})
    public ResponseEntity<?> getViev(
            @RequestBody FilterFullOverpaymentDto filterFullOverpaymentDto
    ) {
        try {
            List<FullOverpaymentDto> fullOverpaymentDtos = fullOverpaymentsService.findAll()
                    .stream()
                    .map(fullOverpaymentsMapper::toDto)
                    .filter(fullOverpaymentDto ->
                            filterFullOverpaymentDto.getNumDistrict() == null
                                    || pensionerService.findByIdRos(fullOverpaymentDto.getOverpayment().getIdRos()).getDistrict()
                                    .getId().equals(filterFullOverpaymentDto.getNumDistrict().getId())
                    )
                    .filter(fullOverpaymentDto ->
                            filterFullOverpaymentDto.getReasonsForOverpayments() == null
                                    || fullOverpaymentDto.getOverpayment()
                                    .getReasonsForOverpaymentsDto()
                                    .getId().equals(filterFullOverpaymentDto.getNumDistrict().getId())
                    )
                    .filter(fullOverpaymentDto ->
                            filterFullOverpaymentDto.getSelectSpecificationOfTheReasonsForOverpayments() == null
                                    || fullOverpaymentDto.getOverpayment()
                                    .getSpecificationOfTheReasonsForOverpaymentsDto()
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
            StatisticsDto statisticsDto = new StatisticsDto();
            statisticsDto.setRevealed(fullOverpaymentDtos.size());
            for (var o:
                 fullOverpaymentDtos) {
                if(o.getClose_date()!=null){
                    statisticsDto.setRedeemed(statisticsDto.getRedeemed() + 1);
                }
            }
            return new ResponseEntity<>(statisticsDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
