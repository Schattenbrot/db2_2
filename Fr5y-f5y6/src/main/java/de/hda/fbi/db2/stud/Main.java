package de.hda.fbi.db2.stud;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.*;

import javax.persistence.*;

import de.hda.fbi.db2.stud.entity.Answer;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Player;
import de.hda.fbi.db2.stud.entity.Question;
import de.hda.fbi.db2.tools.CsvDataReader;


/**
 * Main Class.
 * @version 0.1.1
 * @since 0.1.0
 * @author A. Hofmann
 * @author B.-A. Mokroß
 */
public class Main {
    /**
     * Main Method and Entry-Point.
     * @param args Command-Line Arguments.
     */

    public static void main(String[] args) {

        // Connect
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("defaultPU");

        EntityManager em = null;                    // Session
        EntityTransaction tx = null;                // Transaction



        try {
            em = emf.createEntityManager();         // Open Session ************************ [EM]
            tx = em.getTransaction();               // i.e. OneToOne Session-Transaction-Coupling
            tx.begin();                             // Open Transaction -------------------- [TX]

            // ObjectLists
            final List<Category> cList = new ArrayList<>();
            final List<Answer> answerList = new ArrayList<>();

            // Read default csv
            final List<String[]> defaultCsvLines = CsvDataReader.read();

            // Read (if available) additional csv-files and default csv-file
            List<String> availableFiles = CsvDataReader.getAvailableFiles();
            for (String availableFile: availableFiles){
                final List<String[]> additionalCsvLines = CsvDataReader.read(availableFile);
            }
            for (int i = 1; i < defaultCsvLines.size(); i++) {
                List<Answer> aList = new ArrayList<>();
                //List<Answer> answerAlreadyUsed = new ArrayList<>();
                boolean[] answerNew = {false, false, false, false};
                for (int j = 2; j < 6; j++) {
                    //List<String> questList = new ArrayList<>();
                    //questList.add(defaultCsvLines.get(i)[1]);
                    Answer answer = new Answer(defaultCsvLines.get(i)[j]);
                    int checkans = checkAnswer(answer, answerList);
                    if (checkans < 0){
                        answerList.add(answer);
                        answerNew[j - 2] = true;
                    } else {
                        answer = answerList.get(checkans);
                    }
                    aList.add(answer);
                    em.persist(answer);
                }

                Question quest = new Question(defaultCsvLines.get(i)[1],
                        Integer.parseInt(defaultCsvLines.get(i)[6]), aList);
                List<Question> qList = new ArrayList<>();
                qList.add(quest);

                for (int j = 0; j < aList.size(); j++) {
                    if (answerNew[j]) {
                        aList.get(j).setAquestion(new ArrayList<Question>());
                    }
                    aList.get(j).getAquestion().add(quest);
                }

                Category cat = new Category(defaultCsvLines.get(i)[7], qList);
                int checkCat = checkCategory(cat, cList);
                if (checkCat < 0) {
                    cList.add(cat);
                } else {
                    cat = cList.get(checkCat);
                    cat.addqList(quest);
                }
                quest.setQcategory(cat);

                cat.setCquestion(new ArrayList<Question>());
                cat.getCquestion().add(quest);

                em.persist(quest);
                em.persist(cat);

            }
            System.out.println("Number of Categories: " + cList.size());
            int count = 0;
            int answercount = 0;
            for (int i = 0; i < cList.size(); i++) {
                count += cList.get(i).getqList().size();
                for (int j = 0; j < cList.get(i).getqList().size(); j++) {
                    answercount += cList.get(i).getqList().get(j).getaList().size();
                }
            }
            System.out.println("Number of questions: " + count);
            System.out.println("Number of unique Answers: " + answerList.size());
            System.out.println("Number of total Answers: " + answercount);

            for (int i = 0; i < cList.size(); i++) {
                System.out.println("Categoryname: " + cList.get(i).getNameOfCategory());
                for (int j = 0; j < cList.get(i).getqList().size(); j++) {
                    System.out.println("\tQuestion " + (j + 1) + ": " +
                            cList.get(i).getqList().get(j).getQuestion());
                    for (int k = 0; k < cList.get(i).getqList().get(j).getaList().size(); k++) {
                        System.out.println("\t\tAnswer: " + (k + 1) + ": " +
                                cList.get(i).getqList().get(j).getaList().get(k).getAnswer());
                    }
                    System.out.println("\t\tSolution : " +
                            cList.get(i).getqList().get(j).getSolution());
                }
            }
/*




            for (int i = 0; i < cList.size(); i++) {
                Category category1;
                category1 = cList.get(i);
                em.persist(category1);
            }
            for (int i = 0; i < questionList.size(); i++) {
                Question question1;
                question1 = questionList.get(i);
                em.persist(question1);
            }
            for (int i = 0; i < answerList.size(); i++) {
                em.persist(answerList.get(i));
            }
*/
            tx.commit();
        } catch (URISyntaxException use) {
            System.out.println(use);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }

        emf.close();
    }

    public static int checkCategory(Category cat, List<Category> categoryList){
        for (int i = 0; i < categoryList.size(); i++) {
            if (cat.getNameOfCategory().contentEquals(categoryList.get(i).getNameOfCategory())){
                return i;
            }
        }
        return -1;
    }
    public static int checkAnswer(Answer ans, List<Answer> ansList){
        for (int i = 0; i < ansList.size(); i++) {
            if (ans.getAnswer().contentEquals(ansList.get(i).getAnswer())) {
                return i;
            }
        }
        return -1;
    }

    public String getGreeting() {
        return "app should have a greeting";
    }
}
