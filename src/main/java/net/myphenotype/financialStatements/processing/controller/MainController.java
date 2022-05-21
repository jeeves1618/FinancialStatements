package net.myphenotype.financialStatements.processing.controller;

import lombok.extern.slf4j.Slf4j;
import net.myphenotype.financialStatements.processing.domain.BalanceSheet;
import net.myphenotype.financialStatements.processing.domain.JournalRel;
import net.myphenotype.financialStatements.processing.domain.Ledger;
import net.myphenotype.financialStatements.processing.domain.TrialBalance;
import net.myphenotype.financialStatements.processing.entity.ChartOfAccounts;
import net.myphenotype.financialStatements.processing.entity.Journals;
import net.myphenotype.financialStatements.processing.repo.ChartOfAccountsRepo;
import net.myphenotype.financialStatements.processing.repo.JournalsRepo;
import net.myphenotype.financialStatements.processing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Controller
@RequestMapping(path = "/fin")
public class MainController {

    @Autowired
    ChartOfAccountsRepo chartOfAccountsRepo;

    @Autowired
    ChartOfAccounts chartOfAccounts;

    @Autowired
    ChartService chartService;

    @Autowired
    Journals journals;

    @Autowired
    JournalsRepo journalsRepo;

    @Autowired
    JournalService journalService;

    @Autowired
    JournalRel journalRel;

    @Autowired
    Ledger ledger;

    @Autowired
    LedgerService ledgerService;

    @Autowired
    TrialBalanceService trialBalanceService;

    @Autowired
    BalanceSheetService balanceSheetService;

    @Autowired
    IncomeService incomeService;

    @Autowired
    CashService cashService;

    @GetMapping(path = "/meta/coa")
    public String getChartOfAccounts(Model model){
        List<ChartOfAccounts> chartOfAccountsList = chartOfAccountsRepo.findAll();
        model.addAttribute("chartOfAccts",chartOfAccountsList);
        log.info("chartOfAccounts" + chartOfAccountsList);
        return "chartOfAccounts";
    }

    @GetMapping(path = "/meta/showFormForUpdating")
    public String ShowFormForUpdate(@RequestParam("chartID") int theID, Model model){
        //Get the book using the ID from the Service (in turn from DAO and in turn from Table)
        ChartOfAccounts coaToBeUpdated = chartOfAccountsRepo.getById(theID);

        //Set the Customer as the Model Attribute to Prepopulate the Form
        model.addAttribute("chartOfAccount",coaToBeUpdated);

        //Send the data to the right form
        return "coaForm";
    }

    @GetMapping(path = "/meta/showFormForAdding")
    public String ShowFormForAdd(Model model){
        //Get the book using the ID from the Service (in turn from DAO and in turn from Table)


        //Set the Customer as the Model Attribute to Prepopulate the Form
        model.addAttribute("chartOfAccount",chartOfAccounts);

        //Send the data to the right form
        return "coaForm";
    }

    @PostMapping(path = "/meta/addUpdateChart")
    public String AddBookToList(@ModelAttribute("chartOfAccount") ChartOfAccounts chartOfAccounts){
        chartOfAccountsRepo.save(chartOfAccounts);
        return "redirect:/fin/meta/coa";
    }

    @GetMapping(path = "/meta/showFormForDeleting")
    public String ShowFormForDelete(@RequestParam("chartID") int theID, Model model){
        //Get the Chart Entry using the ID from the Service (in turn from DAO and in turn from Table)
        ChartOfAccounts entryToBeDeleted = chartOfAccountsRepo.getById(theID);

        //Set the Customer as the Model Attribute to Prepopulate the Form
        model.addAttribute("chartOfAccount",entryToBeDeleted);
        log.info(entryToBeDeleted.toString());

        //Send the data to the right form
        return "deleteCoaForm";
    }

    @GetMapping(path = "/meta/delete")
    public String DeleteChartEntry(@RequestParam("chartID") int theID, Model model){
        //Delete the Chart Entry
        log.info("The ID of chart entry to be deleted : " + theID);
        chartOfAccountsRepo.deleteById(theID);
        return "redirect:/fin/meta/coa";
    }

    @GetMapping(path = "/txn/entry")
    public String enterJournals(Model model){
        //Set the Customer as the Model Attribute to Prepopulate the Form
        model.addAttribute("journal",new Journals());
        //Send the data to the right form
        return "journalEntry";
    }

    @GetMapping(path = "/txn/relentry")
    public String enterRelJournals(@RequestParam("journalKey") int theID, Model model){
        //Set the Customer as the Model Attribute to Prepopulate the Form
        log.info("relID : " + theID);
        journals.setJournalsRelKey(theID);
        journals.setJournalsKey(null);
        journals.setAccountNumber(null);
        journals.setIncreaseOrDecreaseInd(null);
        journals.setJournalMessage(null);
        model.addAttribute("journal",journals);
        //Send the data to the right form
        return "journalEntry";
    }

    @GetMapping(path = "/txn/updateForm")
    public String updateJournalsForm(@RequestParam("journalKey") int theID, Model model){
        //Set the Customer as the Model Attribute to Prepopulate the Form
        model.addAttribute("journal",journals);
        //Send the data to the right form
        return "journalEntry";
    }

    @GetMapping(path = "/txn/deleteForm")
    public String deleteJournalsForm(@RequestParam("journalKey") int theID, Model model){
        //Get the Chart Entry using the ID from the Service (in turn from DAO and in turn from Table)
        Journals entryToBeDeleted = journalsRepo.getById(theID);

        //Set the Customer as the Model Attribute to Prepopulate the Form
        model.addAttribute("journal",entryToBeDeleted);
        log.info(entryToBeDeleted.toString());

        //Send the data to the right form
        return "deleteJnlForm";
    }

    @PostMapping(path = "/txn/addUpdateJournal")
    public String addJournal(Model model, @ModelAttribute("journal") Journals journal){
        log.info("journal.getCashAccountNumber() " + journal.getCashAccountNumber());
        if (journal.getCashAccountNumber() != null) {
            journal.setAccountNumber(journal.getCashAccountNumber());
        }
        if (chartService.getStatementType(journal.getAccountNumber()).equals("Cash Flow"))
            journal.setJournalsKey(null);
        journals = journalService.saveJournal(journal);
        log.info("journals.getJournalMessage() = " + journals.getJournalMessage());
        journalRel.setJournalMessage(journals.getJournalMessage());
        if (journal.getAccountNumber().equals("10100") && (journal.getCashAccountNumber() == null))
        {
            journal.setAccountNumber("00");
            model.addAttribute(journal);
            return "cashAccountEntry";
        }

        else
            return "redirect:/fin/txn/listadd";
    }

    @GetMapping(path = "/txn/list")
    public String getJournals(Model model){
        List<Journals> journalsList = journalsRepo.findAll();
        List<JournalRel> journalRels = new ArrayList<>();
        Journals journal;
        if (journalRel.getJournalMessage() != null) journalRels.add(journalRel);
        if (journalsList.isEmpty())
        {
            journal = new Journals();
        } else {
            journal = journalsList.get(journalsList.size()-1);
            log.info("Index " + (journalsList.size()-1));
            log.info("Last Element " + journalsList.get(journalsList.size()-1).toString());
        }

        model.addAttribute("journal",journal);
        model.addAttribute("journals",journalsList);
        return "journalSummary";
    }

    @GetMapping(path = "/txn/oblist")
    public String getOutOfBalance(Model model){
        List<Journals> journalsList = journalsRepo.findByJournalStatus("Pending");
        List<JournalRel> journalRels = new ArrayList<>();
        Journals journal;
        if (journalRel.getJournalMessage() != null) journalRels.add(journalRel);
        if (journalsList.isEmpty())
        {
            journal = new Journals();
        } else {
            journal = journalsList.get(journalsList.size()-1);
            log.info("Index " + (journalsList.size()-1));
            log.info("Last Element " + journalsList.get(journalsList.size()-1).toString());
        }

        model.addAttribute("journal",journal);
        model.addAttribute("journals",journalsList);
        return "journalSummary";
    }

    @GetMapping(path = "/txn/listadd")
    public String getAddReadBack(Model model){
        List<Journals> journalsList = journalsRepo.findAll();
        List<JournalRel> journalRels = new ArrayList<>();
        if (journalRel.getJournalMessage() != null) journalRels.add(journalRel);
        model.addAttribute("journal",journals);
        model.addAttribute("journalRels",journalRels);
        model.addAttribute("journals",journalsList);
        return "journalSummary";
    }

    @GetMapping(path = "/txn/ledger")
    public String getLedger(Model model){
        List<Ledger> ledgerList = ledgerService.getLedgerEntries();
        model.addAttribute("ledgers",ledgerList);
        return "accountLedger";
    }

    @GetMapping(path = "/txn/tbalance")
    public String getTrialBalance(Model model){
        List<TrialBalance> trialBalances = trialBalanceService.getTrialBalanceEntries();
        model.addAttribute("trialbalances", trialBalances);
        return "trialBalance";
    }

    @GetMapping(path = "/rpt/bsheet")
    public String getBalanceSheet(Model model){
        List<BalanceSheet> balanceSheetEntries = balanceSheetService.getBalanceSheetEntries();
        model.addAttribute("balances", balanceSheetEntries);
        return "balanceSheet";
    }


    @GetMapping(path = "/rpt/istmt")
    public String getIncomeStatement(Model model){
        List<BalanceSheet> incomeEntries = incomeService.getIncomeEntries();
        model.addAttribute("balances", incomeEntries);
        return "incomeStatement";
    }

    @GetMapping(path = "/txn/cflist")
    public String getCashFlow(Model model){
        List<BalanceSheet> cashEntries = cashService.getCashEntries();
        model.addAttribute("balances", cashEntries);
        return "cashFlow";
    }
}
