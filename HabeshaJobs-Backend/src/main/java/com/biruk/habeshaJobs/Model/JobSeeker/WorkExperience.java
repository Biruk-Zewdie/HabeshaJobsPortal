package com.biruk.habeshaJobs.Model.JobSeeker;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class WorkExperience {

    private String companyName;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> responsibilities = new ArrayList<>();
    private String achievements;

    public WorkExperience() {

    }

    public WorkExperience(String companyName, String position, LocalDate startDate, LocalDate endDate, List<String> responsibilities, String achievements) {
        this.companyName = companyName;
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
        this.responsibilities = responsibilities;
        this.achievements = achievements;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public List<String> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(List<String> responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    @Override
    public String toString() {
        return "WorkExperience{" +
                "companyName='" + companyName + '\'' +
                ", position ='" + position + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", responsibilities=" + responsibilities +
                ", achievements='" + achievements + '\'' +
                '}';
    }
}
