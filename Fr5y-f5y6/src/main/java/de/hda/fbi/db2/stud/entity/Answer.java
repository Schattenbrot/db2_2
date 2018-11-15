package de.hda.fbi.db2.stud.entity;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 * Answer Class.
 * @version 0.1.1
 * @since 0.1.0
 * @author A. Machill
 * @author M. Stuber
 */

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int aId;
    private String answer;

    @ManyToMany
    private Collection<Question> aquestion;

    //Constructors
    public Answer() {

    }

    public Answer(String answer) {
        this.answer = answer;
    }

    // Getter & Setter
    public int getaId() {
        return aId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Collection<Question> getAquestion() {
        return aquestion;
    }

    public void setAquestion(Collection<Question> aquestion) {
        this.aquestion = aquestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Answer answer1 = (Answer) o;
        return aId == answer1.aId &&
                Objects.equals(answer, answer1.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aId, answer);
    }

    // String representation
    @Override
    public String toString() {
        return "Answer{" +
                "aId=" + aId +
                ", answer='" + answer + '\'' +
                '}';
    }

}
