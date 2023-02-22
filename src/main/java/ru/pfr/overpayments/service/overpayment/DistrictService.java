package ru.pfr.overpayments.service.overpayment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.overpayments.jpaRepository.overpayment.DistrictRepository;
import ru.pfr.overpayments.model.overpayment.entity.District;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager = "overpaymentsTransactionManager")
public class DistrictService {

    private final DistrictRepository repository;

    public District findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public District findByKod(Integer id) {
        return repository.findByKod(id).orElse(null);
    }

    public List<District> findAll() {
        return repository.findAll();
    }

    public List<District> findAll(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }


    public void update(District district) {
        repository.save(district);
    }

    public void save(District district) {
        repository.save(district);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
