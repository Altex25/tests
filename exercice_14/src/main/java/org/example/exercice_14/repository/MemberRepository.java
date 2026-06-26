package org.example.exercice_14.repository;

import org.example.exercice_14.model.Member;

import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findById(String id);

    Member save(Member member);
}
