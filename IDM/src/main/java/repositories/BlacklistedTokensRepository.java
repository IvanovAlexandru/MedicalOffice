package repositories;

import models.BlacklistedTokensModel;
import models.UserModel;

import javax.persistence.*;
import java.util.List;

public class BlacklistedTokensRepository {

    public static void addToken(String token){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("user-db");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            BlacklistedTokensModel blacklistedTokensModel = new BlacklistedTokensModel();
            blacklistedTokensModel
                    .setToken(token);
            entityManager.persist(blacklistedTokensModel);

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
    public static List<BlacklistedTokensModel> verifyToken(String token) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("user-db");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            TypedQuery<BlacklistedTokensModel> query = entityManager.createQuery(
                    "SELECT b FROM BlacklistedTokensModel b WHERE b.token = :token", BlacklistedTokensModel.class);
            query.setParameter("token", token);

            return query.getResultList();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
