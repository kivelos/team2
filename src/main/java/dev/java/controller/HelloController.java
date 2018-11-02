package dev.java.controller;

import dev.java.DateProcessor;
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
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class HelloController {

    @Value("${application.version}")
    private String buildVersion;

    private Logging logging = new Logging();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public final String sayHello(HttpServletRequest request) {
        logging.runMe(request);
        return "index";
    }
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public final ModelAndView getBirthday(HttpServletRequest request, ModelMap map) {
        logging.runMe(request);
        String sBirthDate = request.getParameter("birthDate");
        Date birthDate = DateProcessor.tryParseDate(sBirthDate);
        ModelAndView modelAndView = new ModelAndView("index");
        if (birthDate == null) {
            modelAndView.addObject("error", "Invalid date format. Try again!");
        } else {
            modelAndView.addObject("birthdate", birthDate);
            modelAndView.addObject("age", DateProcessor.calcAge(birthDate));
            modelAndView.addObject("daysUntilNextBirthday", DateProcessor.calcDaysToBirth(birthDate));
        }
        modelAndView.addAllObjects(map);
        return modelAndView;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        Date currentTime=new Date();
        SimpleDateFormat myFormat=new SimpleDateFormat("HH:mm:ss");
        model.addAttribute("time",myFormat.format(currentTime));
        model.addAttribute("author", "Team Liquid");
        model.addAttribute("version", buildVersion);
    }
}
