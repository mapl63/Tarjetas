package es.marius.tarjetas.tarjetas.repositories;

import es.marius.tarjetas.tarjetas.models.Tarjeta;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class TarjetasRepositoryImplTest {

    private final Tarjeta tarjeta1 = Tarjeta.builder()
            .id(1L)
            .numero("1234-5678-1234-5678")
            .cvc("555")
            .fechaCaducidad(LocalDate.of(2025,12,31))
            .titular("jose")
            .saldo(100.0)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .uuid(UUID.fromString("57727bc2-0c1c-494e-bbaf-e952a778e478"))
            .build();

    private final Tarjeta tarjeta2 = Tarjeta.builder()
            .id(2L)
            .numero("4321-5678-1234-5678")
            .cvc("555")
            .fechaCaducidad(LocalDate.of(2025,12,31))
            .titular("juan")
            .saldo(100.0)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .uuid(UUID.fromString("b36835eb-e56a-4023-b058-52bfa600fee5"))
            .build();

    private TarjetasRepositoryImpl repositorio;

    
    @BeforeEach
    void setUp() {
        repositorio = new TarjetasRepositoryImpl();
        repositorio.save(tarjeta1);
        repositorio.save(tarjeta2);
    }

    @Test
    void findAll() {
        List<Tarjeta> tarjetas = repositorio.findAll();

        assertAll("findAll",
                () -> assertNotNull(tarjetas, "La lista no debe ser nula"),
                () -> assertEquals(2, tarjetas.size(), "La lista debe contener 2 tarjetas")
        );
    }

    @Test
    void findAllByNumero() {
        String numero = "4321-5678-1234-5678";
        log.info("Haciendo el Test de findAllByNumero por el numero: {}", numero);

        List<Tarjeta> tarjetas = repositorio.findAllByNumero(numero);

        assertAll("findAllByNumero",
                () -> assertNotNull(tarjetas, "La lista no debe ser nula"),
                () -> assertEquals(1, tarjetas.size(), "La lista debe contener 1 tarjeta"),
                () -> assertEquals(numero , tarjetas.getFirst().getNumero() , "El numero de la tarjeta debe coincidir")
        );
    }

    @Test
    void findAllByTitular() {

        String titular = "juan";
        log.info("Haciendo el Test de findAllByTitular por el titular: {}", titular);

        List<Tarjeta> tarjetas = repositorio.findAllByTitular(titular);

        assertAll("findAllByTitular",
                () -> assertNotNull(tarjetas, "La lista no debe ser nula"),
                () -> assertEquals(1, tarjetas.size(), "La lista debe contener 1 tarjeta"),
                () -> assertEquals(titular , tarjetas.getFirst().getTitular() , "El titular de la tarjeta debe coincidir")
        );
    }


    @Test
    void findAllByNumeroAndTitular() {

        String numero = "1234-5678-1234-5678";
        String titular = "jose";
        log.info("Haciendo el Test de findAllByNumeroAndTitular por el numero: {} y titular: {}", numero, titular);

        List<Tarjeta> tarjetas = repositorio.findAllByNumeroAndTitular(numero, titular);

        assertAll("findAllByNumeroAndTitular",
                () -> assertNotNull(tarjetas, "La lista no debe ser nula"),
                () -> assertEquals(1, tarjetas.size(), "La lista debe contener 1 tarjeta"),
                () -> assertEquals(numero , tarjetas.getFirst().getNumero() , "El numero de la tarjeta debe coincidir"),
                () -> assertEquals(titular , tarjetas.getFirst().getTitular() , "El titular de la tarjeta debe coincidir")
        );
    }

    @Test
    void findById_existingId_returnsOptionalWithTarjeta() {
        Long id = 1L;
        log.info("Haciendo el Test de findById por el id: {}", id);
        Optional<Tarjeta> optionaltarjeta = repositorio.findById(id);

        assertAll("findById_existingId_returnsOptionalWithTarjeta",
                () -> assertNotNull(optionaltarjeta, "El resultado no debe ser nulo"),
                () -> assertTrue(optionaltarjeta.isPresent(), "La tarjeta debe estar presente"),
                () -> assertEquals(id, optionaltarjeta.get().getId(), "El id de la tarjeta debe coincidir")
        );
    }

    @Test
    void findById_nonexistingId_returnsOptionalWithTarjeta() {
        Long id = 4L;
        log.info("Haciendo el Test de findById por un id que no existe: {}", id);
        Optional<Tarjeta> optionaltarjeta = repositorio.findById(id);

        assertAll("findById_existingId_returnsOptionalWithTarjeta",
                () -> assertNotNull(optionaltarjeta, "El resultado no debe ser nulo"),
                () -> assertTrue(optionaltarjeta.isEmpty(), "La tarjeta debe estar presente")
        );
    }

    @Test
    void findByUuid_nonExistingUuid_returnEmptyOptional() {
        UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
        log.info("Haciendo el Test de findByUuid por un uuid que no existe: {}", uuid);
        Optional<Tarjeta> optionaltarjeta = repositorio.findByUuid(uuid);

        assertAll("findByUuid_nonExistingUuid_returnEmptyOptional",
                () -> assertNotNull(optionaltarjeta, "El resultado no debe ser nulo"),
                () ->  assertTrue(optionaltarjeta.isEmpty(), "La tarjeta no debe estar presente")
        );
    }

    @Test
    void existsById_existingId_returnsTrue() {
        Long id = 1L;
        log.info("Haciendo el Test de existsById por el id: {}", id);
        boolean exists = repositorio.existsById(id);

        assertAll("existsById_existingId_returnsTrue",
                () -> assertTrue(exists, "La tarjeta debe existir")
        );
    }

    @Test
    void existsById_existingId_returnsFalse() {
        Long id = 4L;
        log.info("Haciendo el Test de existsById por un id que no existe: {}", id);
        boolean exists = repositorio.existsById(id);

        assertAll("existsById_existingId_returnsFalse",
                () -> assertFalse(exists, "La tarjeta debe existir")
        );
    }

    @Test
    void existsByUuid_existingUuid_returnsTrue() {

        UUID uuid = UUID.fromString("57727bc2-0c1c-494e-bbaf-e952a778e478");

        log.info("Haciendo el Test de existsByUuid por el uuid: {}", uuid);
        boolean exists = repositorio.existsByUuid(uuid);

        assertAll("existsByUuid_existingUuid_returnsTrue",
                () -> assertTrue(exists, "La tarjeta debe existir")
        );
    }

    @Test
    void existsByUuid_nonExistingUuid_returnsFalse() {

        UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");

        log.info("Haciendo el Test de existsByUuid por un uuid que no existe: {}", uuid);
        boolean exists = repositorio.existsByUuid(uuid);

        assertAll("existsByUuid_nonExistingUuid_returnsFalse",
                () -> assertFalse(exists, "La tarjeta no debe existir")
        );
    }

    @Test
    void save_notExists() {
        log.info("Haciendo el Test de save por una tarjeta que no existe");
        Tarjeta nuevaTarjeta = Tarjeta.builder()
                .id(3L)
                .numero("9999-8888-7777-6666")
                .cvc("123")
                .fechaCaducidad(LocalDate.of(2026,11,30))
                .titular("maria")
                .saldo(200.0)
                .build();

        Tarjeta tarjetaGuardada = repositorio.save(nuevaTarjeta);
        var all = repositorio.findAll();

        assertAll("save_notExists",
                () -> assertNotNull(tarjetaGuardada, "La tarjeta guardada no debe ser nula"),
                () -> assertEquals(nuevaTarjeta, tarjetaGuardada, "El id de la tarjeta guardada debe coincidir"),
                () -> assertEquals(3, all.size(), "El repositorio debe contener 3 tarjetas")
        );
    }

    @Test
    void save_exists() {
        log.info("Haciendo el Test de save por una tarjeta que ya existe");

        Tarjeta tarjetaExistente = Tarjeta.builder()
                .id(1L)
                .build();

        Tarjeta tarjetaGuardada = repositorio.save(tarjetaExistente);
        var all = repositorio.findAll();

        assertAll("save_exists",
                () -> assertNotNull(tarjetaGuardada, "La tarjeta guardada no debe ser nula"),
                () -> assertEquals(tarjetaExistente, tarjetaGuardada, "El id de la tarjeta guardada debe coincidir"),
                () -> assertEquals(2, all.size(), "El repositorio debe contener 2 tarjetas")
        );
    }

    @Test
    void deleteById_existingId() {
        Long id = 1L;
        log.info("Haciendo el Test de deleteById por el id: {}", id);

        repositorio.deleteById(id);
        var all = repositorio.findAll();

        assertAll("deleteById_existingId",
                () -> assertEquals(1, all.size(), "El repositorio debe contener 1 tarjeta"),
                () -> assertFalse(repositorio.existsById(id), "La tarjeta con id " + id + " no debe existir")
        );
    }

    @Test
    void deleteByUuid_existingUuid() {
        UUID uuid = UUID.fromString("b36835eb-e56a-4023-b058-52bfa600fee5");
        log.info("Haciendo el Test de deleteByUuid por el uuid: {}", uuid);

        repositorio.deleteByUuid(uuid);
        var all = repositorio.findAll();

        assertAll("deleteByUuid_existingUuid",
                () -> assertEquals(1, all.size(), "El repositorio debe contener 1 tarjeta"),
                () -> assertFalse(repositorio.existsByUuid(uuid), "La tarjeta con uuid " + uuid + " no debe existir")
        );
    }

    @Test
    void nextId() {
        log.info("Haciendo el Test de nextId");

        Long nextId = repositorio.nextId();
        var all = repositorio.findAll();

        assertAll("nextId",
                () -> assertNotNull(nextId, "El siguiente id no debe ser nulo"),
                () -> assertEquals(3L, nextId, "El siguiente id debe ser 3"),
                () -> assertEquals(2, all.size(), "El repositorio debe contener 2 tarjetas")
        );
    }
}