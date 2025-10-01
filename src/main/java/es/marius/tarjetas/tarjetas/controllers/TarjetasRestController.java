package es.marius.tarjetas.tarjetas.controllers;


import es.marius.tarjetas.tarjetas.models.Tarjeta;
import es.marius.tarjetas.tarjetas.repositories.TarjetasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
