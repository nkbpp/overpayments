package ru.pfr.overpayments.service.ros;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.overpayments.jpaRepository.ros.OverpaymentRosJpaRepository;
import ru.pfr.overpayments.model.ros.entity.OverpaymentRos;


import java.util.List;

@Service
@RequiredArgsConstructor
public class OverpaymentsRosService {

    private final OverpaymentRosJpaRepository repository;

    public List<OverpaymentRos> findById(String id){
        return repository
                .findOverpaymentByIdRos(id);
    }

    public OverpaymentRos findByIdIs(Long idIs){
        return repository
                .findOverpaymentByIdIs(idIs).orElse(null);
    }

}
