package com.example.bcsd.repository;

import com.example.bcsd.model.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    public Optional<Member> findById(Integer id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    public Optional<Member> findByEmail(String email) {
        List<Member> list = em.createQuery("SELECT m FROM Member m WHERE m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
        return list.stream().findFirst();
    }

    public boolean existsById(Integer id) {
        return em.find(Member.class, id) != null;
    }

    public boolean existsByEmail(String email) {
        Long cnt = em.createQuery("SELECT COUNT(m) FROM Member m WHERE m.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return cnt > 0;
    }

    @Transactional
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Transactional
    public Member update(Integer id, Member member) {
        return em.merge(member);
    }

    @Transactional
    public void deleteById(Integer id) {
        Member m = em.find(Member.class, id);
        if (m == null) {
            throw new RuntimeException("삭제할 Member가 없음: " + id);
        }
        em.remove(m);
    }
}
