package net.myphenotype.financialStatements.processing.controller;

import lombok.extern.slf4j.Slf4j;
import net.myphenotype.financialStatements.processing.entity.ChartOfAccounts;
import net.myphenotype.financialStatements.processing.repo.ChartOfAccountsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;
@Slf4j
@Controller
@RequestMapping(path = "/fin")
public class MainController {

    @Autowired
    ChartOfAccountsRepo chartOfAccountsRepo;

    @Autowired
    ChartOfAccounts chartOfAccounts;

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
        //Get the book using the ID from the Service (in turn from DAO and in turn from Table)


        //Set the Customer as the Model Attribute to Prepopulate the Form
        model.addAttribute("chartOfAccount",chartOfAccounts);

        //Send the data to the right form
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
