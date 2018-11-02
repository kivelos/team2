package dev.java.controller;

import dev.java.Logging;
import dev.java.db.ConnectorDB;
import dev.java.db.daos.VacancyDao;
import dev.java.db.model.Vacancy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Controller
public class VacancyController {
    private Logging logging = new Logging();
    private static boolean sortType = true;
    private static String sortedField = "position";
    private static final int itemsInPage = 3;
    private static final int filteringItemsInPage=100;

    private static VacancyDao vacancyDao;
    private static Connection connection;

    @PostConstruct
    public void initialize() {
        try {
            connection = ConnectorDB.getConnection();
            vacancyDao = new VacancyDao(connection);
        } catch (SQLException e) {
            logging.runMe(e);
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            logging.runMe(e);
        }
    }

    @GetMapping("/vacancies")
    public ResponseEntity getAllVacancies(HttpServletRequest request) {
        logging.runMe(request);
        List<Vacancy> allVacancies;
        try {
            allVacancies = vacancyDao.getSortedEntitiesPage(1, sortedField, true, itemsInPage);
            return ResponseEntity.ok(allVacancies);
        } catch (SQLException e) {
            return getResponseEntityOnServerError(e);
        }
    }

    @PostMapping("/vacancies")
    public ResponseEntity createVacancy(@RequestBody Vacancy vacancy, HttpServletRequest request) {
        logging.runMe(request);
        try {
            if (vacancyDao.createEntity(vacancy)) {
                return ResponseEntity.created(new URI("/vacancy/" + vacancy.getId()))
                        .body("Created");
            }
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Invalid Input");
        } catch (SQLException | URISyntaxException e) {
            return getResponseEntityOnServerError(e);
        }
    }

    @GetMapping("/vacancy/{id:\\d+}")
    public ResponseEntity getVacancy(@PathVariable long id, HttpServletRequest request) {
        logging.runMe(request);
        try {
            Vacancy vacancy = vacancyDao.getEntityById(id);
            if (vacancy == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(vacancy);
        } catch (SQLException e) {
            return getResponseEntityOnServerError(e);
        }
    }

    @PutMapping("/vacancy/{id:\\d+}")
    public ResponseEntity updateVacancy(@PathVariable long id, @RequestBody Vacancy vacancy,
                                        HttpServletRequest request) {
        logging.runMe(request);
        try {
            if (vacancyDao.updateEntity(vacancy)) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return getResponseEntityOnServerError(e);
        }
    }

    @DeleteMapping("/vacancy/{id:\\d+}")
    public ResponseEntity deleteVacancy(@PathVariable long id, HttpServletRequest request) {
        logging.runMe(request);
        try {
            if (vacancyDao.deleteEntity(new Vacancy(id))) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return getResponseEntityOnServerError(e);
        }
    }

    private ResponseEntity getResponseEntityOnServerError(Exception e) {
        logging.runMe(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
    }


    /*@RequestMapping(value = "/vacancies", method = RequestMethod.GET)
    public final ModelAndView getAllVacancies(HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView;
        try (Connection connection = ConnectorDB.getConnection()) {
            VacancyDao vacancyDao = new VacancyDao(connection);
            String sort = request.getParameter("sort");
            boolean sortType = true;
            if (sort != null) {
                sortType = !sort.equals("desc");
            }
            String sortedField = request.getParameter("field");
            if (sortedField == null) {
                sortedField = "position";
            }
            List<Vacancy> vacancies = vacancyDao.getSortedEntitiesPage
                    (1, sortedField, sortType, itemsInPage);
            VacancyState[] vacanciesStates = VacancyState.values();
            UserDao userDao = new UserDao(connection);
            List<User> allUsers = userDao.getSortedEntitiesPage(1, "surname", true, filteringItemsInPage);
            modelAndView = new ModelAndView("vacancies/vacancies");
            modelAndView.addObject("states", vacanciesStates);
            modelAndView.addObject("vacancies_list", vacancies);
            modelAndView.addObject("users_list", allUsers);
            modelAndView.addObject("page",1);
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/vacancies/page/{page:\\d+}", method = RequestMethod.GET)
    public final ModelAndView nextPage(@PathVariable int page, HttpServletRequest request) {
        ModelAndView modelAndView;
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            VacancyDao vacancyDao = new VacancyDao(connection);
            if (page == 0) {
                page = 1;
                modelAndView = new ModelAndView("redirect:/vacancies/page/" + page);
                return modelAndView;
            }
            List<Vacancy> vacancies = vacancyDao.getSortedEntitiesPage
                    (page, sortedField, sortType, itemsInPage);
            if (vacancies.isEmpty() && page != 1) {
                page--;
                modelAndView = new ModelAndView("redirect:/vacancies/page/" + page);
                return modelAndView;
            }
            UserDao userDao = new UserDao(connection);
            List<User> allUsers = userDao.getSortedEntitiesPage(1, "surname", true, filteringItemsInPage);

            VacancyState[] vacanciesStates = VacancyState.values();
            modelAndView = new ModelAndView("vacancies/vacancies");
            modelAndView.addObject("vacancies_list", vacancies);
            modelAndView.addObject("states", vacanciesStates);
            modelAndView.addObject("users_list", allUsers);
            modelAndView.addObject("page", page);
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/vacancies", method = RequestMethod.POST)
    public final ModelAndView addCandidate(HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView;
        try (Connection connection = ConnectorDB.getConnection()) {
            String position = request.getParameter("position");
            position = position == null ? "" : position.trim();
            if (position.equals("")) {
                throw new IllegalArgumentException("Field Position is empty");
            }
            float salaryInDollarsFrom;
            try {
                salaryInDollarsFrom= Float.parseFloat(request.getParameter("salary_in_dollars_from"));
            } catch (NumberFormatException | NullPointerException e) {
                salaryInDollarsFrom = 0;
            }
            float salaryInDollarsTo;
            try {
                salaryInDollarsTo= Float.parseFloat(request.getParameter("salary_in_dollars_to"));
            } catch (NumberFormatException | NullPointerException e) {
                salaryInDollarsTo = 0;
            }
            VacancyState vacancyState;
            try {
                vacancyState = VacancyState.valueOf(request.getParameter("state"));
            } catch (IllegalArgumentException | NullPointerException e) {
                throw new IllegalArgumentException("Field State is empty");
            }
            float experienceYearsRequire;
            try {
                experienceYearsRequire= Float.parseFloat(request.getParameter("experience_years_require"));
            } catch (NumberFormatException | NullPointerException e) {
                experienceYearsRequire = 0;
            }
            User developer;
            try {
                long idUser = Long.parseLong(request.getParameter("developer"));
                developer = new User(idUser);
            } catch (NumberFormatException | NullPointerException e) {
                throw new IllegalArgumentException("Field State is empty");
            }
            VacancyDao vacancyDao = new VacancyDao(connection);
            Vacancy vacancy = new Vacancy(position, salaryInDollarsFrom, salaryInDollarsTo,
                    vacancyState, experienceYearsRequire, developer);
            vacancyDao.createEntity(vacancy);
            modelAndView = new ModelAndView("redirect:" + "/vacancies/" + vacancy.getId());
        } catch (IllegalArgumentException e) {
            modelAndView = getAllVacancies(request);
            modelAndView.addObject("error", e.getMessage());
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/vacancies/{id:\\d+}/edit", method = RequestMethod.GET)
    public final ModelAndView editCandidate(@PathVariable long id, HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = getVacancy(id, request);
        try (Connection connection = ConnectorDB.getConnection()) {
            UserDao userDao = new UserDao(connection);
            List<User> allUsers = userDao.getSortedEntitiesPage(1, "surname", true, 100);
            VacancyState[] vacancyStates = VacancyState.values();
            modelAndView.addObject("states", vacancyStates);
            modelAndView.addObject("users", allUsers);
            modelAndView.setViewName("vacancies/vacancy_edit");
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView.setViewName("errors/500");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/vacancies/{id:\\d+}/edit", method = RequestMethod.POST)
    public final ModelAndView editCandidate(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) {
        logging.runMe(request);
        ModelAndView modelAndView;
        try (Connection connection = ConnectorDB.getConnection()) {
            String position = request.getParameter("position");
            position = position == null ? "" : position.trim();
            if (position.equals("")) {
                throw new IllegalArgumentException("Field Position is empty");
            }
            float salaryInDollarsFrom;
            try {
                salaryInDollarsFrom= Float.parseFloat(request.getParameter("salary_in_dollars_from"));
            } catch (NumberFormatException | NullPointerException e) {
                salaryInDollarsFrom = 0;
            }
            float salaryInDollarsTo;
            try {
                salaryInDollarsTo= Float.parseFloat(request.getParameter("salary_in_dollars_to"));
            } catch (NumberFormatException | NullPointerException e) {
                salaryInDollarsTo = 0;
            }
            VacancyState vacancyState;
            try {
                vacancyState = VacancyState.valueOf(request.getParameter("state"));
            } catch (IllegalArgumentException | NullPointerException e) {
                throw new IllegalArgumentException("Field State is empty");
            }
            float experienceYearsRequire;
            try {
                experienceYearsRequire= Float.parseFloat(request.getParameter("experience_years_require"));
            } catch (NumberFormatException | NullPointerException e) {
                experienceYearsRequire = 0;
            }

            User developer;
            try {
                long idUser = Long.parseLong(request.getParameter("developer"));
                developer = new User(idUser);
            } catch (NumberFormatException | NullPointerException e) {
                throw new IllegalArgumentException("Field State is empty");
            }
            VacancyDao vacancyDao = new VacancyDao(connection);
            Vacancy vacancy = new Vacancy(position, salaryInDollarsFrom, salaryInDollarsTo,
                    vacancyState, experienceYearsRequire, developer);
            vacancy.setId(id);
            vacancyDao.updateEntity(vacancy);
            modelAndView = new ModelAndView("redirect:" + "/vacancies/" + id);
        } catch (IllegalArgumentException e) {
            modelAndView = getVacancy(id, request);
            modelAndView.addObject("error", "Name must be filled");
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/vacancies/{id:\\d+}", method = RequestMethod.GET)
    public final ModelAndView getVacancy(@PathVariable long id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("vacancies/vacancy");
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            VacancyDao vacancyDao = new VacancyDao(connection);
            Vacancy vacancy = vacancyDao.getEntityById(id);
            UserDao userDao = new UserDao(connection);
            User user = userDao.getEntityById(vacancy.getDeveloper().getId());
            vacancy.setDeveloper(user);
            modelAndView.addObject("vacancy", vacancy);
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/vacancies/filtering", method = RequestMethod.POST)
    public final ModelAndView getFilteredEntities(HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = new ModelAndView("vacancies/vacancies");
        try (Connection connection = ConnectorDB.getConnection()) {
            VacancyDao vacancyDao = new VacancyDao(connection);
            String position = request.getParameter("position").trim();
            String salaryInDollarsFrom = request.getParameter("salary_in_dollars_from").trim();
            String salaryInDollarsTo = request.getParameter("salary_in_dollars_to").trim();
            String vacancyState = request.getParameter("state").trim();
            String experienceYearsRequire = request.getParameter("experience_years_require");
            String developerId = request.getParameter("developer");
            System.out.println(developerId);
            List<Vacancy> vacancies = vacancyDao.getFilteredEntitiesPage(position, salaryInDollarsFrom,
                    salaryInDollarsTo, vacancyState, experienceYearsRequire, developerId);


            VacancyState[] vacancyStates = VacancyState.values();
            UserDao userDao = new UserDao(connection);
            List<User> allUsers = userDao.getSortedEntitiesPage(1, "surname", true, 100);
            modelAndView.addObject("users_list", allUsers);
            modelAndView.addObject("states", vacancyStates);
            modelAndView.addObject("vacancies_list", vacancies);
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }*/
}
