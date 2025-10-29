package es.marius.tarjetas.tarjetas.mappers;

import es.marius.tarjetas.tarjetas.dto.TarjetaCreateDto;
import es.marius.tarjetas.tarjetas.dto.TarjetaResponseDto;
import es.marius.tarjetas.tarjetas.dto.TarjetaUpdateDto;
import es.marius.tarjetas.tarjetas.models.Tarjeta;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class TarjetaMapper {
    public Tarjeta toTarjeta(Long id , TarjetaCreateDto tarjetaCreateDto) {
        return Tarjeta.builder()
                .id(id)
                .numero(tarjetaCreateDto.getNumero())
                .cvc(tarjetaCreateDto.getCvc())
                .fechaCaducidad(tarjetaCreateDto.getFechaCaducidad())
                .titular(tarjetaCreateDto.getTitular())
                .saldo(tarjetaCreateDto.getSaldo())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .uuid(UUID.randomUUID())
                .build();

    }

    public Tarjeta toTarjeta(TarjetaUpdateDto tarjetaUpdateDto, Tarjeta tarjeta) {
        return Tarjeta.builder()
                .id(tarjeta.getId())
                .numero(tarjeta.getNumero() != null ? tarjetaUpdateDto.getNumero() : tarjeta.getNumero())
                .cvc(tarjetaUpdateDto.getCvc() != null ? tarjetaUpdateDto.getCvc() : tarjeta.getCvc())
                .fechaCaducidad(tarjetaUpdateDto.getFechaCaducidad() != null ? tarjetaUpdateDto.getFechaCaducidad() : tarjeta.getFechaCaducidad())
                .titular(tarjeta.getTitular())
                .saldo(tarjetaUpdateDto.getSaldo() != null ? tarjetaUpdateDto.getSaldo() : tarjeta.getSaldo())
                .createdAt(tarjeta.getCreatedAt())
                .updatedAt(tarjeta.getUpdatedAt())
                .uuid(tarjeta.getUuid())
                .build();

    }

    public TarjetaResponseDto toTarjetaResponseDto(Tarjeta tarjeta) {
        return TarjetaResponseDto.builder()
                .id(tarjeta.getId())
                .numero(tarjeta.getNumero())
                .cvc(tarjeta.getCvc())
                .fechaCaducidad(tarjeta.getFechaCaducidad())
                .titular(tarjeta.getTitular())
                .saldo(tarjeta.getSaldo())
                .createdAt(tarjeta.getCreatedAt())
                .updatedAt(tarjeta.getUpdatedAt())
                .uuid(tarjeta.getUuid())
                .build();
    }

    public List<TarjetaResponseDto> toTarjetaResponseDtoList(List<Tarjeta> tarjetas) {
        return tarjetas.stream()
                .map(this::toTarjetaResponseDto)
                .toList();
    }
}
