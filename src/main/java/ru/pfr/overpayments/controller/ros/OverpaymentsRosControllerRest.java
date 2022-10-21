package ru.pfr.overpayments.controller.ros;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pfr.overpayments.jpaRepository.ros.VozPereRepository;
import ru.pfr.overpayments.model.ros.entity.VozPereRos;
import ru.pfr.overpayments.model.ros.mapper.OverpaymentsRosMapper;
import ru.pfr.overpayments.service.ros.OverpaymentsRosService;
import ru.pfr.overpayments.service.ros.UderRosService;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/overpayment/ros")
public class OverpaymentsRosControllerRest {

    private final OverpaymentsRosMapper overpaymentsRosMapper;
    private final OverpaymentsRosService overpaymentsRosService;

    private final VozPereRepository vozPereRepository;

    /**
     * Найти ID
     */
    @GetMapping(path = "/overpayments/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(overpaymentsRosService.findById(id)
                    .stream()
                    .map(overpaymentsRosMapper::toDto)
                    .collect(Collectors.toList())
                    , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Найти ID_IS
     */
    @GetMapping(path = "/overpayment/{id}")
    public ResponseEntity<?> findByIdIs(@PathVariable("id") Long idIs) {
        try {
            /*var t = overpaymentsRosService.findByIdIs(idIs).getDoc();
            var v = vozPereRepository.findDoc(t);*/
            return new ResponseEntity<>(
                    overpaymentsRosMapper.toDto(overpaymentsRosService.findByIdIs(idIs))
                    , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
