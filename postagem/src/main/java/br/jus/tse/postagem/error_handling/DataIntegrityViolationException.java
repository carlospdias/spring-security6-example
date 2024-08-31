package br.jus.tse.postagem.error_handling;

public class DataIntegrityViolationException extends DataAccessException {

    private static final long serialVersionUID = -8146834359701827537L;

    public DataIntegrityViolationException(String msg) {
        super(msg);
    }

    public DataIntegrityViolationException(String msg, Throwable t) {
        super(msg, t);
    }

}