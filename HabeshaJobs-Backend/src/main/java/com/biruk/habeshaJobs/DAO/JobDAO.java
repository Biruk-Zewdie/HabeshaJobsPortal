package com.biruk.habeshaJobs.DAO;

import com.biruk.habeshaJobs.Model.Job.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobDAO extends JpaRepository<Job, UUID> {

    Optional<List<Job>> findByEmployer_EmployerId(UUID employerId);

    //the below methods are used to filter the job based on different criteria.

    /*
    findByJobTitle(String title)           ---> Exact match (case-sensitive)
    findByJobTitleIgnoreCase(String title) ---> Exact match (case-insensitive)
    findByJobTitleContaining(String title) ---> Partial match (case-sensitive)
    findByJobTitleContainingIgnoreCase(String title) ----> Partial match (case-insensitive)
    */

    List <Job> findByJobTitleContainingIgnoreCase(String jobTitle);
    List <Job> findBySalaryBetween(double minSalary, double maxSalary);
    List <Job> findByJobType (Job.JobType jobType);


//    @Query ("SELECT j FROM job j JOIN j.jobDescription.skillsRequired s WHERE LOWER (s) LIKE LOWER (CONCAT('%', :skill, '%')) ")
//    List <Job> findBySkill(String skillsRequired);


    List <Job> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List <Job> findByEmployer_CompanyNameContainingIgnoreCase(String CompanyName);
    List <Job> findByEmployer_IndustrySectorContainingIgnoreCase(String industrySector);

    /*This query returns all rows from the job table where the location is within the specified radius of the given point (lat, lon).
     * ST_DWithin is a PostGIS function that checks if two geometries are within a specified distance of each other.
     * ST_MakePoint creates a point geometry from the given latitude and longitude.
     * ::geography converts the geometry to geography type, which is used for distance calculations on the Earth's surface.
     * The radius is specified in meters.
     * Single colon (:) is used to indicate that the parameter is a named parameter and it will be replaced by the actual value at runtime.
     * double colon (::) is used to cast the geometry to geography type.
     * nativeQuery = true indicates that this is a native SQL query, not a JPQL query.
     */
    @Query(value = "SELECT * FROM jspschema.job j WHERE j.location IS NOT NULL AND ST_DWithin(j.location, ST_MakePoint(:lon, :lat)::geography, :radius)", nativeQuery = true)
    List <Job> findByNearbyJobs (@Param("lat") double lat,
                                 @Param("lon") double lon,
                                 @Param("radius") double radius);


}
