package net.myphenotype.financialStatements.processing.repo;

import net.myphenotype.financialStatements.processing.entity.ChartOfAccounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChartOfAccountsRepo extends JpaRepository<ChartOfAccounts, Integer> {
}
