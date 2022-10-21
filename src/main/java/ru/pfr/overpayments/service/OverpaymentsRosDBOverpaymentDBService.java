package ru.pfr.overpayments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.overpayments.model.ros.entity.OverpaymentRos;
import ru.pfr.overpayments.service.overpayment.OverpaymentService;
import ru.pfr.overpayments.service.ros.OverpaymentsRosService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OverpaymentsRosDBOverpaymentDBService {

    private final OverpaymentsRosService rosService;
    private final OverpaymentService overpaymentService;

    public List<OverpaymentRos> findById(String id){

        return rosService.findById(id);

    }

}
