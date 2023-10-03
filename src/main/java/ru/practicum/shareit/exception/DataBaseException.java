
package ru.practicum.shareit.exception;

import lombok.Getter;

@Getter
public class DataBaseException extends RuntimeException {

    private final String message;

    public DataBaseException(String message) {
        this.message = message;
    }
}