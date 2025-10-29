package es.marius.tarjetas.tarjetas.services;

import es.marius.tarjetas.tarjetas.dto.TarjetaCreateDto;
import es.marius.tarjetas.tarjetas.dto.TarjetaResponseDto;
import es.marius.tarjetas.tarjetas.exceptions.TarjetaBadUuidException;
import es.marius.tarjetas.tarjetas.exceptions.TarjetaNotFound;
import es.marius.tarjetas.tarjetas.mappers.TarjetaMapper;
import es.marius.tarjetas.tarjetas.models.Tarjeta;
import es.marius.tarjetas.tarjetas.repositories.TarjetasRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.*;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;


@Slf4j
@ExtendWith(MockitoExtension.class)
class TarjetasServiceImplTest {

    private final Tarjeta tarjeta1 = Tarjeta.builder()
            .id(1L)
            .numero("1234-5678-1234-5678")
            .cvc("555")
            .fechaCaducidad(LocalDate.of(2025,12,31))
            .titular("Jose")
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
            .titular("Juan")
            .saldo(100.0)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .uuid(UUID.fromString("b36835eb-e56a-4023-b058-52bfa600fee5"))
            .build();

    private final TarjetaResponseDto tarjetaResponse1 = TarjetaResponseDto.builder()
            .id(1L)
            .numero("1234-5678-1234-5678")
            .cvc("555")
            .fechaCaducidad(LocalDate.of(2025,12,31))
            .titular("Jose")
            .saldo(100.0)
            .build();

    private final TarjetaResponseDto tarjetaResponse2 = TarjetaResponseDto.builder()
            .id(2L)
            .numero("4321-5678-1234-5678")
            .cvc("555")
            .fechaCaducidad(LocalDate.of(2025,12,31))
            .titular("Juan")
            .saldo(100.0)
            .build();

    @Mock
    private TarjetasRepository tarjetasRepository;

    @Mock
    private TarjetaMapper tarjetaMapper;

    @InjectMocks
    private TarjetasServiceImpl tarjetasService;

    @Captor
    private ArgumentCaptor<Tarjeta> tarjetaCaptor;

    @Test
    void findAllSinPasarleNingunParametro() {
        log.info("Haciendo el test de findAll sin pasarle ningun parametro");

        List<Tarjeta> expectTarjetas = Arrays.asList(tarjeta1, tarjeta2);

        List<TarjetaResponseDto> expectTarjetasResponseDto =
                Arrays.asList(tarjetaResponse1, tarjetaResponse2);

        when(tarjetasRepository.findAll()).thenReturn(expectTarjetas);
        when(tarjetaMapper.toTarjetaResponseDtoList(anyList())).thenReturn(expectTarjetasResponseDto);

        List<TarjetaResponseDto> actualTarjetasResponse =
                tarjetasService.findAll(null, null);

        assertIterableEquals(expectTarjetasResponseDto, actualTarjetasResponse);

        verify(tarjetasRepository, times(1)).findAll();
        verify(tarjetaMapper, times(1)).toTarjetaResponseDtoList(anyList());

    }

    @Test
    void findAllConParametroNumero() {
        log.info("Haciendo el test de findAll con parametro numero");

        String numero = "1234-5678-1234-5678";
        List<Tarjeta> expectTarjetas = List.of(tarjeta1);

        List<TarjetaResponseDto> expectTarjetasResponseDto =
                List.of(tarjetaResponse1);
        when(tarjetasRepository.findAllByNumero(numero)).thenReturn(expectTarjetas);
        when(tarjetaMapper.toTarjetaResponseDtoList(anyList())).thenReturn(expectTarjetasResponseDto);

        List<TarjetaResponseDto> actualTarjetasResponse =
                tarjetasService.findAll(numero, null);

        assertIterableEquals(expectTarjetasResponseDto, actualTarjetasResponse);

        verify(tarjetasRepository, times(1)).findAllByNumero(numero);
        verify(tarjetaMapper, times(1)).toTarjetaResponseDtoList(anyList());

    }

    @Test
    void findAllConParametroTitular() {
        log.info("Haciendo el test de findAll con parametro titular");

        String titular = "Juan";
        List<Tarjeta> expectTarjetas = List.of(tarjeta2);

        List<TarjetaResponseDto> expectTarjetasResponseDto =
                List.of(tarjetaResponse2);
        when(tarjetasRepository.findAllByTitular(titular)).thenReturn(expectTarjetas);
        when(tarjetaMapper.toTarjetaResponseDtoList(anyList())).thenReturn(expectTarjetasResponseDto);

        List<TarjetaResponseDto> actualTarjetasResponse =
                tarjetasService.findAll(null, titular);

        assertIterableEquals(expectTarjetasResponseDto, actualTarjetasResponse);

        verify(tarjetasRepository, times(1)).findAllByTitular(titular);
        verify(tarjetaMapper, times(1)).toTarjetaResponseDtoList(anyList());
    }

    @Test
    void findAllConNombreYTitular() {
        log.info("Haciendo el test de findAll con ambos parametros");

        String numero = "4321-5678-1234-5678";
        String titular = "Juan";
        List<Tarjeta> expectTarjetas = List.of(tarjeta2);

        List<TarjetaResponseDto> expectTarjetasResponseDto =
                List.of(tarjetaResponse2);
        when(tarjetasRepository.findAllByNumeroAndTitular(numero, titular)).thenReturn(expectTarjetas);
        when(tarjetaMapper.toTarjetaResponseDtoList(anyList())).thenReturn(expectTarjetasResponseDto);

        List<TarjetaResponseDto> actualTarjetasResponse =
                tarjetasService.findAll(numero, titular);

        assertIterableEquals(expectTarjetasResponseDto, actualTarjetasResponse);

        verify(tarjetasRepository, times(1)).findAllByNumeroAndTitular(numero, titular);
        verify(tarjetaMapper, times(1)).toTarjetaResponseDtoList(anyList());
    }


    @Test
    void findByIdParametroValido() {
        log.info("Haciendo el test de findByIdParametro valido");
        Long id = 1L;
        TarjetaResponseDto expectedTarjetaResponse = tarjetaResponse1;
        when(tarjetasRepository.findById(id)).thenReturn(Optional.of(tarjeta1));
        when(tarjetaMapper.toTarjetaResponseDto(any(Tarjeta.class))).thenReturn(expectedTarjetaResponse);

        TarjetaResponseDto actualTarjetaResponse = tarjetasService.findById(id);

        assertEquals(expectedTarjetaResponse, actualTarjetaResponse);

        verify(tarjetasRepository, times(1)).findById(id);
        verify(tarjetaMapper, times(1)).toTarjetaResponseDto(any(Tarjeta.class));

    }

    @Test
    void findByIdParametroInvalido() {
        log.info("Haciendo el test de findByIdParametro invalido");

        Long id = 1L;
        when(tarjetasRepository.findById(id)).thenReturn(Optional.empty());

        var resultado = assertThrows(TarjetaNotFound.class, () -> tarjetasService.findById(id));
        assertEquals("Tarjeta con id " + id + " no encontrada", resultado.getMessage());

        verify(tarjetasRepository, times(1)).findById(id);
    }

    @Test
    void findbyUuidConParametroInvalido() {
        log.info("Haciendo el test de findByUuid con parametro invalido");

        String uuid = "57727bc2-0c1c-494e-bbaf-e952a778e478";

        var resultado = assertThrows(TarjetaBadUuidException.class, () -> tarjetasService.findByUuid(uuid));
        assertEquals("El UUID " + uuid + " no es valido", resultado.getMessage());

        verify(tarjetasRepository, times(1)).findByUuid(UUID.fromString(uuid));
    }

    @Test
    void findbyUuidConParametroValido() {
        log.info("Haciendo el test de findByUuid con parametro valido");
        UUID expectedUuid = tarjeta1.getUuid();

        TarjetaResponseDto expectedTarjetaResponseDto = tarjetaResponse1;

        when(tarjetasRepository.findByUuid(expectedUuid)).thenReturn(Optional.of(tarjeta1));
        when(tarjetaMapper.toTarjetaResponseDto(any(Tarjeta.class))).thenReturn(expectedTarjetaResponseDto);

        TarjetaResponseDto actualTarjetaResponseDto = tarjetasService.findByUuid(expectedUuid.toString());

        assertEquals(expectedTarjetaResponseDto, actualTarjetaResponseDto);

        verify(tarjetasRepository, times(1)).findByUuid(expectedUuid);
        verify(tarjetaMapper, times(1)).toTarjetaResponseDto(any(Tarjeta.class));

    }


    @Test
    void saveTarjetaConValidosParametros() {
        log.info("Haciendo el test de saveTarjeta validos");

        TarjetaCreateDto tarjetaCreateDto = TarjetaCreateDto.builder()
                .numero("5678")
                .cvc("111")
                .fechaCaducidad(LocalDate.of(2023,7,11))
                .titular("Marius")
                .saldo(1500.0)
                .build();

        Tarjeta expectedTarjeta = Tarjeta.builder()
                .id(3L)
                .numero("5678")
                .cvc("111")
                .fechaCaducidad(LocalDate.of(2023,7,11))
                .titular("Marius")
                .saldo(1500.0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .uuid(UUID.randomUUID())
                .build();

        TarjetaResponseDto expectedTarjetaResponseDto = TarjetaResponseDto.builder()
                .id(3L)
                .numero("5678")
                .cvc("111")
                .fechaCaducidad(LocalDate.of(2023,7,11))
                .titular("Marius")
                .saldo(1500.0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .uuid(expectedTarjeta.getUuid())
                .build();

        when(tarjetasRepository.nextId()).thenReturn(3L);
        when(tarjetaMapper.toTarjeta(3L, tarjetaCreateDto)).thenReturn(expectedTarjeta);
        when(tarjetasRepository.save(any(Tarjeta.class))).thenReturn(expectedTarjeta);
        when(tarjetaMapper.toTarjetaResponseDto(any(Tarjeta.class))).thenReturn(expectedTarjetaResponseDto);

        TarjetaResponseDto actualTarjetaResponseDto = tarjetasService.save(tarjetaCreateDto);

        assertEquals(expectedTarjetaResponseDto, actualTarjetaResponseDto);

        verify(tarjetasRepository, times(1)).nextId();
        verify(tarjetasRepository, times(1)).save(tarjetaCaptor.capture());
        verify(tarjetaMapper, times(1)).toTarjeta(3L, tarjetaCreateDto);
        verify(tarjetaMapper, times(1)).toTarjetaResponseDto(any(Tarjeta.class));

    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}