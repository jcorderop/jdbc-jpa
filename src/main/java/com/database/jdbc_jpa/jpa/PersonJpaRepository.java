package com.database.jdbc_jpa.jpa;


import com.database.jdbc_jpa.entity.PersonEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public class PersonJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public PersonEntity findById(final int id) {
        return entityManager.find(PersonEntity.class, id);
    }

    public void deleteById(final int id) {
        entityManager.remove(findById(id));
    }

    public PersonEntity update(final PersonEntity person) {
        return entityManager.merge(person);
    }

    public PersonEntity insert(final PersonEntity person) {
        return entityManager.merge(person);
    }

    public List<PersonEntity> findAll() {
        final Query query = entityManager.createNamedQuery("find_all_persons");
        return query.getResultList();
    }
}
