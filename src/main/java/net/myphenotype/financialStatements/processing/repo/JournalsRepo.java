package net.myphenotype.financialStatements.processing.repo;

import net.myphenotype.financialStatements.processing.entity.Journals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalsRepo extends JpaRepository<Journals,Integer> {

    public List<Journals> findByJournalsRelKey(Integer journalsRelKey);

    public List<Journals> findByJournalStatus(String journalStatus);

}
