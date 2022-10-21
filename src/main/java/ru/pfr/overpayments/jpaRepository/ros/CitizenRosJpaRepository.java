package ru.pfr.overpayments.jpaRepository.ros;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pfr.overpayments.model.ros.entity.citizen.CitizenRos;

import java.util.List;
import java.util.Optional;

@Repository
public interface CitizenRosJpaRepository extends JpaRepository<CitizenRos, String> {

    List<CitizenRos> findBySnils(String snils);

    Optional<CitizenRos> findById(Long id);

    /*List<CitizenRos> findAll();*/

    /*Optional<List<CitizenRos>> findBySurnameLikeIgnoreCaseAndNameLikeIgnoreCaseAndPatronymicLikeIgnoreCase(String surname, String name, String patronymic);*/
/*    @Query(
            value = "SELECT * FROM PF.MAN " +
                    "WHERE (?1 is null or FA like ?1%) and " +
                    "(?2 is null or IM like ?2%) and " +
                    "(?3 is null or OT like ?3%) and " +
                    "(?4 is null or RDAT = ?4) order by RDAT desc ",
            nativeQuery = true)
    Optional<List<CitizenRos>> findByFioAndDate(String surname, String name, String patronymic, LocalDate date);*/


/*    @Query(
            value = "SELECT * " +
                    "FROM PF.MAN WHERE id in(" +
                    "SELECT ID_UHOD  " +
                    "FROM PF.PE WHERE id IN(SELECT ID FROM PF.MAN WHERE NPERS = ?1) AND SROKS = (SELECT MAX(SROKS) FROM PF.PE WHERE id IN(SELECT ID FROM PF.MAN WHERE NPERS = ?1))" +
                    ")",
            nativeQuery = true)
    Optional<List<CitizenRos>> findPEBySnils(String snils);*/

/*    @Query(
            value = "SELECT * " +
                    "FROM PF.MAN WHERE id in(" +
                    "SELECT ID_UHOD  " +
                    "FROM PF.PE WHERE id = ?1 AND SROKS = (SELECT MAX(SROKS) FROM PF.PE WHERE id = ?1)" +
                    ")",
            nativeQuery = true)
    List<CitizenRos> findPEById(String id);*/
}
