package ru.pfr.overpayments.service.overpayment.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.overpayments.jpaRepository.overpayment.log.LogiRepository;
import ru.pfr.overpayments.model.overpayment.entity.log.Logi;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager = "overpaymentsTransactionManager")
public class LogiService {

    private final LogiRepository repository;
    
    public void clear() {
        repository.deleteAll();
    }

    public List<Logi> findAll() {
        return repository.findAll();
    }

    public List<Logi> findByDateBetween(LocalDateTime d1, LocalDateTime d2, String user, String type, String text) {
        return repository.findByDateParam(d1, d2, user, type, text);
    }

    public List<Logi> findByDateBetween(String user, String type, String text) {
        return repository.findByUser(user, type, text);
    }

    public Logi findByUser(String login) {
        return repository.findByUser(login).orElse(null);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Logi findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void save(Logi logi) {
        logi.setDate(LocalDateTime.now());
        repository.save(logi);
    }

}