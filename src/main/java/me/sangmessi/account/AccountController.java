package me.sangmessi.account;

import lombok.RequiredArgsConstructor;
import me.sangmessi.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;
    private final AccountValidator validator;

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        try {
            AccountDTO accountDTO = service.getUser(id);
            return ResponseEntity.ok(accountDTO);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity getUsers(@RequestParam String name) {
//        if(Objects.isNull(name))
//            return ResponseEntity.badRequest().body("Parameter is empty!!");
        try {
            List<AccountDTO> accountDTOList = service.getUsers(name);
            return ResponseEntity.ok(accountDTOList);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody Account account, Errors errors) {
        if(errors.hasErrors())
            return badRequest(errors);

        AccountDTO accountDTO = service.createUser(account);
        return ResponseEntity.ok(accountDTO);
    }

    @PutMapping
    public ResponseEntity saveUser(@RequestBody Account account, Errors errors) {
        if(errors.hasErrors())
            return badRequest(errors);

        validator.validateSave(account, errors);

        if(errors.hasErrors())
            return badRequest(errors);

        try {
            AccountDTO accountDTO = service.saveUser(account);
            return ResponseEntity.ok(accountDTO);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        if(!service.existsUser(id))
            return ResponseEntity.badRequest().body("This id ["+id+"] of Account does not exist!! ");
        service.deleteUser(id);
        return ResponseEntity.ok("Delete ["+id+"] account successfully");
    }

    private ResponseEntity badRequest(Errors errors) {
        //return ResponseEntity.badRequest().body(new ErrorResource(errors));
        return ResponseEntity.badRequest().body(errors.toString());
    }

}
