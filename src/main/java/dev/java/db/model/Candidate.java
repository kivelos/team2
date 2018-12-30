package dev.java.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "CANDIDATE", schema = "team6")
public class Candidate extends AbstractEntity {
    private String name;
    private String surname;
    private Date birthday;
    private BigDecimal salaryInDollars;
    private List<CandidateExperience> experiences = new ArrayList<>();
    private List<Skill> skills = new ArrayList<>();
    private CandidateState candidateState;
    private List<Attachment> attachments = new ArrayList<>();
    private List<ContactDetails> contactDetails = new ArrayList<>();
    private List<Interview> interviews = new ArrayList<>();
    private List<Vacancy> vacancies = new ArrayList<>();
    private List<CandidateFeedback> candidateFeedbacks = new ArrayList<>();

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return super.getId();
    }


    @Basic
    @Column(name = "NAME", nullable = false, length = 255)
    @SuppressWarnings("checkstyle:MagicNumber")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "SURNAME", nullable = true, length = 255)
    @SuppressWarnings("checkstyle:MagicNumber")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "BIRTHDAY", nullable = true)
    public Date getBirthday() {
        if (birthday == null) {
            return null;
        }
        return new Date(birthday.getTime());
    }

    public void setBirthday(Date birthday) {
        if (birthday == null) {
            this.birthday = null;
            return;
        }
        this.birthday = new Date(birthday.getTime());
    }

    @Basic
    @Column(name = "SALARY", nullable = true, precision = 2)
    @SuppressWarnings("checkstyle:MagicNumber")
    public BigDecimal getSalaryInDollars() {
        return salaryInDollars;
    }

    public void setSalaryInDollars(BigDecimal salaryInDollars) {
        this.salaryInDollars = salaryInDollars;
    }

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    public List<CandidateExperience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<CandidateExperience> experiences) {
        this.experiences = experiences.stream()
                .peek(experience -> experience.setCandidate(this))
                .collect(Collectors.toList());
    }

    @ManyToMany()
    @JoinTable(name = "CANDIDATE_COMPETENCE",
            joinColumns = {@JoinColumn(name = "CANDIDATE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "SKILL", referencedColumnName = "NAME")}
    )
    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    @ManyToOne
    @JoinColumn(name = "CANDIDATE_STATE", referencedColumnName = "NAME")
    public CandidateState getCandidateState() {
        return candidateState;
    }

    public void setCandidateState(CandidateState candidateState) {
        this.candidateState = candidateState;
    }

    @ElementCollection
    @CollectionTable(
            name = "ATTACHMENT",
            joinColumns = @JoinColumn(name = "CANDIDATE_ID")
    )
    @AttributeOverrides({
            @AttributeOverride(name = "filePath", column = @Column(name = "FILE_PATH",
                    nullable = false, length = 1000)),
            @AttributeOverride(name = "attachmentType", column = @Column(name = "ATTACHMENT_TYPE", nullable = false))
    })
    @SuppressWarnings("checkstyle:MagicNumber")
    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @ElementCollection
    @CollectionTable(
            name = "CONTACT_DETAILS",
            joinColumns = @JoinColumn(name = "CANDIDATE_ID")
    )
    @AttributeOverrides({
            @AttributeOverride(name = "contactType", column = @Column(name = "CONTACT_TYPE", nullable = false)),
            @AttributeOverride(name = "contactDetails", column = @Column(name = "CONTACT_DETAILS",
                    nullable = false, length = 1000))
    })
    @SuppressWarnings("checkstyle:MagicNumber")
    public List<ContactDetails> getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(List<ContactDetails> contactDetails) {
        this.contactDetails = contactDetails;
    }

    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    public List<Interview> getInterviews() {
        return interviews;
    }

    public void setInterviews(List<Interview> interviews) {
        this.interviews = interviews;
    }

    @ManyToMany
    @JoinTable(name = "VACANCY_CANDIDATES",
            joinColumns = {@JoinColumn(name = "CANDIDATE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "VACANCY_ID", referencedColumnName = "ID")}
    )
    @JsonIgnore
    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    public List<CandidateFeedback> getCandidateFeedbacks() {
        return candidateFeedbacks;
    }

    public void setCandidateFeedbacks(List<CandidateFeedback> candidateFeedbacks) {
        this.candidateFeedbacks = candidateFeedbacks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return Objects.equals(name, candidate.name)
               && Objects.equals(surname, candidate.surname)
               && Objects.equals(birthday, candidate.birthday)
               && Objects.equals(salaryInDollars, candidate.salaryInDollars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, birthday);
    }

    @Override
    public String toString() {
        return "Candidate{"
               + "name='" + name + '\''
               + ", surname='" + surname + '\''
               + ", birthday=" + birthday
               + ", salaryInDollars=" + salaryInDollars
               + ", experiences=" + experiences
               + ", skills=" + skills
               + '}';
    }
}
