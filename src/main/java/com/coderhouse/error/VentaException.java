package com.coderhouse.error;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class VentaException extends Exception implements Serializable {
    public VentaException(String message) {
        super(message);
    }
}