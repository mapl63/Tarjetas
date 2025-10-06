package es.marius.tarjetas.tarjetas.repositories;


import es.marius.tarjetas.tarjetas.models.Tarjeta;
import java.util.*;
import java.util.Optional;

public interface TarjetasRepository {

    List<Tarjeta> findAll();

    List<Tarjeta> findAllByNumero(String numero);

    List<Tarjeta> findAllByTitular(String titular);

    List<Tarjeta> findAllByNumeroAndTitular(String numero,String titular);

    Optional<Tarjeta> findById(Long id);
}
