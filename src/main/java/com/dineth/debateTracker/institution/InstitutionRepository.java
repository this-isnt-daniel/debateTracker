package com.dineth.debateTracker.institution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    // @Query("SELECT i.id, i.name FROM Institution i " +
    //         "WHERE similarity(i.name, :name) > 0.4 " +
    //         "ORDER BY similarity(i.name, :name) DESC")
    // List<String> findSimilarInstitutions(@Param("name") String name);

    // find institutions containing the name
    List<Institution> findByNameContaining(String name);

    Institution findByName(String institutionName);
}
