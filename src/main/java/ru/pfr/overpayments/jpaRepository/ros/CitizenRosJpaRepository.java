package ru.pfr.overpayments.jpaRepository.ros;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pfr.overpayments.model.ros.entity.citizen.CitizenRos;

import java.util.List;
import java.util.Optional;

@Repository
public interface CitizenRosJpaRepository extends JpaRepository<CitizenRos, String> {

    List<CitizenRos> findBySnils(String snils);
    Optional<CitizenRos> findById(String id);

}
