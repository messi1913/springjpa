package me.sangmessi.account;

import lombok.RequiredArgsConstructor;
import me.sangmessi.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService  implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

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

        accountFromDB.setPassword(this.passwordEncoder.encode(accountFromDB.getPassword()));
        Account saveAccount = accountRepository.save(accountFromDB);
        return modelMapper.map(saveAccount, AccountDTO.class);
    }

    public AccountDTO createUser(Account account) {
        account.setPassword(this.passwordEncoder.encode(account.getPassword()));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(account.getEmail(), account.getPassword(), getAuthorities(account.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<AccountRole> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_"+r.name()))
                .collect(Collectors.toSet());

    }
}
