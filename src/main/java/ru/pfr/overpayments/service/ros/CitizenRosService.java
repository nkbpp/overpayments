package ru.pfr.overpayments.service.ros;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.overpayments.jpaRepository.ros.CitizenRosJpaRepository;
import ru.pfr.overpayments.model.ros.entity.citizen.CitizenRos;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class CitizenRosService {

    private final CitizenRosJpaRepository repository;

    public List<CitizenRos> findPensionerBySnils(String snils){
        return repository
                .findBySnils(snils);
                //.stream()
                //.filter((p) -> (p.getPw()==null || p.getPw().equals("0")))
                //.collect(Collectors.toList());
    }

    public List<CitizenRos> findBySnils(String snils){
        return repository
                .findBySnils(snils);
    }

    public CitizenRos findById(String id){
        return repository
                .findById(id).orElse(null);
    }

}
