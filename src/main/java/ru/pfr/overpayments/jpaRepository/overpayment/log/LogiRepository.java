package ru.pfr.overpayments.jpaRepository.overpayment.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pfr.overpayments.model.overpayment.entity.log.Logi;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface LogiRepository extends JpaRepository<Logi, Long> {
    Optional<Logi> findByUser(String login);

/*    List<Logi> findByDateBetweenOrDateNotNullAndUserOrUserNotNullAndTypeOrTypeNull(LocalDateTime d1, LocalDateTime d2, String user, String l);

    List<Logi> findByUserOrUserNotNullAndTypeOrTypeNull(String user, String l);

    Optional<Logi> findByTypeAndUser(String t, String login);

    Optional<Logi> findByTypeAndUserAndDate(String t, String login, LocalDateTime date);*/

    @Query(
            value = "SELECT l.id, l.datelog, l.user, l.type, l.text FROM Logi l " +
                    "WHERE ((?1 is null and ?2 is null) " +
                    "or (l.datelog between ?1 and ?2)) and " +
                    "(?3 is null or l.user=?3) and (?4 is null or l.type=?4) and (?5 is null or l.text=?5)",
            nativeQuery = true)
    List<Logi> findByDateParam(LocalDateTime d1, LocalDateTime d2, String user, String type, String text);

    @Query(
            value = "SELECT l.id, l.datelog, l.user, l.type, l.text " +
                    "FROM Logi l " +
                    "WHERE " +
                    "(?1 is null or l.user=?1) and (?2 is null or l.type=?2) and (?3 is null or l.text=?3)",
            nativeQuery = true)
    List<Logi> findByUser(String user, String type, String text);
}

