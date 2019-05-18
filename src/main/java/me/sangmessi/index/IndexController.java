package me.sangmessi.index;

import me.sangmessi.account.AccountController;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class IndexController {

    @GetMapping("/")
    public ResourceSupport index(){
        var index = new ResourceSupport();
        index.add(linkTo(AccountController.class).withRel("account"));
        return index;
    }
}
