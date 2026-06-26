package org.example.exercice_14.service;

import org.example.exercice_14.exception.BookAvailableException;
import org.example.exercice_14.exception.MemberNotFoundException;
import org.example.exercice_14.exception.MemberSuspendedException;
import org.example.exercice_14.model.Member;
import org.example.exercice_14.model.Reservation;
import org.example.exercice_14.repository.LoanRepository;
import org.example.exercice_14.repository.MemberRepository;
import org.example.exercice_14.repository.ReservationRepository;

import java.util.List;
import java.util.UUID;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              LoanRepository loanRepository,
                              MemberRepository memberRepository) {
        this.reservationRepository = reservationRepository;
        this.loanRepository = loanRepository;
        this.memberRepository = memberRepository;
    }

    public Reservation reserve(String memberId, String bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        if (member.isSuspended()) {
            throw new MemberSuspendedException(memberId);
        }
        if (!loanRepository.existsActiveLoanForBook(bookId)) {
            throw new BookAvailableException(bookId);
        }
        int position = reservationRepository.findByBookId(bookId).size() + 1;
        Reservation reservation = new Reservation(UUID.randomUUID().toString(), bookId, memberId, position);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservations(String bookId) {
        return reservationRepository.findByBookId(bookId);
    }

    public Reservation fulfillNextReservation(String bookId) {
        List<Reservation> queue = reservationRepository.findByBookId(bookId);
        if (queue.isEmpty()) {
            throw new IllegalStateException("No reservation for book: " + bookId);
        }
        Reservation next = queue.get(0);
        reservationRepository.delete(next);
        return next;
    }
}
