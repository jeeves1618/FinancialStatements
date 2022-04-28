package net.myphenotype.financialStatements.processing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/fin")
public class MainController {

    @GetMapping(path = "/meta/coa")
    public String getChartOfAccounts(){
        return "chartOfAccounts";
    }

    @GetMapping(path = "/txn/entry")
    public String enterJournals(){
        return "journalEntry";
    }

    @GetMapping(path = "/txn/list")
    public String getJournals(){
        return "journalSummary";
    }

    @GetMapping(path = "/txn/ledger")
    public String getLedger(){
        return "accountLedger";
    }

    @GetMapping(path = "/txn/tbalance")
    public String getTrialBalance(){
        return "trialBalance";
    }

    @GetMapping(path = "/rpt/bsheet")
    public String getBalanceSheet(){
        return "balanceSheet";
    }


    @GetMapping(path = "/rpt/istmt")
    public String getIncomeStatement(){
        return "incomeStatement";
    }

    @GetMapping(path = "/rpt/entry")
    public String getCashFlow(){
        return "cashFlow";
    }
}
