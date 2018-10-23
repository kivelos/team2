package dev.java.controller;

import dev.java.Logging;
import dev.java.db.ConnectorDB;
import dev.java.db.daos.UserDao;
import dev.java.db.model.User;
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
public class UserController {
    private Logging logging = new Logging();


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView getAllUsers(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("users/users");
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            UserDao userDao = new UserDao(connection);
            List<User> users = userDao.getSortedEntitiesPage(1,"email",true,100);
            modelAndView.addObject("users_list", users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }


    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response) {
        logging.runMe(request);
        ModelAndView modelAndView = new ModelAndView("users/users");
        try (Connection connection = ConnectorDB.getConnection()) {
            String surname = request.getParameter("surname").trim();
            String name = request.getParameter("name").trim();
            String password=request.getParameter("password").trim();
            String email=request.getParameter("email").trim();
            UserDao userDao = new UserDao(connection);
            List<User> users = userDao.getSortedEntitiesPage(1,"email",true,100);
            modelAndView.addObject("users_list", users);
            if(isCorrectInputDates(surname,password,email,modelAndView)){
                User user = new User(email, password, name, surname);
                userDao.createEntity(user);
                modelAndView.setViewName("redirect:" + "/users/" + user.getId());
            }
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

    private boolean isCorrectInputDates(String surname, String password, String email,ModelAndView modelAndView) {
        if (surname.equals("")||password.equals("")||email.equals("")) {
            modelAndView.addObject("mistake", "Not enough dates!");
            return false;
        }
        if(!User.isPasswordValid(password))
        {
            modelAndView.addObject("mistake", "Wrong password length!");
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/users/{id:\\d+}/edit", method = RequestMethod.GET)
    public ModelAndView editUser(@PathVariable long id, HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = getUser(id, request);
        modelAndView.addObject("Active", User.State.ACTIVE);
        modelAndView.setViewName("users/user_edit");
        return modelAndView;
    }

    @RequestMapping(value = "/users/{id:\\d+}/edit", method = RequestMethod.POST)
    public ModelAndView editUser(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) {
        logging.runMe(request);
        ModelAndView modelAndView = new ModelAndView("redirect:" + "/users/" + id);
        try (Connection connection = ConnectorDB.getConnection()) {
            String surname = request.getParameter("surname").trim();
            String name = request.getParameter("name").trim();
            String password=request.getParameter("password").trim();
            String email=request.getParameter("email").trim();
            User.State state;
            if(request.getParameter("state").equals("ACTIVE"))
                state= User.State.ACTIVE;
            else
                state= User.State.BLOCKED;
            if(isCorrectInputDates(surname,password,email,modelAndView)) {
                UserDao userDao = new UserDao(connection);
                User user = new User(email, password, name, surname);
                user.setId(id);
                user.setState(state);
                userDao.updateEntity(user);
            }
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

    @RequestMapping(value = "/users/{id:\\d+}", method = RequestMethod.GET)
    public ModelAndView getUser(@PathVariable long id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("users/user");
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            UserDao userDao = new UserDao(connection);
            User user = userDao.getEntityById(id);
            modelAndView.addObject("user", user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }
}