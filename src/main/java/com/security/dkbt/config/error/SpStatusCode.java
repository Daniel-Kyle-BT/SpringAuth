package com.security.dkbt.config.error;

import org.springframework.http.HttpStatus;

public enum SpStatusCode {


    OK(200, HttpStatus.OK, false),
    CREATED(201, HttpStatus.CREATED, false),
    
    BAD_REQUEST(400, HttpStatus.BAD_REQUEST, true),
    UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED, true),
    FORBIDDEN(403, HttpStatus.FORBIDDEN, true),
    NOT_FOUND(404, HttpStatus.NOT_FOUND, true),
    CONFLICT(409, HttpStatus.CONFLICT, true),

    INTERNAL_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, false);

    private final int spCode;
    private final HttpStatus httpStatus;
    private final boolean businessError;

    SpStatusCode(int spCode, HttpStatus httpStatus, boolean businessError) {
        this.spCode = spCode;
        this.httpStatus = httpStatus;
        this.businessError = businessError;
    }

    public int getSpCode() {
        return spCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public boolean isBusinessError() {
        return businessError;
    }
    
    public boolean isSuccess() {
        return this == OK || this == CREATED;
    }
    
    public boolean isError() {
        return !isSuccess();
    }

    public static SpStatusCode from(int code) {
        for (SpStatusCode v : values()) {
            if (v.spCode == code) {
                return v;
            }
        }
        return INTERNAL_ERROR;
    }
}
