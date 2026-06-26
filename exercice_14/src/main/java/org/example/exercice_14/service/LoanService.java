package org.example.exercice_14.service;

import org.example.exercice_14.exception.BookNotAvailableException;
import org.example.exercice_14.exception.LoanNotFoundException;
import org.example.exercice_14.exception.MemberNotFoundException;
import org.example.exercice_14.exception.MemberSuspendedException;
import org.example.exercice_14.model.Loan;
import org.example.exercice_14.model.Member;
import org.example.exercice_14.repository.LoanRepository;
import org.example.exercice_14.repository.MemberRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class LoanService {
    private static final int LOAN_DURATION_DAYS = 21;
    private static final double PENALTY_PER_DAY = 0.15;
    private static final int SIGNIFICANT_DELAY_THRESHOLD_DAYS = 7;
    private static final int MAX_SIGNIFICANT_LATE_RETURNS = 3;

    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;
    private final Clock clock;

    public LoanService(LoanRepository loanRepository, MemberRepository memberRepository, Clock clock) {
        this.loanRepository = loanRepository;
        this.memberRepository = memberRepository;
        this.clock = clock;
    }

    /**
     * Create a loan for a member and a book.
     *
     * @param memberId ID of the member.
     * @param bookId ID of the book.
     * @return The created loan.
     */
    public Loan createLoan(String memberId, String bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        if (member.isSuspended()) {
            throw new MemberSuspendedException(memberId);
        }
        if (loanRepository.existsActiveLoanForBook(bookId)) {
            throw new BookNotAvailableException(bookId);
        }
        LocalDate loanDate = LocalDate.now(clock);
        LocalDate dueDate = loanDate.plusDays(LOAN_DURATION_DAYS);
        Loan loan = new Loan(UUID.randomUUID().toString(), memberId, bookId, loanDate, dueDate);
        return loanRepository.save(loan);
    }

    /**
     * Return a loan.
     *
     * @param loanId ID of the loan to return.
     * @return The loan that was returned.
     */
    public Loan returnLoan(String loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(loanId));
        LocalDate returnDate = LocalDate.now(clock);
        long daysLate = Math.max(0, ChronoUnit.DAYS.between(loan.getDueDate(), returnDate));
        loan.setReturnDate(returnDate);
        loan.setPenalty(daysLate * PENALTY_PER_DAY);
        loanRepository.save(loan);
        if (daysLate > SIGNIFICANT_DELAY_THRESHOLD_DAYS) {
            registerSignificantLateReturn(loan.getMemberId());
        }
        return loan;
    }

    private void registerSignificantLateReturn(String memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        member.setSignificantLateReturns(member.getSignificantLateReturns() + 1);
        if (member.getSignificantLateReturns() >= MAX_SIGNIFICANT_LATE_RETURNS) {
            member.setSuspended(true);
        }
        memberRepository.save(member);
    }
}
