package org.example.exercice_14.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.exercice_14.model.Loan;
import org.example.exercice_14.model.Member;
import org.example.exercice_14.model.Reservation;
import org.example.exercice_14.repository.InMemoryLoanRepository;
import org.example.exercice_14.repository.InMemoryMemberRepository;
import org.example.exercice_14.repository.InMemoryReservationRepository;
import org.example.exercice_14.service.ReservationService;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationSteps {

    private final InMemoryLoanRepository loanRepository = new InMemoryLoanRepository();
    private final InMemoryMemberRepository memberRepository = new InMemoryMemberRepository();
    private final InMemoryReservationRepository reservationRepository = new InMemoryReservationRepository();
    private final ReservationService reservationService =
            new ReservationService(reservationRepository, loanRepository, memberRepository);

    private Reservation lastReservation;
    private Reservation servedReservation;
    private RuntimeException caughtException;

    @Given("a book {string} is currently borrowed")
    public void aBookIsCurrentlyBorrowed(String bookId) {
        LocalDate today = LocalDate.now();
        Loan loan = new Loan(UUID.randomUUID().toString(), "holder", bookId, today, today.plusDays(21));
        loanRepository.save(loan);
    }

    @Given("a book {string} is available")
    public void aBookIsAvailable(String bookId) {
    }

    @Given("member {string} is active")
    public void memberIsActive(String memberId) {
        memberRepository.save(new Member(memberId));
    }

    @Given("member {string} is suspended")
    public void memberIsSuspended(String memberId) {
        Member member = new Member(memberId);
        member.setSuspended(true);
        memberRepository.save(member);
    }

    @When("member {string} reserves book {string}")
    public void memberReservesBook(String memberId, String bookId) {
        lastReservation = reservationService.reserve(memberId, bookId);
    }

    @When("member {string} tries to reserve book {string}")
    public void memberTriesToReserveBook(String memberId, String bookId) {
        try {
            lastReservation = reservationService.reserve(memberId, bookId);
        } catch (RuntimeException exception) {
            caughtException = exception;
        }
    }

    @When("book {string} is returned")
    public void bookIsReturned(String bookId) {
        servedReservation = reservationService.fulfillNextReservation(bookId);
    }

    @Then("the reservation is registered at position {int}")
    public void assert_reservation_is_registered_at_position(int position) {
        assertThat(lastReservation).isNotNull();
        assertThat(lastReservation.getPosition()).isEqualTo(position);
    }

    @Then("book {string} has {int} reservations")
    public void assert_book_has_reservations(String bookId, int count) {
        assertThat(reservationService.getReservations(bookId)).hasSize(count);
    }

    @Then("member {string} holds position {int}")
    public void assert_member_holds_position(String memberId, int position) {
        Reservation reservation = reservationService.getReservations("B1").stream()
                .filter(r -> r.getMemberId().equals(memberId))
                .findFirst()
                .orElseThrow();
        assertThat(reservation.getPosition()).isEqualTo(position);
    }

    @Then("member {string} is the next to borrow book {string}")
    public void assert_member_is_the_next_to_borrow(String memberId, String bookId) {
        assertThat(servedReservation).isNotNull();
        assertThat(servedReservation.getMemberId()).isEqualTo(memberId);
        assertThat(servedReservation.getBookId()).isEqualTo(bookId);
    }

    @Then("the reservation is refused")
    public void assert_reservation_is_refused() {
        assertThat(caughtException).isNotNull();
    }
}
