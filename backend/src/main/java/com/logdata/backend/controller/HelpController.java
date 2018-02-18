package com.logdata.backend.controller;

import com.logdata.backend.model.CrashVO;
import com.logdata.backend.model.UserVO;
import com.logdata.backend.repository.CrashDataRepository;
import com.logdata.backend.repository.UserDataRepository;
import com.logdata.backend.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class HelpController {
    @Autowired
    private UserDataRepository userDataRepository;
    @Autowired
    private CrashDataRepository crashDataRepository;

    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String help() {
        return "help";
    }

    @RequestMapping(value = "/mypage", method = RequestMethod.GET)
    public String myPage(Principal user, Model model) {
        if (user == null) {
            return "login";
        }

        String apiKey = this.userDataRepository.findByUserID(user.getName()).getApiKey();
        ArrayList<CrashVO> crashVOArrayList = this.crashDataRepository.findAllByApiKeyOrderByTimeDesc(getUserApiKey(user));
        HashMap<String, Integer> crashList = new HashMap<String, Integer>();

        for (int i = 0; i < crashVOArrayList.size(); i++) {
            String packageName = Utility.findCrashName(crashVOArrayList.get(i).getLogcat());

            if (crashList.get(packageName) == null) {
                crashList.put(packageName, 1);
            } else {
                int count = crashList.get(packageName);
                crashList.put(packageName, ++count);
            }
        }

        model.addAttribute("crashList", crashList);
        model.addAttribute("apikey", apiKey);

        return "mypage";
    }

    public String getUserApiKey(Principal user) {
        UserVO u = this.userDataRepository.findByUserID(user.getName());
        return u.getApiKey();
    }
}