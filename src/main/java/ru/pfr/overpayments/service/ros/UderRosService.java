package ru.pfr.overpayments.service.ros;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.overpayments.jpaRepository.ros.UderJpaRepository;
import ru.pfr.overpayments.jpaRepository.ros.VidVplJpaRepository;
import ru.pfr.overpayments.model.ros.entity.UderRos;
import ru.pfr.overpayments.model.ros.entity.VidVplRos;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UderRosService {

    private final UderJpaRepository repository;

    public List<UderRos> findAll(){
        return repository
                .findAll();
    }

    public List<UderRos> findIdAndDoc(String id, String doc){
        return repository
                .findIdAndDoc(id, doc);
    }

    public List<UderRos> findId(String id){
        return repository
                .findId(id);
    }

    public List<UderRos> findDoc(String doc){
        return repository
                .findDoc(doc);
    }

}
