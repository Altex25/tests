package org.example.exercice_14.service;

import org.example.exercice_14.model.Loan;
import org.example.exercice_14.repository.LoanRepository;
import org.example.exercice_14.repository.MemberRepository;

import java.time.Clock;

public class LoanService {

    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;
    private final Clock clock;

    public LoanService(LoanRepository loanRepository, MemberRepository memberRepository, Clock clock) {
        this.loanRepository = loanRepository;
        this.memberRepository = memberRepository;
        this.clock = clock;
    }

    public Loan createLoan(String memberId, String bookId) {
        throw new UnsupportedOperationException("TODO");
    }

    public Loan returnLoan(String loanId) {
        throw new UnsupportedOperationException("TODO");
    }
}
