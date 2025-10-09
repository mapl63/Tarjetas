package es.marius.tarjetas.tarjetas.exceptions;

public abstract class TarjetaException extends RuntimeException{
    public TarjetaException(String message) {
        super(message);
    }
}
