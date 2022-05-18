package net.myphenotype.financialStatements.processing.config;

import net.myphenotype.financialStatements.processing.entity.ChartOfAccounts;
import net.myphenotype.financialStatements.processing.repo.ChartOfAccountsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("h2")
public class ChartOfAccountsLoader implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    ChartOfAccountsRepo chartOfAccountsRepo;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        List<ChartOfAccounts> chartOfAccountsList = new ArrayList<>();

        chartOfAccountsList.add(new ChartOfAccounts("10100","Cash","Asset","Balance Sheet",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("10200","Account Receivables","Asset","Balance Sheet",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("10300","Inventories","Asset","Balance Sheet",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("10400","Prepaid Expenses","Asset","Balance Sheet",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("10500","Current Assets","Asset","Balance Sheet",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("10600","Other Assets","Asset","Balance Sheet",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("10700","Fixed Assets @ Cost","Asset","Balance Sheet",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("10800","Accumulated Depreciation","Asset","Balance Sheet",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("10850","Net Fixed Assets","Asset","Balance Sheet",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("10900","Total Assets","Asset","Balance Sheet",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("20100","Accounts Payable","Liabilities","Balance Sheet",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("20200","Accrued Expenses","Liabilities","Balance Sheet",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("20300","Current Portion of Debt","Liabilities","Balance Sheet",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("20400","Income Taxes Payable","Liabilities","Balance Sheet",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("20450","Current Liabilities","Liabilities","Balance Sheet",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("20500","Long Term Debt","Liabilities","Balance Sheet",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("20910","Capital Stock","Liabilities","Balance Sheet",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("20920","Retained Earnings","Liabilities","Balance Sheet",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("20930","Shareholder's Equity","Liabilities","Balance Sheet",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("20940","Total Liabilities and Equity","Liabilities","Balance Sheet",0.00));

        chartOfAccountsList.add(new ChartOfAccounts("30100","Net Sales","Revenue","Income Statement",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("40100","Cost of Goods Sold","Expenditure","Income Statement",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("40150","Gross Margin","Revenue","Income Statement",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("40200","Sales and Marketing","Expenditure","Income Statement",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("40300","Research and Development","Expenditure","Income Statement",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("40400","General and Administrative","Expenditure","Income Statement",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("40500","Operating Expense","Expenditure","Income Statement",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("40600","Income from Operations","Revenue","Income Statement",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("80100","Net Interest Income","Expenditure","Income Statement",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("80200","Income Taxes","Expenditure","Income Statement",0.00));
        chartOfAccountsList.add(new ChartOfAccounts("90900","Net Income","Revenue","Income Statement",0.00));


        chartOfAccountsRepo.saveAll(chartOfAccountsList);

    }
}
