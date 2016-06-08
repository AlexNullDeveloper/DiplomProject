package diplom.catalogs.accounts;

import diplom.catalogs.CatalogOfClientsSwing;
import diplom.catalogs.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.math.BigInteger;
import java.util.List;

/**
 * @author a.talismanov
 * @version 1.0.0 08.06.2016.
 */
class AccModel {
    private static SessionFactory factory;

    void getAccountsFromDB() {

        factory = new AnnotationConfiguration().configure().addPackage("accounts").addAnnotatedClass(Acc.class).buildSessionFactory();

        AccModel accModel = new AccModel();
        accModel.populateAccList();

    }

    private void populateAccList() {
        Session session = factory.openSession();
        String hql = "FROM diplom.catalogs.accounts.Acc";
        Query query = session.createQuery(hql);
        List<String> accList = (List<String>) query.list();

        ObservableList<String> accIds = FXCollections.observableList(accList);
        //CatalogOfClients.setNumsOfClients(clientIds);
        AccFrame.setAccIds(accIds);

    }
}
