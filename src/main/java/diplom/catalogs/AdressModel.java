package diplom.catalogs;

import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;

import java.util.List;

/**
 * Created by a.talismanov on 06.06.2016.
 */
public class AdressModel {

    private static SessionFactory factory;

    AdressJpa getAdressFromDBbyId() {
        try {
            factory = new AnnotationConfiguration().configure().addPackage("catalogs").addAnnotatedClass(Customer.class).buildSessionFactory();
        } catch (Throwable th) {
            System.err.println("не создался SessionFactory " + th);
        }

        AdressModel adressModel = new AdressModel();
        return adressModel.populateAdressInfo();
    }

    private AdressJpa populateAdressInfo() {
        Session session = factory.openSession();
        String hql = "FROM diplom.catalogs.AdressJpa A where A.id = :address_id";
        Query query = session.createQuery(hql);
        query.setParameter("address_id", Adress.getCurrentAdrId());
        List<AdressJpa> result = (List<AdressJpa>) query.list();
        AdressJpa resultAdr = null;

        for (AdressJpa adr : result) {
            resultAdr = adr;
        }

        return resultAdr;
    }

    public void updateAdresses(String fizAdr, String jurAdr, String pochtAdr) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "UPDATE diplom.catalogs.AdressJpa A set A.fizAdr = :fiz_adr, " +
                    "A.urAdr = :jur_adr, A.pochtAdr = :pocht_adr " +
                    "WHERE A.id = :address_id";
            Query query = session.createQuery(hql);
            query.setParameter("fiz_adr", fizAdr);
            query.setParameter("jur_adr", jurAdr);
            query.setParameter("pocht_adr", pochtAdr);
            query.setParameter("address_id", Adress.getCurrentAdrId());
            int result = query.executeUpdate();
            System.out.println("Row affected: " + result);
            tx.commit();
        } catch (HibernateException e){
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
