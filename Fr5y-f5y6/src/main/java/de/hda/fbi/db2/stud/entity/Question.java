package de.hda.fbi.db2.stud.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 * Question Class.
 * @version 0.1.1
 * @since 0.1.0
 * @author A. Machill
 * @author M. Stuber
 */

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int qId;
    private String question;
    private int solution;
    private List<Answer> aList;

    @ManyToOne
    private Category qcategory;
    @ManyToMany(mappedBy = "aquestion")
    private Collection<Answer> qanswer;

    // Constructors
    public Question() {

    }

    public Question(String question, int solution) {
        this.question = question;
        this.solution = solution;
    }

    public Question(String question, int solution, List<Answer> aList) {
        this.question = question;
        this.solution = solution;
        this.aList = aList;
    }

    // Getter & Setter
    public int getqId() {
        return qId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getSolution() {
        return solution;
    }

    public void setSolution(int solution) {
        this.solution = solution;
    }

    public List<Answer> getaList() {
        return aList;
    }

    public void setQcategory(Category qcategory) {
        this.qcategory = qcategory;
    }

    public Collection<Answer> getQanswer() {
        return qanswer;
    }

    public void setQanswer(Collection<Answer> qanswer) {
        this.qanswer = qanswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Question question1 = (Question) o;
        return qId == question1.qId &&
                solution == question1.solution &&
                Objects.equals(question, question1.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qId, question, solution);
    }

    // String representation
    @Override
    public String toString() {
        return "Question{" +
                "qId=" + qId +
                ", question='" + question + '\'' +
                ", solution=" + solution +
                '}';
    }
}
