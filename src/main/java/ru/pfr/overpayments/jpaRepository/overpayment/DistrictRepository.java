package ru.pfr.overpayments.jpaRepository.overpayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pfr.overpayments.model.overpayment.entity.District;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, String> {

    Optional<District> findById(Long id);
    Optional<District> findByKod(Integer id);

    void deleteById(Long id);

}
