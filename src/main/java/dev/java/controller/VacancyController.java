package dev.java.controller;

import dev.java.Logging;
import dev.java.db.ConnectorDB;
import dev.java.db.daos.VacancyDao;
import dev.java.db.model.Vacancy;
import dev.java.db.model.VacancyState;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Controller
public class VacancyController {
    private Logging logging = new Logging();

    @RequestMapping(value = "/vacancies", method = RequestMethod.GET)
    public ModelAndView getAllCandidates(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("vacancies/vacancies");
        logging.runMe(request);
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
            List<Vacancy> candidates = vacancyDao.getSortedEntitiesPage(1, sortedField, sortType, 10);
            VacancyState[] vacancyStates = VacancyState.values();
            modelAndView.addObject("states", vacancyStates);
            modelAndView.addObject("vacancies_list", candidates);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelAndView;

    }

    @RequestMapping(value = "/vacancies", method = RequestMethod.POST)
    public ModelAndView addVacancy(HttpServletRequest request, HttpServletResponse response) {
        logging.runMe(request);
        ModelAndView modelAndView = new ModelAndView();
        try (Connection connection = ConnectorDB.getConnection()) {
            String position = request.getParameter("position").trim();
            if (position.equals("")) {
                throw new IllegalArgumentException("Field Name is empty");
            }
            float salaryInDollarsFrom = Float.parseFloat(request.getParameter("salary_in_dollars_from"));
            float salaryInDollarsTo = Float.parseFloat(request.getParameter("salary_in_dollars_to"));
            VacancyState vacancyState = VacancyState.valueOf(request.getParameter("state"));
            float experienceInYears = Float.parseFloat(request.getParameter("experience"));
            VacancyDao vacancyDao = new VacancyDao(connection);
            Vacancy vacancy;
            vacancy = new Vacancy(position, salaryInDollarsFrom, salaryInDollarsTo, vacancyState, experienceInYears);
            vacancyDao.createEntity(vacancy);
            modelAndView.setViewName("redirect:" + "/vacancies/" + vacancy.getId());
            return modelAndView;
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return modelAndView;
        }
        catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return modelAndView;
        }

    }

    @RequestMapping(value = "/vacancies/{id:\\d+}/edit", method = RequestMethod.GET)
    public ModelAndView editVacancy(@PathVariable long id, HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = getCandidate(id, request);
        try (Connection connection = ConnectorDB.getConnection()){
            VacancyState[] vacancyStates = VacancyState.values();
            modelAndView.addObject("states", vacancyStates);
        }
        catch (SQLException e) {

        }
        modelAndView.setViewName("vacancies/vacancy_edit");

        return modelAndView;
    }

    @RequestMapping(value = "/vacancies/{id:\\d+}/edit", method = RequestMethod.POST)
    public ModelAndView editVacancy(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) {
        logging.runMe(request);
        ModelAndView modelAndView = new ModelAndView("redirect:" + "/vacancies/" + id);
        try (Connection connection = ConnectorDB.getConnection()) {
            String position = request.getParameter("position").trim();
            if (position.equals("")) {
                throw new IllegalArgumentException("Field Name is empty");
            }
            float salaryInDollarsFrom = Float.parseFloat(request.getParameter("salary_in_dollars_from"));
            float salaryInDollarsTo = Float.parseFloat(request.getParameter("salary_in_dollars_to"));
            VacancyState vacancyState = VacancyState.valueOf(request.getParameter("state"));
            float experienceInYears = Float.parseFloat(request.getParameter("experience"));
            VacancyDao vacancyDao = new VacancyDao(connection);
            Vacancy vacancy;
            vacancy = new Vacancy(position, salaryInDollarsFrom, salaryInDollarsTo, vacancyState, experienceInYears);
            vacancyDao.updateEntity(vacancy);
            return modelAndView;
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return modelAndView;
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return modelAndView;
        }

    }

    @RequestMapping(value = "/vacancies/{id:\\d+}", method = RequestMethod.GET)
    public ModelAndView getCandidate(@PathVariable long id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("vacancies/vacancy");
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            VacancyDao vacancyDao = new VacancyDao(connection);
            Vacancy vacancy = vacancyDao.getEntityById(id);
            modelAndView.addObject("vacancy", vacancy);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value = "/vacancies/filtering", method = RequestMethod.POST)
    public ModelAndView getFilteredEntities(HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = new ModelAndView("vacancies/vacancies");
        try (Connection connection = ConnectorDB.getConnection()) {
            VacancyDao vacancyDao = new VacancyDao(connection);
            String position = request.getParameter("position").trim();
            float salaryInDollarsFrom = Float.parseFloat(request.getParameter("salary_in_dollars_from"));
            float salaryInDollarsTo = Float.parseFloat(request.getParameter("salary_in_dollars_to"));
            VacancyState vacancyState = VacancyState.valueOf(request.getParameter("state"));
            float experienceInYears = Float.parseFloat(request.getParameter("experience"));
            Vacancy vacancy;
            vacancy = new Vacancy(position, salaryInDollarsFrom, salaryInDollarsTo, vacancyState, experienceInYears);
            vacancyDao.createEntity(vacancy);
            modelAndView.setViewName("redirect:" + "/vacancies/" + vacancy.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }
}
