package menu;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {
    static EntityManagerFactory emf;
    static EntityManager em;
    static final String[] NAMES = {"Grilled chicken fillet", "Caesar with salmon", "Veal medallions", "Cheese plate", "Oysters"};
    static final Random RND = new Random();

    public static void main(String[] args) {
        try {
            // create connection
            emf = Persistence.createEntityManagerFactory("JPAMenuTest");
            em = emf.createEntityManager();
            try {
                Dish test = new Dish("Test", 200.5, 350, 25);
                System.out.println(System.lineSeparator() + "-------------ДОБАВЛЕНИЕ БЛЮДА-------------------------------------------------------------");
                addDish(test);
                insertRandomDishes(5);
                System.out.println(System.lineSeparator() + "-------------МЕНЮ-------------------------------------------------------------");
                viewMenu();
                System.out.println(System.lineSeparator() + "-------------СТОИМОСТЬ БЛЮД ОТ И ДО-------------------------------------------------------------");
                viewMenuPrice(100.2, 500.1);
                System.out.println(System.lineSeparator() + "-------------БЛЮДА СО СКИДКОЙ-------------------------------------------------------------");
                viewMenuSale();
                System.out.println(System.lineSeparator() + "-------------НАБОР БЛЮД ВЕСОМ ДО 1 КГ-------------------------------------------------------------");
                sampleUpToOneKG();

            } finally {
                em.close();
                emf.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    static String randomName() {
        return NAMES[RND.nextInt(NAMES.length)];
    }

    private static void addDish(Dish dish) {

        em.getTransaction().begin();
        try {
            em.persist(dish);
            em.getTransaction().commit();

            System.out.println(dish.getId());
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void insertRandomDishes(int n) {
        em.getTransaction().begin();
        try {
            for (int i = 0; i < n; i++) {
                Dish d = new Dish(randomName(), RND.nextInt(500), RND.nextInt(500), 0);
                if (i % 2 == 0) {
                    d.setSale(10);
                }
                em.persist(d);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void viewMenu() {
        Query query = em.createQuery("SELECT d FROM Dish d", Dish.class);
        List<Dish> list = (List<Dish>) query.getResultList();

        for (Dish d : list)
            System.out.println(d);
    }

    private static void viewMenuPrice(double min, double max) {
        Query query = em.createQuery("SELECT d FROM Dish d WHERE d.price>" + min + " AND d.price<" + max, Dish.class);
        List<Dish> list = (List<Dish>) query.getResultList();

        for (Dish d : list)
            System.out.println(d);

    }

    private static void viewMenuSale() {
        Query query = em.createQuery("SELECT d FROM Dish d WHERE d.sale>0", Dish.class);
        List<Dish> list = (List<Dish>) query.getResultList();

        for (Dish d : list)
            System.out.println(d);

    }

    private static void sampleUpToOneKG() {
        Query query = em.createQuery("SELECT d FROM Dish d", Dish.class);
        List<Dish> list = (List<Dish>) query.getResultList();
        List<Dish> result = new ArrayList<>();
        int n = 0;
        for (Dish d : list) {
            if (n > 1000) {
                result.remove(result.size() - 1);
                break;
            } else if (n >= 900) {
                break;
            }
            n += d.getWeight();
            result.add(d);
        }
        for (Dish d : result) {
            System.out.println(d);
        }
    }
}
