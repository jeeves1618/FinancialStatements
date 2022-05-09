package net.myphenotype.financialStatements.processing.service;

import net.myphenotype.financialStatements.processing.domain.ChartResponsePayload;
import net.myphenotype.financialStatements.processing.entity.ChartOfAccounts;
import net.myphenotype.financialStatements.processing.repo.ChartOfAccountsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChartService {

    @Autowired
    private ChartOfAccounts chart;

    @Autowired
    private ChartOfAccountsRepo chartOfAccountsRepo;

    public ChartResponsePayload getCreditOrDebit(String accountNumber, String increaseOrDecreaseInd){

        chart = chartOfAccountsRepo.findByAccountNumber(accountNumber);
        ChartResponsePayload chartResponsePayload =new ChartResponsePayload();

        chartResponsePayload.setAccountTitle(chart.getAccountDescription());
        if (chart.getAccountType().equals("Asset") && increaseOrDecreaseInd.equals("I") ||
                chart.getAccountType().equals("Expenditure") && increaseOrDecreaseInd.equals("I")) {
            chartResponsePayload.setCreditOrDebit("Debit");
        } else {
            chartResponsePayload.setCreditOrDebit("Credit");
        }
        return chartResponsePayload;
    }
}
