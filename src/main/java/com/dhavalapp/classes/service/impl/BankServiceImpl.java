package com.dhavalapp.classes.service.impl;

import com.dhavalapp.classes.domain.Bank;
import com.dhavalapp.classes.repository.BankRepository;
import com.dhavalapp.classes.service.BankService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Bank}.
 */
@Service
@Transactional
public class BankServiceImpl implements BankService {

    private final Logger log = LoggerFactory.getLogger(BankServiceImpl.class);

    private final BankRepository bankRepository;

    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public Bank save(Bank bank) {
        log.debug("Request to save Bank : {}", bank);
        return bankRepository.save(bank);
    }

    @Override
    public Optional<Bank> partialUpdate(Bank bank) {
        log.debug("Request to partially update Bank : {}", bank);

        return bankRepository
            .findById(bank.getId())
            .map(existingBank -> {
                if (bank.getAccountName() != null) {
                    existingBank.setAccountName(bank.getAccountName());
                }
                if (bank.getAccountNumber() != null) {
                    existingBank.setAccountNumber(bank.getAccountNumber());
                }
                if (bank.getNotes() != null) {
                    existingBank.setNotes(bank.getNotes());
                }

                return existingBank;
            })
            .map(bankRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bank> findAll() {
        log.debug("Request to get all Banks");
        return bankRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bank> findOne(Long id) {
        log.debug("Request to get Bank : {}", id);
        return bankRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bank : {}", id);
        bankRepository.deleteById(id);
    }
}
