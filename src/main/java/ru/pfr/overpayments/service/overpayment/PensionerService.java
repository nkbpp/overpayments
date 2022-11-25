package ru.pfr.overpayments.service.overpayment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.overpayments.jpaRepository.overpayment.PensionerRepository;
import ru.pfr.overpayments.model.overpayment.dto.PensionerDto;
import ru.pfr.overpayments.model.overpayment.entity.District;
import ru.pfr.overpayments.model.overpayment.entity.Overpayment;
import ru.pfr.overpayments.model.overpayment.entity.Pensioner;
import ru.pfr.overpayments.model.ros.entity.citizen.CitizenRos;
import ru.pfr.overpayments.service.ros.CitizenRosService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional(transactionManager = "overpaymentsTransactionManager")
public class PensionerService {

    private final PensionerRepository repository;
    private final CitizenRosService citizenRosService;
    private final DistrictService districtService;


    public void update(Pensioner pensioner) {
        save(pensioner);
    }

    public void save(PensionerDto pensionerDto) {
        CitizenRos citizenRos = citizenRosService.findById(pensionerDto.getId_ros());//данные из базы ros
        save(Pensioner.builder()
                .idRos(pensionerDto.getId_ros())
                .adrreg(pensionerDto.getAdrreg())
                .tel(pensionerDto.getTel())
                .snils(citizenRos.getSnils())
                .name(citizenRos.getName())
                .surname(citizenRos.getSurname())
                .patronymic(citizenRos.getPatronymic())
                .rdat(citizenRos.getRdat())
                .dsm(citizenRos.getDsm())
                .district(districtService.findByKod(citizenRos.getDistrict()))
                .overpayments(new ArrayList<>())
                .overpayments(new ArrayList<>())
                .build());
    }

    public void save(Pensioner pensioner) {
        if (pensioner.getOverpayments() != null) {
            for (Overpayment overpayment :
                    pensioner.getOverpayments()) {
                overpayment.setPensioner(pensioner);
            }
        }
        repository.save(pensioner);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Pensioner> findBySnils(String snils) {
        return repository
                .findBySnils(snils);
    }

    public List<Pensioner> findByDistrict(District district) {
        return repository.findByDistrict(district);
    }

    public List<Pensioner> findByFioAndDate(String surname, String name, String patronymic, LocalDate dateOfBirth) {
        //todo return repository.findByFioAndDateOfBirth(surname, name, patronymic, dateOfBirth);
        return repository.findByFioAndDateOfBirth(surname, name, patronymic, dateOfBirth.toString());
    }

    public Pensioner findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Pensioner findByIdRos(String idRos) {
        return repository.findByIdRos(idRos).orElse(null);
    }

}
