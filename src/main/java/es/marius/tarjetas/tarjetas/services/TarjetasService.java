package es.marius.tarjetas.tarjetas.services;

import es.marius.tarjetas.tarjetas.models.Tarjeta;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface TarjetasService  {
    List<Tarjeta> findAll(String numero, String titular);

    Tarjeta findById(Long id);
}
