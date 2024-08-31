package br.jus.tse.postagem.error_handling;

public class DataAccessException extends RuntimeException {
    private static final long serialVersionUID = 3620199505147655172L;

    public DataAccessException(String msg) {
        super(msg);
    }

    public DataAccessException(String msg, Throwable t) {
        super(msg, t);
    }

}