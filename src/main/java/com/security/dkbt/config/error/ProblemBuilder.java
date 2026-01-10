package com.security.dkbt.config.error;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public final class ProblemBuilder {

    private ProblemBuilder() {}

    public static ProblemDetail springProblem(
            HttpStatus status,
            String detail,
            String type,
            String origin
    ) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, detail);
        pd.setTitle(status.getReasonPhrase());
        pd.setType(URI.create("https://api.dkbt.com/errors/" + type));
        pd.setProperty("origin", origin);
        return pd;
    }
}

