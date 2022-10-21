package ru.pfr.overpayments.jpaRepository.ros;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;
import ru.pfr.overpayments.model.ros.entity.OverpaymentRos;

import java.util.List;
import java.util.Optional;

@Repository
public interface OverpaymentRosJpaRepository extends JpaRepository<OverpaymentRos, String> {

    @Query(
            value = "SELECT * FROM VPL.IS " +
                    "WHERE VU = 6 and " +
                    "id = ?1 " +
                    "order by SROKPO desc ",
            nativeQuery = true)
    List<OverpaymentRos> findOverpaymentById(String id);

    @Query(
            value = "SELECT * FROM VPL.IS " +
                    "WHERE VU = 6 " +
                    "order by SROKPO desc ",
            nativeQuery = true)
    List<OverpaymentRos> findAllOverpayment();

    @Query(
            value = "SELECT * " +
                    "FROM VPL.IS " +
                    "WHERE IS_ID = ?1",
            nativeQuery = true)
    Optional<OverpaymentRos> findOverpaymentByIdIs(Long idIs);
    /*@Query(
            value = "SELECT I.*, V.NAME VIDVPL " +
                    "FROM VPL.IS I LEFT JOIN VPL.VIDVPL V ON I.UBRAZ=V.KOD " +
                    "WHERE IS_ID = ?1",
            nativeQuery = true)*/

}
