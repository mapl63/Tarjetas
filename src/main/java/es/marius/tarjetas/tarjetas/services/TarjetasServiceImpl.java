package es.marius.tarjetas.tarjetas.services;

import es.marius.tarjetas.tarjetas.dto.TarjetaCreateDto;
import es.marius.tarjetas.tarjetas.dto.TarjetaResponseDto;
import es.marius.tarjetas.tarjetas.dto.TarjetaUpdateDto;
import es.marius.tarjetas.tarjetas.exceptions.TarjetaNotFound;
import es.marius.tarjetas.tarjetas.mappers.TarjetaMapper;
import es.marius.tarjetas.tarjetas.models.Tarjeta;
import es.marius.tarjetas.tarjetas.repositories.TarjetasRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@CacheConfig(cacheNames = {"tarjetas"})
@Slf4j
@Service
public class TarjetasServiceImpl implements TarjetasService {

    private final TarjetasRepository tarjetasRepository;
    private final TarjetaMapper tarjetaMapper;

    @Autowired
    public TarjetasServiceImpl(TarjetasRepository tarjetasRepository, TarjetaMapper tarjetaMapper) {
        this.tarjetasRepository = tarjetasRepository;
        this.tarjetaMapper = tarjetaMapper;

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
    @Cacheable
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

    @Override
    @Cacheable
    public Tarjeta findbyUuid(String uuid){
        log.info("Buscamos tarjeta s por uuid: " + uuid);
        var myUUID = UUID.fromString(uuid);
        return tarjetasRepository.findByUuid(myUUID).orElse(null);
    }

    @Override
    @CachePut
    public TarjetaResponseDto save(TarjetaCreateDto tarjetaCreateDto){
        log.info("Guardando tarjeta: " + tarjetaCreateDto);

        Long id = tarjetasRepository.nextId();

        Tarjeta nuevaTarjeta = tarjetaMapper.toTarjeta(id, tarjetaCreateDto);

        return tarjetaMapper.toTarjetaResponseDto(tarjetasRepository.save(nuevaTarjeta));

    }

    @Override
    @CachePut
    public Tarjeta update(Long id, TarjetaUpdateDto tarjetaUpdateDto){
        log.info("Actualizando tarjeta por id: " + id);

        var tarjetaActual = this.findById(id);

        Tarjeta tarjetaActualizada  = tarjetaMapper.toTarjeta(tarjetaUpdateDto, tarjetaActual);

        return tarjetasRepository.save(tarjetaActualizada);
    }

    @Override
    @CacheEvict
    public void deleteById(Long id){
        log.debug("Eliminando tarjeta por id: " + id);
        var tarjetaEncontrada = this.findById(id);

        if(tarjetaEncontrada != null){
            tarjetasRepository.deleteById(id);
        }
    }
}
