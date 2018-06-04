package com.logdata.web.controller;

import com.logdata.common.model.LogDataInfoVO;
import com.logdata.common.model.UserVO;
import com.logdata.web.service.RestAPIManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class MainController {
    private final RestAPIManager restAPIManager;

    @Autowired
    public MainController(RestAPIManager restAPIManager) {
        this.restAPIManager = restAPIManager;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Principal user, Model model) {
        if (user == null) {
            return "index";
        } else {
            LogDataInfoVO[] logDataInfoList = restAPIManager.getLogDataInfo(getUserApiKey(user.getName()));

            model.addAttribute("logDataInfoList", logDataInfoList);

            return "index";
        }
    }

    public String getUserApiKey(String name) {
        UserVO u = this.restAPIManager.findUser(name);
        return u.getApiKey();
    }
}
