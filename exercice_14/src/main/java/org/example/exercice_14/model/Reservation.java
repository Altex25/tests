package org.example.exercice_14.model;

public class Reservation {

    private String id;
    private String bookId;
    private String memberId;
    private int position;

    public Reservation(String id, String bookId, String memberId, int position) {
        this.id = id;
        this.bookId = bookId;
        this.memberId = memberId;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
