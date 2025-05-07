package com.biruk.habeshaJobs.Model.Job;

import com.fasterxml.jackson.annotation.JsonMerge;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;

import java.util.List;

/*
The Embeddable annotation is used to define a class whose instance can be embedded in other entities as a part of
their persistent state
    - classes annotated with @Embeddable do not have their own table in a database, Instead there fields are mapped
      to columns in the table of entity that embeds them.
    - @Embeddable class are can be reused in multiple classes,which helps reduce redundancy in your database schema.
    - Therefore, there is no need to use @component and @Entity in Embeddable classes.
*/
@Embeddable
public class JobDescription {

    /*
    @ElementCollection -
        -is used to store multiple values (Collections),
        -is responsible for creating a separate table when storing non-entity collection types like:
            Lists (List<String>, List<Integer>)
            Sets (Set<String>)
            Maps (Map<String, Enum>, Map<String, String>)

    Collections cannot be stored directly in a single table since relational databases work with rows and columns.
    @ElementCollection tells Hibernate to create a dedicated table for the collection data.
    It links this table to the parent entity using a foreign key (e.g., employee_id).

    @CollectionTable annotation
        -the primary purpose of @CollectionTable is to customize the collection table created by @ElementCollection. such as
            - rename the table name
            - Customize the foreign key column name using @JoinColumn.
            - Optionally add additional constraints using @UniqueConstraint or @Index.
    */
    @ElementCollection
    @CollectionTable (name="Job_Responsibilities", joinColumns = @JoinColumn(name="job_ID"))
    @JsonMerge // this is used to merge the responsibilities object when deserializing the JSON object
    /*
    @JsonMerge tells Jackson:
    ➔ "Don't replace the whole thing. Only update fields you actually see in the JSON."
    Without @JsonMerge = full object replacement ❌
    With @JsonMerge = field-by-field updating ✅
    */
    private List<String> responsibilities;

    @ElementCollection
    @CollectionTable (name="Job_Qualification", joinColumns = @JoinColumn(name = "job_Id"))
    @JsonMerge
    private List <String> qualifications;

    /*Deprecated
    *The below field is deprecated because I have to normalize the skillsRequired field in order to efficiently match the jobSeeker skill with job required skill using query.
    * */
//    @ElementCollection
//    @CollectionTable(name="Skills_Required", joinColumns = @JoinColumn(name= "job_Id"))
//    @JsonMerge
//    private List<String> skillsRequired;



    @ElementCollection
    @CollectionTable(name = "Benefits", joinColumns = @JoinColumn(name="job_Id"))
    @JsonMerge
    private List <String> benefits;


    // no args constructor
    public JobDescription() {
    }

    //all args constructor
    public JobDescription(List<String> responsibilities, List<String> qualifications, List<String> benefits) {
        this.responsibilities = responsibilities;
        this.qualifications = qualifications;
//        this.skillsRequired = skillsRequired;
        this.benefits = benefits;
    }

    //Getters and Setters
    public List<String> getResponsibilities(){
        return responsibilities;
    }

    public void setResponsibilities (List<String> responsibilities){
        this.responsibilities = responsibilities;
    }

    public List<String> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<String> qualifications) {
        this.qualifications = qualifications;
    }
//
//    public List<String> getSkillsRequired() {
//        return skillsRequired;
//    }
//
//    public void setSkillsRequired(List<String> skillsRequired) {
//        this.skillsRequired = skillsRequired;
//    }

    public List<String> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }

    @Override
    public String toString() {
        return "JobDescription{" +
                "responsibilities=" + responsibilities +
                ", qualifications=" + qualifications +
//                ", skillsRequired=" + skillsRequired +
                ", benefits=" + benefits +
                '}';
    }
}
