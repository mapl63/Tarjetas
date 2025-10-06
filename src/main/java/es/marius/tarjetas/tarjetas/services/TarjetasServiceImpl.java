package es.marius.tarjetas.tarjetas.services;

import es.marius.tarjetas.tarjetas.exceptions.TarjetaNotFound;
import es.marius.tarjetas.tarjetas.models.Tarjeta;
import es.marius.tarjetas.tarjetas.repositories.TarjetasRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TarjetasServiceImpl implements TarjetasService {

    private final TarjetasRepository tarjetasRepository;

    @Autowired
    public TarjetasServiceImpl(TarjetasRepository tarjetasRepository) {
        this.tarjetasRepository = tarjetasRepository;

    }

    @Override
    public List<Tarjeta> findAll(String numero, String titular){
        if((numero==null || numero.isEmpty()) && (titular==null || titular.isEmpty())){
            log.info("Buscamos todas las tarjetas");
            return tarjetasRepository.findAll();
        }
        if((numero != null && !numero.isEmpty()) && (titular == null || titular.isEmpty())){
            log.info("Buscamos las tarjetas por numero: " + numero);
            return tarjetasRepository.findAllByNumero(numero);
        }

        if(numero== null || numero.isEmpty()){
            log.info("Buscamos las tarjetas por titular: " + titular);
            return tarjetasRepository.findAllByTitular(titular);
        }

        log.info("Buscando productos pro numero: " + numero + "y titular: " + titular);
        return tarjetasRepository.findAllByNumeroAndTitular(numero, titular);
    }

    @Override
    public Tarjeta findById(Long id){

        /* La manera mas larga pero normalmente correcta:
        Optional<Tarjeta> tarjetaEncontrada = tarjetasRepository.findById(id);
        if(tarjetaEncontrada.isPresent()){
            return tarjetaEncontrada.get();
        }else{
            throw new TarjetaNotFound(id);
        }*/

        //Es igual que el codigo anterior pero en una sola linea:
        return tarjetasRepository.findById(id).orElseThrow(()-> new TarjetaNotFound(id));
    }
}
