package me.sangmessi.common;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.validation.Errors;


public class ErrorResource extends Resource<Errors> {

    public ErrorResource(Errors content, Link... links) {
        super(content, links);
        //add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
    }
}