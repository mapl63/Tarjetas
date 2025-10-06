package es.marius.tarjetas.tarjetas.exceptions;

import es.marius.tarjetas.tarjetas.models.Tarjeta;

public abstract class TarjetaException extends RuntimeException{
    public TarjetaException(String message) {
        super(message);
    }
}
