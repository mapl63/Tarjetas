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

    Optional<Tarjeta> findByUuid(UUID uuid);

    boolean existsById(Long id);

    boolean existsByUuid(UUID uuid);

    Tarjeta save(Tarjeta tarjeta);

    void deleteById(Long id);

    void deleteByUuid(UUID uuid);

    Long nextId();

}
