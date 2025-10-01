package es.marius.tarjetas.tarjetas.repositories;


import es.marius.tarjetas.tarjetas.models.Tarjeta;
import java.util.*;
import java.util.Optional;

public interface TarjetasRepository {

    List<Tarjeta> findAll();
    List<Tarjeta> findAllByNumero(String numero);
    Optional<Tarjeta> findById(Long id);

}
