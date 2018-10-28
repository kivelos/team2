package dev.java.controller;

import dev.java.db.ConnectorDB;
import dev.java.db.daos.SkillDao;
import dev.java.db.model.Skill;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import dev.java.Logging;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Controller
public class SkillController {

    private Logging logging = new Logging();
    static boolean sortType = true;

    @RequestMapping(value = "/skills", method = RequestMethod.GET)
    public ModelAndView getAllSkills(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("skills/skills");
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            SkillDao skillDao = new SkillDao(connection);
            String sort = request.getParameter("sort");
            if (sort != null) {
                sortType = !sort.equals("desc");
            }
            List<Skill> skills = skillDao.getSortedEntitiesPage(1,"name",sortType,3);
            modelAndView.addObject("skills_list", skills);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value = "/skills/page/{page:\\d+}", method = RequestMethod.POST)
    public ModelAndView nextPage(@PathVariable int page, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("skills/skills");
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            SkillDao skillDao = new SkillDao(connection);
            if(page==0)
                page=1;
            List<Skill> skills = skillDao.getSortedEntitiesPage(page,"name",sortType,3);
            if(skills.isEmpty()&&page!=1) {
                page--;
                skills=skillDao.getSortedEntitiesPage(page,"name",sortType,3);
            }
            modelAndView.addObject("skills_list", skills);
            modelAndView.addObject("page",page);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }
        @RequestMapping(value = "/skills", method = RequestMethod.POST)
        public ModelAndView addSkill(HttpServletRequest request, HttpServletResponse response) {
            logging.runMe(request);
            ModelAndView modelAndView = new ModelAndView("skills/skills");
            try (Connection connection = ConnectorDB.getConnection()) {
                String name = request.getParameter("name").trim();
                SkillDao skillDao = new SkillDao(connection);
                List<Skill> skills = skillDao.getSortedEntitiesPage(1, "name", sortType, 3);
                modelAndView.addObject("skills_list", skills);
                if (isCorrectInputDates(name, modelAndView)) {
                    Skill skill = new Skill(name);
                    skillDao.createEntity(skill);
                    modelAndView.setViewName("redirect:" + "/skills/");
                }
                return modelAndView;
            } catch (SQLException e) {
                //response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

               modelAndView.addObject("mistake", "Skill aready create!");
               //modelAndView.setViewName("redirect:" + "/skills/");

                return modelAndView;

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return modelAndView;
            }
        }

        private boolean isCorrectInputDates (String name, ModelAndView modelAndView){
            if(!Skill.isNameSkillValid(name)) {
                modelAndView.addObject("mistake", "Wrong name of skill!");
                return false;
            }
            return true;
        }


    @RequestMapping(value = "/skills/{name}", method = RequestMethod.GET)
    public ModelAndView getSkill(@PathVariable String name, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("skills/skill");
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            SkillDao skillDao = new SkillDao(connection);
            Skill skill = skillDao.getEntityByName(name);
            modelAndView.addObject("skill", skill);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value = "/skills/{name}/edit", method = RequestMethod.GET)
    public ModelAndView editSkill(@PathVariable String name, HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = getSkill(name, request);
        modelAndView.setViewName("skills/skill_edit");
        return modelAndView;
    }
    @RequestMapping(value = "/skills/{name}/edit", method = RequestMethod.POST)
    public ModelAndView editSkill(@PathVariable String name, HttpServletRequest request, HttpServletResponse response) {
        logging.runMe(request);
        ModelAndView modelAndView = new ModelAndView("redirect:" + "/skills/" + name);
        try (Connection connection = ConnectorDB.getConnection()) {
            String nameReal = request.getParameter("name").trim();
            if(isCorrectInputDates(nameReal,modelAndView)) {
                SkillDao skillDao = new SkillDao(connection);
                Skill skill = new Skill(nameReal);
                skillDao.updateEntity(skill);
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
    @RequestMapping(value = "/skills/filtering", method = RequestMethod.POST)
    public ModelAndView getFilteredEntities(HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = new ModelAndView("skills/skills");
        try (Connection connection = ConnectorDB.getConnection()) {
            SkillDao skillDao = new SkillDao(connection);
            String name = request.getParameter("name").trim();
            List<Skill> skills = skillDao.getSortedFilteredEntitiesPage(name);
            modelAndView.addObject("skills_list", skills);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }
}
