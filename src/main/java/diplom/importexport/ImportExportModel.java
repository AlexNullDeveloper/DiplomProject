package diplom.importexport;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

/**
 * Created by a.talismanov on 03.06.2016.
 */
class ImportExportModel {
    private static SessionFactory factory;
    private String username;
    private String inFormat;
    private String tableFrom;
    private Date dateWhen;

    static Logger log = Logger.getLogger(ImportExportModel.class.getName());

    private String outFormat;
    private String tableIn;

    void exportFromDB() {
        Locale.setDefault(new Locale("en", "US"));

        try {
            factory = new AnnotationConfiguration().
                    configure().
                    addPackage("importexport").
                    addAnnotatedClass(Export.class).
                    buildSessionFactory();
        } catch (Throwable th) {
            System.err.println("Не получилось создать sessionFactory object" + th);
        }


        ImportExportModel importExportModel = new ImportExportModel();

        initExportComponents(importExportModel);

        getSysdate(importExportModel);

        importExportModel.getUserName();

//        System.out.println(importExportModel.tableFrom);
//        System.out.println(importExportModel.inFormat);
//        System.out.println(importExportModel.dateWhen);

        String result = "invalid string";
        if (importExportModel.inFormat.equals("CSV")) {
            result = exportDataInCSV(importExportModel.tableFrom);
        } else if (importExportModel.inFormat.equals("JSON")) {
            result = exportDataInJSON(importExportModel.tableFrom);
        } else if (importExportModel.inFormat.equals("XML")) {
            result = exportDataInXML(importExportModel.tableFrom);
        }

        Integer exportID = importExportModel.addExport(importExportModel.tableFrom, importExportModel.inFormat,
                result, importExportModel.dateWhen, importExportModel.username);

    }

    private String exportDataInXML(String tableFrom) {
        String resultXML = "invalid XML";
        Session session = factory.openSession();
        try {
            String hql = "FROM diplom.importexport.Trn";
            Query query = session.createQuery(hql);

            List<Trn> results = (List<Trn>) query.list();
            System.out.println("List to string " + results.toString());
            final String exportPath = "C:\\inout\\export\\xml";
            File exportImportDir = new File(exportPath);
            if (!exportImportDir.exists()) {
//                System.out.println("creating " + exportPath);

                boolean result = false;

                try {
                    exportImportDir.mkdirs();
                    result = true;
                } catch (SecurityException e) {
                    e.printStackTrace();
                }

                if (result)
                    System.out.println("directory created");
            }

            DocumentBuilderFactory documentBuilderFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = null;
            try {
                documentBuilder =
                        documentBuilderFactory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document document = documentBuilder.newDocument();
            String root = "transactions";
            Element rootElement = document.createElement(root);
            document.appendChild(rootElement);


            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(exportPath + "\\" + tableFrom + ".xml"), "utf-8"));
                Trns trns = new Trns();
                for (Trn trn : results) {
                    String stringTransaction = "transaction";
                    Element elementTransaction = document.createElement(stringTransaction);
                    rootElement.appendChild(elementTransaction);


                    String idStr = "id";
                    Element elementId = document.createElement(idStr);
                    elementTransaction.appendChild(elementId);
//                    elementId.appendChild(document.createTextNode(trn.getId().toString()));
                    //long
                    elementId.appendChild(document.createTextNode(String.valueOf(trn.getId())));

                    String dognumStr = "dognum";
                    Element elementDocnum = document.createElement(dognumStr);
                    elementTransaction.appendChild(elementDocnum);
                    elementDocnum.appendChild(document.createTextNode(((Integer) trn.getDognum()).toString()));

                    String dateSuccessStr = "dateSuccess";
                    Element elementDateSuccess = document.createElement(dateSuccessStr);
                    elementTransaction.appendChild(elementDateSuccess);
                    elementDateSuccess.appendChild(document.createTextNode(trn.getDateSuccess().toString()));


                    String accDebStr = "accDeb";
                    Element elementAccDeb = document.createElement(accDebStr);
                    elementTransaction.appendChild(elementAccDeb);
                    elementAccDeb.appendChild(document.createTextNode(trn.getAccDeb()));


                    String curDebStr = "curDeb";
                    Element elementCurDeb = document.createElement(curDebStr);
                    elementTransaction.appendChild(elementCurDeb);
                    elementCurDeb.appendChild(document.createTextNode(trn.getCurDeb()));

                    String accCredStr = "accCred";
                    Element elementaccCred = document.createElement(accCredStr);
                    elementTransaction.appendChild(elementaccCred);
                    elementaccCred.appendChild(document.createTextNode(trn.getAccCred()));

                    String curCredStr = "curCred";
                    Element elementCurCred = document.createElement(curCredStr);
                    elementTransaction.appendChild(elementCurCred);
                    elementCurCred.appendChild(document.createTextNode(trn.getCurCred().toString()));

                    String sumDebStr = "sumDeb";
                    Element elementSumDeb = document.createElement(sumDebStr);
                    elementTransaction.appendChild(elementSumDeb);
                    elementSumDeb.appendChild(document.createTextNode(trn.getSumDeb().toString()));

                    String sumCredStr = "sumCred";
                    Element elementSumCred = document.createElement(sumCredStr);
                    elementTransaction.appendChild(elementSumCred);
                    elementSumCred.appendChild(document.createTextNode(trn.getSumCred().toString()));
                }

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                try {
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(document);
                    StreamResult result = new StreamResult(writer);
                    transformer.transform(source, result);

                } catch (TransformerConfigurationException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
                nu.xom.Document xomDocument = nu.xom.converters.DOMConverter.convert(document);
                resultXML = xomDocument.toXML();
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        if (resultXML.isEmpty())
            throw new RuntimeException("XML problem");

        return resultXML;
    }

    private String exportDataInJSON(String tableFrom) {
        Session session = factory.openSession();

        String resultJson = "invalid json";


        try {
            String hql = "FROM diplom.importexport.Trn";
            Query query = session.createQuery(hql);

            List<Trn> results = (List<Trn>) query.list();
            System.out.println("List to string " + results.toString());

            final String exportPath = "C:\\inout\\export\\json";
            File exportImportDir = new File(exportPath);
            if (!exportImportDir.exists()) {
                System.out.println("creating " + exportPath);

                boolean result = false;

                try {
                    exportImportDir.mkdirs();
                    result = true;
                } catch (SecurityException e) {
                    e.printStackTrace();
                }

                if (result)
                    System.out.println("directory created");
            }

            Gson gson = new Gson();
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(exportPath + "\\" + tableFrom + ".json"), "utf-8"));
                Trns trns = new Trns();
                for (Trn trn : results) {
                    trns.getListOfTrn().add(trn);
                }
                gson.toJson(trns, writer);
                resultJson = gson.toJson(trns);
                System.out.println("before commit;");
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return resultJson;
    }

    private String exportDataInCSV(String tableFrom) {
        System.out.println("exportDataInCSV");
        String resultCSV = "invalid CSV";
        Session session = factory.openSession();


        try {
            String hql = "FROM diplom.importexport.Trn";
            Query query = session.createQuery(hql);

            List<Trn> results = (List<Trn>) query.list();
            System.out.println("List to string " + results.toString());


            final String exportCSV = "C:\\inout\\export\\csv";
            File exportImportDir = new File(exportCSV);
            if (!exportImportDir.exists()) {
                System.out.println("creating " + exportCSV);

                boolean result = false;

                try {
                    exportImportDir.mkdirs();
                    result = true;
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                if (result)
                    System.out.println("directory created");
            }


            BufferedWriter writer = null;
            StringWriter sw = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(exportCSV + "\\" + tableFrom + ".csv"), "utf-8"));
                for (Trn trn : results) {
//                    writer.write(( trn.getId()).toString());
                    writer.write(String.valueOf(trn.getId()));
                    writer.append(';');
                    writer.write(((Integer) trn.getDognum()).toString());
                    writer.append(';');
                    writer.write(trn.getDateSuccess().toString());
                    writer.append(';');
                    writer.append(trn.getAccDeb());
                    writer.append(';');
                    writer.append(trn.getCurDeb());
                    writer.append(';');
                    writer.append(trn.getAccCred());
                    writer.append(';');
                    writer.append(trn.getCurCred());
                    writer.append(';');
                    writer.append(trn.getSumDeb().toString());
                    writer.append(';');
                    writer.append(trn.getSumCred().toString());
                    writer.append(';');

                    writer.newLine();
//                    writer.flush();
                }
//                writer.close();

//                sw = new StringWriter();
//                writer = new BufferedWriter(sw);
//                for (Trn trn : results) {
////                    writer.write(( trn.getId()).toString());
//                    //for long
//                    writer.write(String.valueOf((trn.getId())));
//                    writer.append(';');
//                    writer.write(((Integer) trn.getDognum()).toString());
//                    writer.append(';');
//                    writer.write(trn.getDateSuccess().toString());
//                    writer.append(';');
//                    writer.append(trn.getAccDeb());
//                    writer.append(';');
//                    writer.append(trn.getCurDeb());
//                    writer.append(';');
//                    writer.append(trn.getAccCred());
//                    writer.append(';');
//                    writer.append(trn.getCurCred());
//                    writer.append(';');
//                    writer.append(trn.getSumDeb().toString());
//                    writer.append(';');
//                    writer.append(trn.getSumCred().toString());
//                    writer.append(';');
//
//                    writer.newLine();
//                }


            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        
        Path path = Paths.get("C:\\inout\\export\\csv","TRN.csv");
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (String line : lines){
            sb.append(line + "\n");
        }


//                writer.flush();
//                writer.close();
//                StringBuffer sb = sw.getBuffer();
        resultCSV = sb.toString();
        System.out.println(resultCSV);


        return resultCSV;
    }

    private void getSysdate(ImportExportModel importExportModel) {
        Session session = factory.openSession();
        String sql = "SELECT SYSDATE FROM DUAL";
        SQLQuery query = session.createSQLQuery(sql);

        List<Object> result = (List<Object>) query.list();

        for (Object obj : result) {
            importExportModel.dateWhen = new Date(((Timestamp) obj).getTime());
        }
    }

    private void initExportComponents(ImportExportModel importExportModel) {

        ToggleGroup group = ImportExportSwingFrame.getGroupInFormat();

        if (group.getSelectedToggle() != null) {
            importExportModel.inFormat = ((RadioButton) group.
                    getSelectedToggle()).
                    getText();
        }

        ComboBox<String> comboBox = ImportExportSwingFrame.getFromTableComboBox();

        importExportModel.tableFrom = comboBox.getValue();
    }

    private void getUserName() {
        Session session = factory.openSession();
        String sql = "SELECT USER FROM DUAL";
        SQLQuery query = session.createSQLQuery(sql);
        java.util.List result = query.list();

        for (Object object : result) {
            username = (String) object;
        }
    }

    private Integer addExport(String tableFrom, String exportFormat, String resultOfExport, Date date, String user) {

        Session session = factory.openSession();
        Transaction transaction = null;
        Integer exportID = null;
        try {
            transaction = session.beginTransaction();
            Export export = new Export();
            export.setTableFrom(tableFrom);
            export.setExpFormat(exportFormat);
            export.setExpResult(resultOfExport);
            export.setExpDate(date);
            export.setExpUser(user);
            exportID = (Integer) session.save(export);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
        return exportID;
    }

    void importIntoDB() {

        Locale.setDefault(new Locale("en", "US"));

        try {
            factory = new AnnotationConfiguration().
                    configure().
                    addPackage("importexport").
                    addAnnotatedClass(Export.class).
                    buildSessionFactory();
        } catch (Throwable th) {
            System.err.println("Не получилось создать sessionFactory object" + th);
        }


        ImportExportModel importExportModel = new ImportExportModel();

        initImportComponents(importExportModel);
        getSysdate(importExportModel);

        importExportModel.getUserName();

        String result = "invalid string";

        if (importExportModel.outFormat.equals("CSV")) {
            result = importDataInCSV(importExportModel.tableIn);
        } else if (importExportModel.outFormat.equals("JSON")) {
            result = importDataInJSON(importExportModel.tableIn);
        } else if (importExportModel.outFormat.equals("XML")) {
            result = importDataInXML(importExportModel.tableIn);
        }

        Long importId = importExportModel.addImport(importExportModel.tableIn, importExportModel.outFormat,
                result, importExportModel.dateWhen, importExportModel.username);
    }

    private Long addImport(String tableIn, String impFormat, String result, Date dateWhen, String username) {
        Session session = factory.openSession();
        Transaction transaction = null;
        Long importID = null;
        try {
            transaction = session.beginTransaction();
            Import anImport = new Import();
            anImport.setTableTo(tableIn);
            anImport.setImpFormat(impFormat);
            System.out.println("result before setImpDoc " + result);
            anImport.setImpDoc(result);
            anImport.setImpDate(dateWhen);
            anImport.setUser(username);
            importID = (Long) session.save(anImport);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
        return importID;
    }

    private String importDataInXML(String tableIn) {
        String result = "invlid XML";
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(Trns.class);
            Unmarshaller u = jc.createUnmarshaller();
            FileReader reader = new FileReader("C:\\inout\\import\\xml\\TRN.xml");
            Trns transactions = (Trns) u.unmarshal(reader);
            System.out.println(transactions);
            tx = session.beginTransaction();
            String sql = "insert into TRN values (:id,:dognum,:dateSuccess,:accDeb," +
                    ":curDeb,:accCred,:curCred,:sumDeb,:sumCred)";
            SQLQuery query = session.createSQLQuery(sql);
            for (Trn item : transactions.getListOfTrn()){
//                session.save(item);
                query.setParameter("id", item.getId());
                query.setParameter("dognum", item.getDognum());
                query.setParameter("dateSuccess", item.getDateSuccess());
                query.setParameter("accDeb", item.getAccDeb());
                query.setParameter("curDeb", item.getCurDeb());
                query.setParameter("accCred", item.getAccCred());
                query.setParameter("curCred", item.getCurCred());
                query.setParameter("sumDeb", item.getSumDeb());
                query.setParameter("sumCred", item.getSumCred());
                query.executeUpdate();
            }
            tx.commit();
            System.out.println("after commit;");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (HibernateException e){
            if (tx != null) tx.rollback();
        } finally {
            session.close();
        }

        Path path = Paths.get("C:\\inout\\import\\xml","TRN.xml");
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (String line : lines){
            sb.append(line);
        }


        //TODO: сделать импорт из XML
        return sb.toString();
    }

    private String importDataInJSON(String tableIn) {
        Session session = factory.openSession();
        Transaction tx = null;
        Gson gson = new Gson();
        String result = "invalid json";
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\inout\\import\\json\\" + tableIn + ".json"))) {
            Trns trns = gson.fromJson(br, Trns.class);
            result = gson.toJson(trns);

            List<Trn> trnList = trns.getListOfTrn();
            //System.out.println(trnList);

            tx = session.beginTransaction();

            String sql = "insert into TRN values (:id,:dognum,:dateSuccess,:accDeb," +
                    ":curDeb,:accCred,:curCred,:sumDeb,:sumCred)";
            SQLQuery query = session.createSQLQuery(sql);

            for (Trn item : trnList) {
                query.setParameter("id", item.getId());
                query.setParameter("dognum", item.getDognum());
                query.setParameter("dateSuccess", item.getDateSuccess());
                query.setParameter("accDeb", item.getAccDeb());
                query.setParameter("curDeb", item.getCurDeb());
                query.setParameter("accCred", item.getAccCred());
                query.setParameter("curCred", item.getCurCred());
                query.setParameter("sumDeb", item.getSumDeb());
                query.setParameter("sumCred", item.getSumCred());
                query.executeUpdate();
            }
         //   System.out.println("before commit;");
            tx.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (HibernateException e) {
            if (tx != null) {
                System.out.println("hiberException");
                e.printStackTrace();
                tx.rollback();
            }
        } finally {
            session.close();
        }
        System.out.println("result before quit json " + result);
        return result;
    }

    private String importDataInCSV(String tableIn) {
        String result = "invalid Csv";
        //TODO: сделать импорт из CSV
        return result;
    }

    private void initImportComponents(ImportExportModel importExportModel) {

        ToggleGroup group = ImportExportSwingFrame.getGroupOutFormat();

        if (group.getSelectedToggle() != null) {
            importExportModel.outFormat = ((RadioButton) group.
                    getSelectedToggle()).
                    getText();
        }

        ComboBox<String> comboBox = ImportExportSwingFrame.getInTableComboBox();

        importExportModel.tableIn = comboBox.getValue();
    }
}
