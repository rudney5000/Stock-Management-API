package com.dedyrudney.gestiondestock.handlers;

import com.dedyrudney.gestiondestock.exception.ErrorCodes;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDTO {

    private Integer httpCode;

    private ErrorCodes codes;

    private String message;

    private List<String> errors = new ArrayList<>();
}
