package ru.pfr.overpayments.jpaRepository.ros;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.pfr.overpayments.model.ros.entity.VozPereRos;

import java.util.List;

@Repository
public interface VozPereRepository extends JpaRepository<VozPereRos, String> {

    @Query(
            value = "SELECT * " +
                    "FROM VPL.VOZPERE " +
                    "WHERE ID = ?1 and DOC = ?2",
            nativeQuery = true)
    List<VozPereRos> findIdAndDoc(String id, String doc);

    @Query(
            value = "SELECT * " +
                    "FROM VPL.VOZPERE " +
                    "WHERE ID = ?1",
            nativeQuery = true)
    List<VozPereRos> findId(String id);

    @Query(
            value = "SELECT * " +
                    "FROM VPL.VOZPERE " +
                    "WHERE DOC = ?1",
            nativeQuery = true)
    List<VozPereRos> findDoc(String doc);
}
