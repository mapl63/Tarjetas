package es.marius.tarjetas.tarjetas.controllers;


import es.marius.tarjetas.tarjetas.dto.TarjetaCreateDto;
import es.marius.tarjetas.tarjetas.dto.TarjetaResponseDto;
import es.marius.tarjetas.tarjetas.dto.TarjetaUpdateDto;
import es.marius.tarjetas.tarjetas.models.Tarjeta;
import es.marius.tarjetas.tarjetas.services.TarjetasService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/${api.version}/tarjetas")
@RestController
public class TarjetasRestController {

    private final TarjetasService tarjetasService;


    @GetMapping()
    public ResponseEntity<List<TarjetaResponseDto>> getAllTarjetas(@RequestParam(required = false) String numero,
                                                        @RequestParam(required = false) String titular) {
        log.info("Buscando tarjetas por numero {} y titular {}", numero, titular);
        return ResponseEntity.ok(tarjetasService.findAll(numero, titular));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarjetaResponseDto> getTarjetaById(@PathVariable Long id) {
        log.info("Buscando tarjeta por id {}", id);
        //Estilo Funcional:
        return ResponseEntity.ok(tarjetasService.findById(id));

        /* Otra Forma de hacerlo LLamado Estilo Estructurado:

            Optional<Tarjeta> tarjeta = tarjetasRepository.findById(id);
            if(tarjeta.isPresent()){
                return ResponseEntity.ok(tarjeta.get());
            }else{
                return ResponseEntity.notFound().build();
            }*/
    }

    @PostMapping()
    public ResponseEntity<TarjetaResponseDto> create(@Valid @RequestBody TarjetaCreateDto tarjetaCreateDto) {
        log.info("Creando tarjeta {}", tarjetaCreateDto);
        var saved = tarjetasService.save(tarjetaCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarjetaResponseDto> update(@PathVariable Long id,@RequestBody TarjetaUpdateDto tarjetaUpdateDto) {
        log.info("Actualizando tarjeta por id={} con tarjeta={}", id, tarjetaUpdateDto);
        return ResponseEntity.ok(tarjetasService.update(id, tarjetaUpdateDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TarjetaResponseDto> updatePartial(@PathVariable Long id,@RequestBody TarjetaUpdateDto tarjetaUpdateDto) {
        log.info("Actualizando parcialmente tarjeta con id={} con tarjeta={}", id,tarjetaUpdateDto);
        return ResponseEntity.ok(tarjetasService.update(id, tarjetaUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TarjetaResponseDto> delete(@PathVariable Long id) {
        log.info("Eliminando tarjeta con id={}", id);
        tarjetasService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException ex) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        BindingResult result = ex.getBindingResult();

        problemDetail.setDetail("FAllo la validación para el objeto='" + result.getObjectName()
                + "'. " + "Núm errores: " + result.getErrorCount());

        Map<String, String> errores = new HashMap<>();
        result.getAllErrors().forEach((error) -> {
            String fieldNAme = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errores.put(fieldNAme, errorMessage);
        });

        problemDetail.setProperty("errores", errores);
        return problemDetail;
    }


}
