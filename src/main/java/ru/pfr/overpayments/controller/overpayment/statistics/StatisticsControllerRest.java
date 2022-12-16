package ru.pfr.overpayments.controller.overpayment.statistics;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pfr.overpayments.model.dto.*;
import ru.pfr.overpayments.model.mapper.FullOverpaymentsMapper;
import ru.pfr.overpayments.model.mapper.FullPensionerMapper;
import ru.pfr.overpayments.model.overpayment.dto.DistrictDto;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.ReasonsForOverpaymentsDto;
import ru.pfr.overpayments.model.overpayment.entity.District;
import ru.pfr.overpayments.model.overpayment.mapper.DistrictMapper;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.ReasonsForOverpaymentsMapper;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.SpecificationOfTheReasonsForOverpaymentsMapper;
import ru.pfr.overpayments.service.FullOverpaymentsService;
import ru.pfr.overpayments.service.FullPensionerService;
import ru.pfr.overpayments.service.overpayment.DistrictService;
import ru.pfr.overpayments.service.overpayment.PensionerService;
import ru.pfr.overpayments.service.overpayment.referenceBook.ReasonsForOverpaymentsService;
import ru.pfr.overpayments.service.overpayment.referenceBook.SpecificationOfTheReasonsForOverpaymentsService;

import java.util.*;
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
    private final FullPensionerService fullPensionerService;
    private final FullPensionerMapper fullPensionerMapper;

    @PostMapping(value = {"/find"})
    public ResponseEntity<?> getViev(
            @RequestBody FilterFullOverpaymentDto filterFullOverpaymentDto
    ) {
        try {

            var fp = fullPensionerService.findAll(filterFullOverpaymentDto)
                    .stream()
                    .map(fullPensionerMapper::toDto)
                    .toList();
            SortedSet<FullDistrictDto> fullDistrictDtos = new TreeSet<>();
            for (var d :
                    districtService.findAll().stream()
                            .map(districtMapper::toDto).toList()
            ) {

                var fullPensionerDtosSortedDistrict = fp
                        .stream()
                        .filter(
                                fullPensionerDto -> fullPensionerDto.getPensioner()
                                        .getDistrictDto()
                                        .getKod()
                                        .equals(d.getKod())
                        )
                        .collect(Collectors.toSet());

                SortedMap<Long, FullStatisticsDto> fsmap = new TreeMap<>();
/*                for (var r :
                        reasonsForOverpaymentsService.findAll().stream()
                                .map(reasonsForOverpaymentsMapper::toDto).toList()
                ) {
                    fsmap.put(r.getId(), new FullStatisticsDto(r.getReasonsForOverpayments()));
                }
                fsmap.put(-1L, new FullStatisticsDto(""));*/

                for (var o :
                        fullPensionerDtosSortedDistrict) {
                    if (o.getOverpayment() != null) {
                        for (var o2 :
                                o.getOverpayment()) {
                            if (o2 != null) {
                                Long key = -1L;
                                if(o2.getOverpayment() != null){
                                   key = o2.getOverpayment().getReasonsForOverpaymentsDto().getId();
                                }
                                if(!fsmap.containsKey(key)){
                                    if(!key.equals(-1)){
                                        fsmap.put(key, new FullStatisticsDto(""));
                                    }else {
                                        fsmap.put(key, new FullStatisticsDto(
                                                reasonsForOverpaymentsService.findById(key).getReasonsForOverpayments()
                                        ));
                                    }

                                }
                                var s = fsmap.get(key);
                                s.setV1(s.getV1() + 1);
                                s.setD1(s.getD1() + o2.getSpe());
                                //fsmap.put(key,s);
                            }
                        }
                    }
                }
                SortedSet<FullStatisticsDto> list = new TreeSet<>(fsmap.values());
                fullDistrictDtos.add(
                        FullDistrictDto.builder()
                                .district(d)
                                .statistics(
                                        list
                                )
                                .build()
                );

            }

            return new ResponseEntity<>(
                    fullDistrictDtos.stream()
                            .filter(fullDistrictDto -> fullDistrictDto.getStatistics().size()>0)
                            .collect(Collectors.toList()),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
