package es.marius.tarjetas.tarjetas.repositories;

import es.marius.tarjetas.tarjetas.models.Tarjeta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Repository
public class TarjetasRepositoryImpl implements TarjetasRepository{

    private final Map<Long, Tarjeta> tarjetas = new LinkedHashMap<>(
            Map.of(
            1L, new Tarjeta(1L,"1234-5678","555", LocalDate.of(2025,12,31), "juan",200.0,
                            LocalDateTime.now(),LocalDateTime.now(), UUID.randomUUID()),

            2L, new Tarjeta(2L,"9101-2345","111", LocalDate.of(2025,8,21), "marius",2000.0,
                            LocalDateTime.now(),LocalDateTime.now(), UUID.randomUUID())
            )
    );

    @Override
    public List<Tarjeta> findAll() {
        log.info("Buscando tarjetas");
        return tarjetas.values().stream().toList();
    }

    @Override
    public List<Tarjeta> findAllByNumero(String numero) {
        log.info("Buscando tarjetas por numero: " + numero);
        return tarjetas.values().stream()
                .filter(t-> t.getNumero().contains(numero.toLowerCase())).toList();
    }

    @Override
    public  List<Tarjeta> findAllByTitular(String titular){
        log.info("Buscando tarjetas por titular: " + titular);
        return tarjetas.values()
                        .stream()
                        .filter(t->
                                t.getTitular().contains(titular.toLowerCase())).toList();
    }

    @Override
    public List<Tarjeta> findAllByNumeroAndTitular(String numero, String titular) {
        log.info("Buscando tarjetas por numero: {} y titular: {} ",numero , titular);
        return tarjetas.values().stream()
                .filter(t-> t.getNumero().contains(numero.toLowerCase()))
                .filter(t-> t.getTitular().contains(titular.toLowerCase()))
                .toList();
    }

    @Override
    public Optional<Tarjeta> findById(Long id) {
        log.info("Buscando tarjetas por id: " + id);
        return tarjetas.get(id) !=  null ? Optional.of(tarjetas.get(id)) : Optional.empty();
    }


}
