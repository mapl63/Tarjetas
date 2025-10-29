package es.marius.tarjetas.tarjetas.mappers;

import es.marius.tarjetas.tarjetas.dto.TarjetaCreateDto;
import es.marius.tarjetas.tarjetas.dto.TarjetaUpdateDto;
import es.marius.tarjetas.tarjetas.models.Tarjeta;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class TarjetaMapperTest {

    private final TarjetaMapper tarjetaMapper = new TarjetaMapper();

    @Test
    void toTarjetaCreated() {
        log.info("Haciendo el test de crear tarjeta a partir de TarjetaCreateDto");

        Long id = 1L;
        TarjetaCreateDto tarjetaCreateDto = TarjetaCreateDto.builder()
                .numero("1234")
                .cvc("111")
                .fechaCaducidad(LocalDate.of(2025, 12, 31))
                .titular("John Doe")
                .saldo(1000.0)
                .build();

        var resultado = tarjetaMapper.toTarjeta(id, tarjetaCreateDto);

        assertAll(
                () -> assertEquals(id, resultado.getId()),
                () -> assertEquals(tarjetaCreateDto.getNumero(), resultado.getNumero()),
                () -> assertEquals(tarjetaCreateDto.getCvc(), resultado.getCvc()),
                () -> assertEquals(tarjetaCreateDto.getFechaCaducidad(), resultado.getFechaCaducidad()),
                () -> assertEquals(tarjetaCreateDto.getTitular(), resultado.getTitular()),
                () -> assertEquals(tarjetaCreateDto.getSaldo(), resultado.getSaldo())
        );
    }

    @Test
    void testToTarjetaCreated() {
        log.info("Haciendo el test de actualizar tarjeta a partir de TarjetaUpdateDto");

        Long id = 1L;

        TarjetaUpdateDto tarjetaUpdateDto = TarjetaUpdateDto.builder()
                .numero("5678")
                .cvc("222")
                .fechaCaducidad(LocalDate.of(2026, 11, 30))
                .saldo(2000.0)
                .build();

        Tarjeta tarjeta = Tarjeta.builder()
                .id(id)
                .numero(tarjetaUpdateDto.getNumero())
                .cvc(tarjetaUpdateDto.getCvc())
                .fechaCaducidad(tarjetaUpdateDto.getFechaCaducidad())
                .saldo(tarjetaUpdateDto.getSaldo())
                .build();

        var resultado = tarjetaMapper.toTarjeta(tarjetaUpdateDto, tarjeta);

        assertAll(
                () -> assertEquals(id, resultado.getId()),
                () -> assertEquals(tarjetaUpdateDto.getNumero(), resultado.getNumero()),
                () -> assertEquals(tarjetaUpdateDto.getCvc(), resultado.getCvc()),
                () -> assertEquals(tarjetaUpdateDto.getFechaCaducidad(), resultado.getFechaCaducidad()),
                () -> assertEquals(tarjetaUpdateDto.getSaldo(), resultado.getSaldo())
        );
    }

    @Test
    void toTarjetaResponseDto() {
        log.info("Haciendo el test de convertir Tarjeta a TarjetaResponseDto");
        Tarjeta tarjeta = Tarjeta.builder()
                .id(1L)
                .numero("1234")
                .cvc("111")
                .fechaCaducidad(LocalDate.of(2025, 12, 31))
                .titular("John Doe")
                .saldo(1000.0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .uuid(UUID.fromString("57727bc2-0c1c-494e-bbaf-e952a778e478"))
                .build();
        var resultado = tarjetaMapper.toTarjetaResponseDto(tarjeta);

        assertAll(
                () -> assertEquals(tarjeta.getId(), resultado.getId()),
                () -> assertEquals(tarjeta.getNumero(), resultado.getNumero()),
                () -> assertEquals(tarjeta.getCvc(), resultado.getCvc()),
                () -> assertEquals(tarjeta.getFechaCaducidad(), resultado.getFechaCaducidad()),
                () -> assertEquals(tarjeta.getTitular(), resultado.getTitular()),
                () -> assertEquals(tarjeta.getSaldo(), resultado.getSaldo())
        );
    }

    @Test
    void testToTarjetaResponseDto() {
    }
}