package es.marius.tarjetas.tarjetas.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TarjetaNotFound extends TarjetaException{

    public TarjetaNotFound(Long id) {
        super("Tarjeta con id " + id + " no encontrada");
    }

    public TarjetaNotFound(UUID uuid) {
        super("Tarjeta con uuid " + uuid + " no encontrada");
    }

}
