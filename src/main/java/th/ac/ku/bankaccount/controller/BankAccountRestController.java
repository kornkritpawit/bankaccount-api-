package th.ac.ku.bankaccount.controller;

import org.springframework.web.bind.annotation.*;
import th.ac.ku.bankaccount.data.BankAccountRepository;
import th.ac.ku.bankaccount.model.BankAccount;
import th.ac.ku.bankaccount.model.Money;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/bankaccount")
public class BankAccountRestController {

    private BankAccountRepository repository;

    public BankAccountRestController(BankAccountRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<BankAccount> getAll() {
        return repository.findAll();
    }

    @GetMapping("/customer/{customerId}")
    public List<BankAccount> getAllCustomerId(@PathVariable int customerId) {
        return repository.findByCustomerId(customerId);
    }

    @GetMapping("/{id}")
    public BankAccount getOne(@PathVariable int id) {
        try {
            return repository.findById(id).get();
        } catch (NoSuchElementException e) {
            return  null;
        }
    }



//    @PostMapping
//    public BankAccount create(@RequestBody BankAccount bankAccount){
//        repository.save(bankAccount);
//        return repository.findById(bankAccount.getId()).get();
//    }

    @PostMapping
    public BankAccount create(@RequestBody BankAccount bankAccount) {
        BankAccount record = repository.save(bankAccount);
        repository.flush();
        return record;
    }

    @PutMapping("/{id}")
    public BankAccount update(@PathVariable int id,
                              @RequestBody BankAccount bankAccount) {
        try {
            BankAccount record = repository.findById(id).get();
            record.setBalance(bankAccount.getBalance());
            repository.save(record);
            return record;
        } catch (NoSuchElementException e) {
            return null;
        }

    }

    @PutMapping("/deposit/{id}")
    public BankAccount deposit(@PathVariable int id,
                              @RequestBody Money money) {
        try {
            BankAccount record = repository.findById(id).get();
//        System.out.println(money);
//        System.out.println(money.getMoney());
//        System.out.println(record.getBalance() + money.getMoney());
            record.setBalance(record.getBalance() + money.getMoney());
//        System.out.println(record);
            repository.save(record);
            return record;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @PutMapping("/withdraw/{id}")
    public BankAccount withdraw(@PathVariable int id,
                               @RequestBody Money money) {
        try {
            BankAccount record = repository.findById(id).get();
//        System.out.println(money);
//        System.out.println(money.getMoney());
//        System.out.println(record.getBalance() + money.getMoney());
            record.setBalance(record.getBalance() - money.getMoney());
//        System.out.println(record);
            repository.save(record);
            return record;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public BankAccount delete(@PathVariable int id) {
        try {
            BankAccount record = repository.findById(id).get();
            repository.deleteById(id);
            return record;
        } catch (NoSuchElementException e) {
            return null;
        }

    }
}
