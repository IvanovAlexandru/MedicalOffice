package repositories;

import models.UserModel;
import utils.RoleEnum;

import javax.persistence.*;
import java.util.Optional;

public class UserRepository {

    public static void createUser(String username, String password, RoleEnum roleEnum){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("user-db");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            UserModel user = new UserModel();
            user.setUsername(username);
            user.setPassword(password);
            user.setRoleEnum(roleEnum);

            entityManager.persist(user);

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

    public static UserModel authenticateUser(String username, String password){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("user-db");
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();


            TypedQuery<UserModel> query = entityManager.createQuery(
                    "select u from UserModel u WHERE u.username = :username AND u.password = :password",
                    UserModel.class);

            query.setParameter("username", username);
            query.setParameter("password", password);

            UserModel user = query.getSingleResult();

            return user;
        }
        catch (Exception e){
            return null;
        }
        finally {
            entityManagerFactory.close();
        }
    }
}
