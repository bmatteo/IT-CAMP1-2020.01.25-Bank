package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.logic.ILogic;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BankController {

    @Autowired
    ILogic service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loginRedirect() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginAction(@RequestParam String login,
                              @RequestParam String pass,
                              Model model) {

        if(!service.login(login, pass)){
            return "redirect:/login";
        }

        model.addAttribute("login", login);

        List<String> lista = new ArrayList<>();
        lista.add("Mateusz");
        lista.add("Janusz");
        lista.add("Karol");
        lista.add("Roman");

        model.addAttribute("listaStringow", lista);

        return "logged";
    }
}
