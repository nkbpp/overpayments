package ru.pfr.overpayments.jpaRepository.ros;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pfr.overpayments.model.ros.entity.VidVplRos;

import java.util.List;
import java.util.Optional;

@Repository
public interface VidVplJpaRepository extends JpaRepository<VidVplRos, String> {

    List<VidVplRos> findAll();

    Optional<VidVplRos> findByKod(Long kod);

}
