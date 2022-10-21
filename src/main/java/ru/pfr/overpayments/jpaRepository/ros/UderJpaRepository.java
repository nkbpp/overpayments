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

    @Query(
            value = "SELECT * " +
                    "FROM VPL.UDER " +
                    "WHERE ID = ?1 and DOC = ?2",
            nativeQuery = true)
    List<UderRos> findIdAndDoc(String id, String doc);

    @Query(
            value = "SELECT * " +
                    "FROM VPL.UDER " +
                    "WHERE ID = ?1",
            nativeQuery = true)
    List<UderRos> findId(String id);

    @Query(
            value = "SELECT * " +
                    "FROM VPL.UDER " +
                    "WHERE DOC = ?1",
            nativeQuery = true)
    List<UderRos> findDoc(String doc);
}
