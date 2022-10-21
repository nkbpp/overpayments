package ru.pfr.overpayments.jpaRepository.overpayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pfr.overpayments.model.overpayment.entity.Carer;
import ru.pfr.overpayments.model.overpayment.entity.Pensioner;

@Repository
public interface CarerRepository extends JpaRepository<Carer, String> {

    void deleteById(Long id);

/*    Optional<PensionerOverpayment> findById(Long id);
    Optional<List<PensionerOverpayment>> findByDistrict(District district);*/


/*
    Optional<List<PensionerOverpayment>> findBySurnameLikeIgnoreCaseAndNameLikeIgnoreCaseAndPatronymicLikeIgnoreCase(String surname, String name, String patronymic);

    Optional<List<Pensioner>> findByDistrict(Integer district);*/

    //List<PensionerOverpayment> findBySnils(String snils);
}
