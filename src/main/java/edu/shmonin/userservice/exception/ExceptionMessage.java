package edu.shmonin.userservice.exception;

import static java.lang.String.format;

public enum ExceptionMessage {

    USER {
        public String getMessage(Long id) {
            return format("There is no user with id=%d in database", id);
        }
    },
    ROLE {
        public String getMessage(Long id) {
            return format("There is no role with id=%d in database", id);
        }
    };

    public abstract String getMessage(Long id);
}
