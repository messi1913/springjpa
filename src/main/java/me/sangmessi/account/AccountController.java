package me.sangmessi.account;

import lombok.RequiredArgsConstructor;
import me.sangmessi.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Type;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/accounts", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;
    private final AccountValidator validator;
    private final ModelMapper modelMapper;

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
        Page<AccountDTO> accounts = this.service.getUsers(pageable);
        var accountResource = assembler.toResource(accounts, p -> new AccountResource(p));
        accountResource.add(new Link("/docs/index.html#resources-accounts-list").withRel("profile"));
        accountResource.add(linkTo(AccountController.class).withRel("get-account"));
        return ResponseEntity.ok(accountResource);
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody Account account, Errors errors) {
        if(errors.hasErrors())
            return badRequest(errors);

        AccountDTO accountDTO = service.createUser(account);
        var accountResource = new AccountResource(accountDTO);
        accountResource.add(linkTo(AccountController.class).withSelfRel());
        accountResource.add(new Link("/docs/index.html#resources-account-create").withRel("profile"));
        accountResource.add(linkTo(AccountController.class).withRel("get-account"));
        return ResponseEntity.ok(accountResource);
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
