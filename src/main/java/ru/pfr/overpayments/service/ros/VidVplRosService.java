package ru.pfr.overpayments.service.ros;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.overpayments.jpaRepository.ros.VidVplJpaRepository;
import ru.pfr.overpayments.model.ros.entity.VidVplRos;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VidVplRosService {

    private final VidVplJpaRepository repository;

    public List<VidVplRos> findAll(){
        return repository
                .findAll();
    }

    public VidVplRos findByKod(Long kod){
        return repository
                .findByKod(kod).orElse(null);
    }

}
