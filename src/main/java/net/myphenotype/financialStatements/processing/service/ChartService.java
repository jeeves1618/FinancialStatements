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
        chartResponsePayload.setPriorAcctPeriodBal(chart.getPriorAcctPeriodBal());
        chartResponsePayload.setAccountTitle(chart.getAccountDescription());
        if (chart.getAccountType().equals("Asset") && increaseOrDecreaseInd.equals("I") ||
                chart.getAccountType().equals("Expenditure") && increaseOrDecreaseInd.equals("I")) {
            chartResponsePayload.setCreditOrDebit("Debit");
        } else {
            chartResponsePayload.setCreditOrDebit("Credit");
        }
        return chartResponsePayload;
    }

    public ChartResponsePayload getIncreaseOrDecrease(String accountNumber, String creditOrDebitInd){

        chart = chartOfAccountsRepo.findByAccountNumber(accountNumber);
        ChartResponsePayload chartResponsePayload =new ChartResponsePayload();
        chartResponsePayload.setPriorAcctPeriodBal(chart.getPriorAcctPeriodBal());
        chartResponsePayload.setAccountTitle(chart.getAccountDescription());
        if (chart.getAccountType().equals("Asset") && creditOrDebitInd.equals("Debit") ||
                chart.getAccountType().equals("Expenditure") && creditOrDebitInd.equals("Debit")||
                chart.getAccountType().equals("Liabilities") && creditOrDebitInd.equals("Credit")||
                chart.getAccountType().equals("Revenue") && creditOrDebitInd.equals("Credit")) {
            chartResponsePayload.setIncreaseOrDecreaseInd("I");
        } else {
            chartResponsePayload.setIncreaseOrDecreaseInd("D");
        }
        return chartResponsePayload;
    }
}
