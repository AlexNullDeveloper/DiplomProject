package diplom.catalogs.accounts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        String hql = "SELECT A.accId FROM diplom.catalogs.accounts.Acc A";
        Query query = session.createQuery(hql);
        List<String> accList = (List<String>) query.list();

        ObservableList<String> accIds = FXCollections.observableList(accList);
        AccFrame.setAccIds(accIds);

    }

    static Acc getAccDetailsFromDB(String selectedId) {
        Session session = factory.openSession();
        String hql = "FROM diplom.catalogs.accounts.Acc A WHERE A.accId = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", selectedId);
        List<Acc> accList = (List<Acc>) query.list();
        Acc resultAcc = null;
        for (Acc item : accList) {
            resultAcc = item;
        }
        return resultAcc;
    }

    void updateAccDetails(String fullNameOfOwner, String engNameOfOwner, String shortNameOfOwner,
                          String otd, String userReg, String dateZav, String userEdit, String dateEdit,
                          String currentOst, String accId) {
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Acc acc = (Acc) session.get(Acc.class,accId);
            acc.setNameOfOwner(fullNameOfOwner);
            acc.setEngNameOfOwner(engNameOfOwner);
            acc.setShortNameOfOwner(shortNameOfOwner);
            acc.setOtd(new BigInteger(otd));
            acc.setUserReg(userReg);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

            Date dateUserReg = null;
            try {
                dateUserReg = new java.sql.Date(sdf.parse(dateZav).getTime());
            } catch (ParseException e){
                dateUserReg = null;
            }
            acc.setUserRegDate(dateUserReg);
            acc.setUserEdit(userEdit);

            Date dateUserEdit = null;
            try {
                dateUserEdit = new java.sql.Date(sdf.parse(dateEdit).getTime());
            } catch (ParseException e){
                dateUserEdit = null;
            }
            acc.setUserEditDate(dateUserEdit);
            acc.setCurrentOst(new BigDecimal(currentOst.replaceAll(",","")));
            session.update(acc);
            System.out.println("before commit");
            tx.commit();
        } catch (HibernateException e){
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }
}
