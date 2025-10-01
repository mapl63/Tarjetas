package es.marius.tarjetas.tarjetas.controllers;


import es.marius.tarjetas.tarjetas.models.Tarjeta;
import es.marius.tarjetas.tarjetas.repositories.TarjetasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("api/${api.version}/tarjetas")
@RestController
public class TarjetasRestController {

    private final TarjetasRepository tarjetasRepository;

    @Autowired
    public TarjetasRestController(TarjetasRepository tarjetasRepository){
        this.tarjetasRepository = tarjetasRepository;
    }


    @GetMapping()
    public ResponseEntity<List<Tarjeta>> getAllTarjetas(@RequestParam(required = false) String numero) {
        if(numero != null){
            return ResponseEntity.ok(tarjetasRepository.findAllByNumero(numero));
        }else{
            return ResponseEntity.ok(tarjetasRepository.findAll());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Tarjeta> getTarjetaById(@PathVariable Long id) {

        //Estilo Funcional:
        return tarjetasRepository.findById(id)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());

        /* Otra Forma de hacerlo LLamado Estilo Estructurado:

            Optional<Tarjeta> tarjeta = tarjetasRepository.findById(id);
            if(tarjeta.isPresent()){
                return ResponseEntity.ok(tarjeta.get());
            }else{
                return ResponseEntity.notFound().build();
            }*/
    }
}
