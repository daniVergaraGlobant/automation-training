package api;

import api.models.MyPojo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import java.util.Date;

/**
 * Created by Juan Krzemien on 7/14/2016.
 */
public class OrmStandAlone {

    private static final SessionFactory sessionFactory = new Configuration()
            .addAnnotatedClass(MyPojo.class)
            .buildSessionFactory();

    private static final ThreadLocal<Session> sessions = new ThreadLocal<Session>() {
        @Override
        protected Session initialValue() {
            return sessionFactory.openSession();
        }
    };

    @Test
    public void testSimple2() throws Exception {
        Session session = sessions.get();
        Transaction tx = session.beginTransaction();
        session.save(new MyPojo("Ruso", new Date()));
        session.flush();
        tx.commit();
        session.close();
        sessions.remove();
    }

}