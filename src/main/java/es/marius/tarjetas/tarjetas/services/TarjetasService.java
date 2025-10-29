package es.marius.tarjetas.tarjetas.services;

import es.marius.tarjetas.tarjetas.dto.TarjetaCreateDto;
import es.marius.tarjetas.tarjetas.dto.TarjetaResponseDto;
import es.marius.tarjetas.tarjetas.dto.TarjetaUpdateDto;
import es.marius.tarjetas.tarjetas.models.Tarjeta;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface TarjetasService  {
    List<TarjetaResponseDto> findAll(String numero, String titular);

    TarjetaResponseDto findById(Long id);

    TarjetaResponseDto findByUuid(String uuid);

    TarjetaResponseDto save(TarjetaCreateDto tarjetaCreateDto);

    TarjetaResponseDto update(Long id, TarjetaUpdateDto tarjetaUpdateDto);

    void deleteById(Long id);
}
