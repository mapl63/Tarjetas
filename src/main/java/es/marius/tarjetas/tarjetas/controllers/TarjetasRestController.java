package es.marius.tarjetas.tarjetas.controllers;


import es.marius.tarjetas.tarjetas.models.Tarjeta;
import es.marius.tarjetas.tarjetas.services.TarjetasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RequestMapping("api/${api.version}/tarjetas")
@RestController
public class TarjetasRestController {

    private final TarjetasService tarjetasService;

    @Autowired
    public TarjetasRestController(TarjetasService tarjetasService) {
        this.tarjetasService = tarjetasService;
    }


    @GetMapping()
    public ResponseEntity<List<Tarjeta>> getAllTarjetas(@RequestParam(required = false) String numero,
                                                        @RequestParam(required = false) String titular) {
        log.info("Buscando tarjetas por numero {} y titular {}", numero, titular);
        return ResponseEntity.ok(tarjetasService.findAll(numero, titular));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarjeta> getTarjetaById(@PathVariable Long id) {
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
}
