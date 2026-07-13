package com.banco.auth.dto;

import java.util.Map;

public class ErrorResponse {
    private int status;
    private String mensaje;
    private Map<String, String> errores;

    //constructors
    public ErrorResponse(int status, String mensaje, Map<String, String> errores) {
        this.status = status;
        this.mensaje = mensaje;
        this.errores = errores;
    }

    // Getters and Setters
    public int getStatus() { return status; }
    public String getMensaje() { return mensaje; }
    public Map<String, String> getErrores() { return errores; }
}
