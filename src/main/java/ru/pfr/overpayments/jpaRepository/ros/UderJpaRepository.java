package ru.pfr.overpayments.jpaRepository.ros;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.pfr.overpayments.model.ros.entity.UderRos;

import java.util.List;
import java.util.Optional;

@Repository
public interface UderJpaRepository extends JpaRepository<UderRos, String> {

    List<UderRos> findAll();

    //Optional<UderRos> findByIdMan(String id);

    Optional<UderRos> findByRecId(Long id);

    /*@Query(
            value = "SELECT * " +
                    "FROM VPL.UDER " +
                    "WHERE ID = ?1 and DOC = ?2",
            nativeQuery = true)*/
    @Query(
            value =
                    "FROM UderRos as uder " +
                            "WHERE uder.idRos = :id and uder.doc = :doc ")
/*    @Query(
            value = "SELECT u.*, s.summa FROM VPL.UDER u LEFT JOIN ( " +
                    "SELECT t0.MONTH MES, t0.YEAR GOD , t2.DOC DOC, sum(t0.AMOUNT) summa " +
                    "FROM PAYSUM.PAY t0 INNER JOIN PAYSUM.UDPAY t1 ON t0.id = t1.PAY_ID AND t0.re = t1.re AND t0.ra = t1.ra " +
                    "INNER JOIN PAYSUM.EXECUTIVE_DOC t2 ON t1.DOC_ID = t2.id AND t1.re = t2.re AND t1.ra = t2.ra " +
                    "WHERE t2.MAN_ID = ?1 and t2.DOC = ?2 AND t0.ACTION_TYPE in (12,13) " +
                    "GROUP BY t0.month, t0.YEAR, t2.DOC " +
                    ") s ON u.doc = s.doc AND u.GOD  = s.GOD AND u.MES = s.MES " +
                    "WHERE  ID = ?1 and u.doc = ?2 ",
            nativeQuery = true)*/
    List<UderRos> findIdAndDoc(String id, String doc);

    /*@Query(
            value = "SELECT * " +
                    "FROM VPL.UDER " +
                    "WHERE ID = ?1",
            nativeQuery = true)*/
    @Query(
            value =
                    "FROM UderRos as uder " +
                            "WHERE uder.idRos = :id")
/*    @Query(
            value = "SELECT u.*, s.summa FROM VPL.UDER u LEFT JOIN ( " +
                    "SELECT t0.MONTH MES, t0.YEAR GOD , t2.DOC DOC, sum(t0.AMOUNT) summa " +
                    "FROM PAYSUM.PAY t0 INNER JOIN PAYSUM.UDPAY t1 ON t0.id = t1.PAY_ID AND t0.re = t1.re AND t0.ra = t1.ra " +
                    "INNER JOIN PAYSUM.EXECUTIVE_DOC t2 ON t1.DOC_ID = t2.id AND t1.re = t2.re AND t1.ra = t2.ra " +
                    "WHERE t2.MAN_ID = ?1 AND t0.ACTION_TYPE in (12,13) " +
                    "GROUP BY t0.month, t0.YEAR, t2.DOC " +
                    ") s ON u.doc = s.doc AND u.GOD  = s.GOD AND u.MES = s.MES " +
                    "WHERE  ID = ?1 ",
            nativeQuery = true)*/
    List<UderRos> findId(String id);

    @Query(
            value =
                    "FROM UderRos as uder " +
                    "WHERE uder.doc = :doc")
    /*@Query(
            value = "SELECT * " +
                    "FROM VPL.UDER " +
                    "WHERE DOC = ?1",
            nativeQuery = true)*/
/*    @Query(
            value = "SELECT u.*, s.summa FROM VPL.UDER u LEFT JOIN ( " +
                    "SELECT t0.MONTH MES, t0.YEAR GOD , t2.DOC DOC, sum(t0.AMOUNT) summa " +
                    "FROM PAYSUM.PAY t0 INNER JOIN PAYSUM.UDPAY t1 ON t0.id = t1.PAY_ID AND t0.re = t1.re AND t0.ra = t1.ra " +
                    "INNER JOIN PAYSUM.EXECUTIVE_DOC t2 ON t1.DOC_ID = t2.id AND t1.re = t2.re AND t1.ra = t2.ra " +
                    "WHERE t2.DOC = ?1 AND t0.ACTION_TYPE in (12,13) " +
                    "GROUP BY t0.month, t0.YEAR, t2.DOC " +
                    ") s ON u.doc = s.doc AND u.GOD  = s.GOD AND u.MES = s.MES " +
                    "WHERE  u.doc = ?1 ",
            nativeQuery = true)*/
    List<UderRos> findDoc(String doc);

}
