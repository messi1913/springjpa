package me.sangmessi.account;

import lombok.RequiredArgsConstructor;
import me.sangmessi.exception.NotFoundException;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Objects;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/accounts", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;
    private final AccountValidator validator;

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        try {
            AccountDTO accountDTO = service.getUser(id);
            AccountResource resource = new AccountResource(accountDTO);
            resource.add(linkTo(AccountController.class).slash(id).withSelfRel());
            resource.add(new Link("/docs/account.html#resources-account-get").withRel("profile"));
            resource.add(linkTo(AccountController.class).withRel("update-account"));
            return ResponseEntity.ok(resource);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity getUsers(Pageable pageable, PagedResourcesAssembler<AccountDTO> assembler) {
        //this.
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
