package org.example.exercice_14.service;

import org.example.exercice_14.exception.BookNotAvailableException;
import org.example.exercice_14.exception.LoanNotFoundException;
import org.example.exercice_14.exception.MemberNotFoundException;
import org.example.exercice_14.exception.MemberSuspendedException;
import org.example.exercice_14.model.Loan;
import org.example.exercice_14.model.Member;
import org.example.exercice_14.repository.LoanRepository;
import org.example.exercice_14.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    private static final ZoneOffset ZONE = ZoneOffset.UTC;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private MemberRepository memberRepository;

    private final LocalDate today = LocalDate.of(2026, 1, 15);
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        Clock clock = Clock.fixed(today.atStartOfDay(ZONE).toInstant(), ZONE);
        loanService = new LoanService(loanRepository, memberRepository, clock);
    }

    @Test
    void assertCreateLoanReturnsLoanForGivenMemberAndBook() {
        when(memberRepository.findById("M1")).thenReturn(Optional.of(new Member("M1")));
        when(loanRepository.existsActiveLoanForBook("B1")).thenReturn(false);
        when(loanRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Loan loan = loanService.createLoan("M1", "B1");

        assertThat(loan.getMemberId()).isEqualTo("M1");
        assertThat(loan.getBookId()).isEqualTo("B1");
        assertThat(loan.getLoanDate()).isEqualTo(today);
    }

    @Test
    void assertCreateLoanSetsDueDate21DaysAfterLoanDate() {
        when(memberRepository.findById("M1")).thenReturn(Optional.of(new Member("M1")));
        when(loanRepository.existsActiveLoanForBook("B1")).thenReturn(false);
        when(loanRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Loan loan = loanService.createLoan("M1", "B1");

        assertThat(loan.getDueDate()).isEqualTo(today.plusDays(21));
    }

    @Test
    void assertCreateLoanPersistsTheLoan() {
        when(memberRepository.findById("M1")).thenReturn(Optional.of(new Member("M1")));
        when(loanRepository.existsActiveLoanForBook("B1")).thenReturn(false);
        when(loanRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        loanService.createLoan("M1", "B1");

        verify(loanRepository).save(any(Loan.class));
    }

    @Test
    void assertCreateLoanThrowsWhenMemberDoesNotExist() {
        when(memberRepository.findById("M1")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loanService.createLoan("M1", "B1"))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void assertCreateLoanThrowsWhenBookAlreadyBorrowed() {
        when(memberRepository.findById("M1")).thenReturn(Optional.of(new Member("M1")));
        when(loanRepository.existsActiveLoanForBook("B1")).thenReturn(true);

        assertThatThrownBy(() -> loanService.createLoan("M1", "B1"))
                .isInstanceOf(BookNotAvailableException.class);
    }

    @Test
    void assertCreateLoanThrowsWhenMemberIsSuspended() {
        Member suspended = new Member("M1");
        suspended.setSuspended(true);
        when(memberRepository.findById("M1")).thenReturn(Optional.of(suspended));

        assertThatThrownBy(() -> loanService.createLoan("M1", "B1"))
                .isInstanceOf(MemberSuspendedException.class);
    }

    @Test
    void assertReturnLoanOnTimeHasNoPenalty() {
        Loan loan = new Loan("L1", "M1", "B1", today.minusDays(21), today);
        when(loanRepository.findById("L1")).thenReturn(Optional.of(loan));
        when(loanRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Loan returned = loanService.returnLoan("L1");

        assertThat(returned.getReturnDate()).isEqualTo(today);
        assertThat(returned.getPenalty()).isEqualTo(0.0);
        verifyNoInteractions(memberRepository);
    }

    @Test
    void assertReturnLoanLateComputesPenaltyOfFifteenCentsPerDay() {
        Loan loan = new Loan("L1", "M1", "B1", today.minusDays(25), today.minusDays(4));
        when(loanRepository.findById("L1")).thenReturn(Optional.of(loan));
        when(loanRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Loan returned = loanService.returnLoan("L1");

        assertThat(returned.getPenalty()).isCloseTo(0.60, within(0.0001));
    }

    @Test
    void assertReturnLoanThrowsWhenLoanDoesNotExist() {
        when(loanRepository.findById("L1")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loanService.returnLoan("L1"))
                .isInstanceOf(LoanNotFoundException.class);
    }

    @Test
    void assertSignificantLateReturnIncrementsMemberCounter() {
        Loan loan = new Loan("L1", "M1", "B1", today.minusDays(31), today.minusDays(10));
        Member member = new Member("M1");
        when(loanRepository.findById("L1")).thenReturn(Optional.of(loan));
        when(loanRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(memberRepository.findById("M1")).thenReturn(Optional.of(member));

        loanService.returnLoan("L1");

        assertThat(member.getSignificantLateReturns()).isEqualTo(1);
        assertThat(member.isSuspended()).isFalse();
        verify(memberRepository).save(member);
    }

    @Test
    void assertMinorLateReturnDoesNotCountAsSignificant() {
        Loan loan = new Loan("L1", "M1", "B1", today.minusDays(26), today.minusDays(5));
        when(loanRepository.findById("L1")).thenReturn(Optional.of(loan));
        when(loanRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        loanService.returnLoan("L1");

        verifyNoInteractions(memberRepository);
    }

    @Test
    void assertMemberBecomesSuspendedOnThirdSignificantLateReturn() {
        Loan loan = new Loan("L1", "M1", "B1", today.minusDays(31), today.minusDays(10));
        Member member = new Member("M1");
        member.setSignificantLateReturns(2);
        when(loanRepository.findById("L1")).thenReturn(Optional.of(loan));
        when(loanRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(memberRepository.findById("M1")).thenReturn(Optional.of(member));

        loanService.returnLoan("L1");

        assertThat(member.getSignificantLateReturns()).isEqualTo(3);
        assertThat(member.isSuspended()).isTrue();
        verify(memberRepository).save(member);
    }

    @Test
    void assertMemberBelowThresholdIsNotSuspended() {
        Loan loan = new Loan("L1", "M1", "B1", today.minusDays(31), today.minusDays(10));
        Member member = new Member("M1");
        member.setSignificantLateReturns(1);
        when(loanRepository.findById("L1")).thenReturn(Optional.of(loan));
        when(loanRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(memberRepository.findById("M1")).thenReturn(Optional.of(member));

        loanService.returnLoan("L1");

        assertThat(member.getSignificantLateReturns()).isEqualTo(2);
        assertThat(member.isSuspended()).isFalse();
        verify(memberRepository).save(member);
    }
}
