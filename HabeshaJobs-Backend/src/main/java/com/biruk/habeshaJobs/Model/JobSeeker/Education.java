package com.biruk.habeshaJobs.Model.JobSeeker;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

@Embeddable
public class Education {

    private Degree degree;
    private String fieldOfStudy;
    private String institutionName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double GPA;

    public Education() {
    }

    public Education (Degree degree, String fieldOfStudy, String institutionName, LocalDate startDate, LocalDate endDate, Double GPA) {
        this.degree = degree;
        this.fieldOfStudy = fieldOfStudy;
        this.institutionName = institutionName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.GPA = GPA;
    }

    @Enumerated
    public Degree getDegree() {
        return degree;
    }

    public void setDegree (Degree degree) {
        this.degree = degree;
    }

    public String getFieldOfStudy () {
        return fieldOfStudy;
    }

    public void setFieldOfStudy (String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getGPA() {
        return GPA;
    }

    public void setGPA(Double GPA) {
        this.GPA = GPA;
    }

    public enum Degree {
        Bachelor,
        Masters,
        PhD,
        Diploma,
        Certificate,
        Associate,
        Other
    }

    @Override
    public String toString() {
        return "Education{" +
                "degree=" + degree +
                ", fieldOfStudy='" + fieldOfStudy + '\'' +
                ", institutionName='" + institutionName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", GPA=" + GPA +
                '}';
    }



}
