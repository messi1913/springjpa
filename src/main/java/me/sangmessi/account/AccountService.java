package me.sangmessi.account;

import lombok.RequiredArgsConstructor;
import me.sangmessi.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public AccountDTO getUser(Long id) throws NotFoundException {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isEmpty()){
            throw new NotFoundException("This id ["+id+"] of Account does not exist!! ");
        }
        return modelMapper.map(accountOptional.get(), AccountDTO.class);
    }

    public List<AccountDTO> getUsers(String userName) throws NotFoundException {
        List<Account> accountList = accountRepository.findByUserNameContaining(userName);
        if(Objects.isNull(accountList) || accountList.size() < 1) {
            throw new NotFoundException("This ["+userName+"] of Accounts does not exist!! ");
        }
        Type targetListType = new TypeToken<List<AccountDTO>>() {}.getType();
        return modelMapper.map(accountList, targetListType);
    }

    public AccountDTO saveUser(Account account) throws NotFoundException {
        Optional<Account> accountOptional = accountRepository.findById(account.getId());
        if(accountOptional.isEmpty())
            throw new NotFoundException("This ["+account.getId()+"] of Accounts does not exist!! ");

        Account accountFromDB = accountOptional.get();
        modelMapper.map(account , accountFromDB);

        Account saveAccount = accountRepository.save(accountFromDB);
        return modelMapper.map(saveAccount, AccountDTO.class);
    }

    public AccountDTO createUser(Account account) {
        Account saveAccount = accountRepository.save(account);
        return modelMapper.map(saveAccount, AccountDTO.class);
    }

    public Page<AccountDTO> getUsers(Pageable pageable) {
        Page<Account> accounts = this.accountRepository.findAll(pageable);
        Page<AccountDTO> accountDTOS = accounts.map(this::to);
        return accountDTOS;
    }

    private AccountDTO to(Account account) {
        return modelMapper.map(account, AccountDTO.class);
    }


    public boolean existsUser(Account account) {
        return accountRepository.findById(account.getId()).isPresent();
    }

    public boolean existsUser(Long id) {
        return accountRepository.findById(id).isPresent();
    }

    public void deleteUser(Long id) {
        accountRepository.deleteById(id);
    }
}
