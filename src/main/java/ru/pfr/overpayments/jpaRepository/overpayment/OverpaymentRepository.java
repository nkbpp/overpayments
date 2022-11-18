package ru.pfr.overpayments.jpaRepository.overpayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pfr.overpayments.model.overpayment.entity.Overpayment;

import java.util.Optional;

@Repository
public interface OverpaymentRepository extends JpaRepository<Overpayment, Long> {

/*    Optional<PensionerOverpayment> findById(Long id);
    Optional<List<PensionerOverpayment>> findByDistrict(District district);*/
    Optional<Overpayment> findByIsId(Long id);

    void deleteById(Long id);
/*
    Optional<List<PensionerOverpayment>> findBySurnameLikeIgnoreCaseAndNameLikeIgnoreCaseAndPatronymicLikeIgnoreCase(String surname, String name, String patronymic);

    Optional<List<Pensioner>> findByDistrict(Integer district);*/

    //List<PensionerOverpayment> findBySnils(String snils);
}
