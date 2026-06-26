package org.example.exercice_14.exception;

public class MemberSuspendedException extends RuntimeException {

    public MemberSuspendedException(String memberId) {
        super("Member is suspended: " + memberId);
    }
}
