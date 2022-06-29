package org.austral.ing.lab1.persistence;

import javax.persistence.EntityManager;

public class MedicalHistories {

    private EntityManager entityManager;

    public MedicalHistories(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
