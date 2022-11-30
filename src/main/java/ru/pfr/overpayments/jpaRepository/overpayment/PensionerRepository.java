package ru.pfr.overpayments.jpaRepository.overpayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.pfr.overpayments.model.overpayment.entity.District;
import ru.pfr.overpayments.model.overpayment.entity.Pensioner;

import java.util.List;
import java.util.Optional;

@Repository
public interface PensionerRepository extends JpaRepository<Pensioner, String> {

/*    Optional<PensionerOverpayment> findById(Long id);
    Optional<List<PensionerOverpayment>> findByDistrict(District district);*/

    void deleteById(Long id);

    List<Pensioner> findBySnils(String snils);

    Optional<Pensioner> findById(Long Id);

    Optional<Pensioner> findByIdRos(String id_ros);

    List<Pensioner> findByDistrict(District district);

    @Query(
            value = "SELECT * FROM pensioner " +
                    "WHERE (?1 is null or ?1 = '' or surname like ?1%) and " +
                    "(?2 is null or ?2 = '' or name like ?2%) and " +
                    "(?3 is null or ?3 = '' or patronymic like ?3%) and " +
                    "(?4 is null or rdat = ?4) order by rdat desc ",
            nativeQuery = true)
    List<Pensioner> findByFioAndDateOfBirth(String surname, String name, String patronymic, String dateOfBirth);

}
