package ru.pfr.overpayments.service.overpayment.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.overpayments.controller.overpayment.admin.LogiFindDto;
import ru.pfr.overpayments.jpaRepository.overpayment.log.LogiRepository;
import ru.pfr.overpayments.model.overpayment.entity.log.Logi;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager = "overpaymentsTransactionManager")
public class LogiService {

    private final LogiRepository repository;

    public void clear() {
        repository.deleteAll();
    }

    public List<Logi> findAll() {
        return repository.findAll().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
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

    public List<Logi> find(LogiFindDto logiFindDto) {
        return repository.findAll()
                .stream()
                .filter(
                        logi ->
                                (logiFindDto.getDateS() == null || logiFindDto.getDatePo() == null) ||
                                        (logi.getDate().isAfter(logiFindDto.getDateS()) && logi.getDate().isBefore(logiFindDto.getDatePo()))
                )
                .filter(
                        logi -> logiFindDto.getType() == null || logi.getType() == logiFindDto.getType()
                )
                .filter(
                        logi -> logiFindDto.getUser().equals("") || logi.getUser().equals(logiFindDto.getUser())
                )
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

}