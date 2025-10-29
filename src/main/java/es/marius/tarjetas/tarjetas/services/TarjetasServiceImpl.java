package es.marius.tarjetas.tarjetas.services;

import es.marius.tarjetas.tarjetas.dto.TarjetaCreateDto;
import es.marius.tarjetas.tarjetas.dto.TarjetaResponseDto;
import es.marius.tarjetas.tarjetas.dto.TarjetaUpdateDto;
import es.marius.tarjetas.tarjetas.exceptions.TarjetaBadUuidException;
import es.marius.tarjetas.tarjetas.exceptions.TarjetaNotFound;
import es.marius.tarjetas.tarjetas.mappers.TarjetaMapper;
import es.marius.tarjetas.tarjetas.models.Tarjeta;
import es.marius.tarjetas.tarjetas.repositories.TarjetasRepository;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@CacheConfig(cacheNames = {"tarjetas"})
@Slf4j
@Service
public class TarjetasServiceImpl implements TarjetasService {

    private final TarjetasRepository tarjetasRepository;
    private final TarjetaMapper tarjetaMapper;


    @Override
    public List<TarjetaResponseDto> findAll(String numero, String titular){

        if((numero==null || numero.isEmpty()) && (titular==null || titular.isEmpty())){
            log.info("Buscamos todas las tarjetas");
            return tarjetaMapper.toTarjetaResponseDtoList(tarjetasRepository.findAll());
        }

        if((numero != null && !numero.isEmpty()) && (titular == null || titular.isEmpty())){
            log.info("Buscamos las tarjetas por numero: ´{} " , numero);
            return tarjetaMapper.toTarjetaResponseDtoList(tarjetasRepository.findAllByNumero(numero));
        }

        if(numero== null || numero.isEmpty()){
            log.info("Buscamos las tarjetas por titular: {}" , titular);
            return tarjetaMapper.toTarjetaResponseDtoList(tarjetasRepository.findAllByTitular(titular));
        }

        log.info("Estamos en service y busco por numero: {} y titular: {}",numero , titular);
        return tarjetaMapper.toTarjetaResponseDtoList(tarjetasRepository.findAllByNumeroAndTitular(numero, titular));
    }


    @Cacheable(key = "#id")
    @Override
    public TarjetaResponseDto findById(Long id){

        /* La manera mas larga pero normalmente correcta:
        Optional<Tarjeta> tarjetaEncontrada = tarjetasRepository.findById(id);
        if(tarjetaEncontrada.isPresent()){
            return tarjetaEncontrada.get();
        }else{
            throw new TarjetaNotFound(id);
        }*/

        //Es igual que el codigo anterior pero en una sola linea:
        return tarjetaMapper.toTarjetaResponseDto(tarjetasRepository.findById(id).orElseThrow(()-> new TarjetaNotFound(id)));
    }


    @Cacheable(key = "#uuid")
    @Override
    public TarjetaResponseDto findByUuid(String uuid){

        log.info("Buscando tarjeta por uuid: {}", uuid);

        try {
            var myUUID = UUID.fromString(uuid);
            return tarjetaMapper.toTarjetaResponseDto(tarjetasRepository.findByUuid(myUUID)
                    .orElseThrow(()-> new TarjetaBadUuidException(uuid)));
        }catch (IllegalArgumentException e){
            throw new TarjetaBadUuidException(uuid);
        }

    }


    @CachePut(key = "#result.id")
    @Override
    public TarjetaResponseDto save(TarjetaCreateDto tarjetaCreateDto){
        log.info("Guardando tarjeta: " + tarjetaCreateDto);

        Long id = tarjetasRepository.nextId();

        Tarjeta nuevaTarjeta = tarjetaMapper.toTarjeta(id, tarjetaCreateDto);

        return tarjetaMapper.toTarjetaResponseDto(tarjetasRepository.save(nuevaTarjeta));

    }

    @CachePut(key ="#result.id")
    @Override
    public TarjetaResponseDto update(Long id, TarjetaUpdateDto tarjetaUpdateDto){
        log.info("Actualizando tarjeta por id: " + id);

        var tarjetaActual = tarjetasRepository.findById(id).orElseThrow(()-> new TarjetaNotFound(id));

        Tarjeta tarjetaActualizada  = tarjetaMapper.toTarjeta(tarjetaUpdateDto, tarjetaActual);

        return tarjetaMapper.toTarjetaResponseDto(tarjetasRepository.save(tarjetaActualizada));
    }

    @CacheEvict(key = "#id")
    @Override
    public void deleteById(Long id){
        log.debug("Eliminando tarjeta por id:{} ", id);
        tarjetasRepository.findById(id).orElseThrow(()-> new TarjetaNotFound(id));
        tarjetasRepository.deleteById(id);
    }
}
