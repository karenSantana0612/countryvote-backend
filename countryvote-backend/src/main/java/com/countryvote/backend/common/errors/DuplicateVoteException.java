package com.countryvote.backend.common.errors;

public class DuplicateVoteException extends RuntimeException {

    public DuplicateVoteException(String email) {
        super("Only one vote is allowed per email: " + email);
    }
}
