package com.poojithairosha.ecodeals.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.poojithairosha.ecodeals.util.ApiUrlConstants.CommonUrlConstants.API_V_1;
import static com.poojithairosha.ecodeals.util.ApiUrlConstants.UserUrlConstants.USERS;

@RestController
@RequestMapping(API_V_1 + USERS)
public class UserController {

}
