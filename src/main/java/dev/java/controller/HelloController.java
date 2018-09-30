package dev.java.controller;

import dev.java.BirthDate;
import dev.java.Logging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import org.apache.log4j.Logger;

@Controller
public class HelloController {

    @Value("${application.version}")
    private String buildVersion;

    private Logging logging = new Logging();

    @RequestMapping(method = RequestMethod.GET)
    public String sayHello(HttpServletRequest request) {
        logging.runMe(request);
        return "index";

    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView getBirthday(HttpServletRequest request, ModelMap map) {
        logging.runMe(request);
        String date = request.getParameter("birthDate");
        ModelAndView modelAndView = new ModelAndView("index");
        try {
            BirthDate birthDate = new BirthDate(date);
            modelAndView.addObject("birthdate", birthDate);
            modelAndView.addObject("age", birthDate.getAge(new Date()));
            modelAndView.addObject("daysUntilNextBirthday", birthDate.getNumberOfDaysUntilNextBirthday(new Date()));
        }
        catch (IllegalArgumentException e) {
            modelAndView.addObject("error", "Invalid date format. Try again!");
        }
        modelAndView.addAllObjects(map);
        return modelAndView;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "Kseniya Piliak");
        model.addAttribute("version", buildVersion);
    }
}
