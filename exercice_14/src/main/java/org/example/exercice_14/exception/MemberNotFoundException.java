package org.example.exercice_14.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(String memberId) {
        super("Member not found: " + memberId);
    }
}
