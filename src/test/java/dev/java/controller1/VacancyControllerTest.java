//package dev.java.controller1;
//
//import dev.java.db.daos1.AbstractDao;
//import dev.java.db.model1.User;
//import dev.java.db.model1.Vacancy;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.http.ResponseEntity;
//
//import javax.servlet.http.HttpServletRequest;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//
//public class VacancyControllerTest {
//
//    private VacancyController controller;
//
//    @Before
//    public void setUp() {
//        this.controller = new VacancyController();
//        this.controller.initialize();
//    }
//
//    @Test
//    public void checkOkInGetAllEntities() throws Exception {
//        List<Vacancy> vacancies = new ArrayList<>();
//        Vacancy vacancy1 = new Vacancy(1);
//        vacancy1.setPosition("HR");
//        vacancy1.setSalaryInDollarsTo(50);
//        vacancy1.setSalaryInDollarsFrom(100);
//        vacancy1.setVacancyState(Vacancy.VacancyState.OPEN);
//        vacancy1.setExperienceYearsRequire(5);
//        vacancy1.(new User(1));
//
//        Vacancy vacancy2 = new Vacancy(2);
//        vacancy1.setPosition("JavaDeveloper");
//        vacancy1.setSalaryInDollarsTo(250);
//        vacancy1.setSalaryInDollarsFrom(500);
//        vacancy1.setVacancyState(Vacancy.VacancyState.CLOSE);
//        vacancy1.setExperienceYearsRequire(2);
//        vacancy1.setUser(new User(2));
//
//        vacancies.add(vacancy1);
//        vacancies.add(vacancy2);
//
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.getSortedEntitiesPage(anyInt(), anyString(), anyBoolean(), anyInt())).thenReturn(vacancies);
//        controller.setAbstractDao(daoMock);
//
//        ResponseEntity res = this.controller.getAllEntities(mock(HttpServletRequest.class));
//        //System.out.println(res);
//
//        Assert.assertEquals(vacancies, res.getBody());
//    }
//
//    @Test
//    public void checkSQLExceptionInGetAllEntities() throws Exception {
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.getSortedEntitiesPage(anyInt(), anyString(), anyBoolean(), anyInt())).thenThrow(new SQLException());
//        controller.setAbstractDao(daoMock);
//
//        ResponseEntity res = this.controller.getAllEntities(mock(HttpServletRequest.class));
//        //System.out.println(res);
//
//        Assert.assertEquals(500, res.getStatusCodeValue());
//    }
//
//    @Test
//    public void checkOkInCreateEntity() throws Exception {
//        Vacancy vacancy = new Vacancy("HR", 50, 100 , VacancyState.OPEN, 5, new User(1));
//        vacancy.setId(1);
//
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.createEntity(vacancy)).thenReturn(true);
//
//        controller.setAbstractDao(daoMock);
//        controller.setUrl("/vacancy/");
//        ResponseEntity res = this.controller.createEntity(vacancy, mock(HttpServletRequest.class));
//        Assert.assertEquals("/vacancy/1", res.getHeaders().getLocation().toString());
//    }
//
//    @Test
//    public void checkInvalidInputCreateEntity() throws Exception {
//        Vacancy vacancy = new Vacancy("HR", 50, 100 , VacancyState.OPEN, 5, new User(1));
//        vacancy.setId(1);
//
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.createEntity(vacancy)).thenReturn(false);
//
//        controller.setAbstractDao(daoMock);
//        controller.setUrl("/vacancy/");
//        ResponseEntity res = this.controller.createEntity(vacancy, mock(HttpServletRequest.class));
//        Assert.assertEquals(405, res.getStatusCodeValue());
//    }
//
//    @Test
//    public void checkSQLExceptionInCreateEntity() throws Exception {
//        Vacancy vacancy = new Vacancy("HR", 50, 100 , VacancyState.OPEN, 5, new User(1));
//        vacancy.setId(1);
//
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.createEntity(vacancy)).thenThrow(new SQLException());
//
//        controller.setAbstractDao(daoMock);
//        controller.setUrl("/vacancy/");
//        ResponseEntity res = this.controller.createEntity(vacancy, mock(HttpServletRequest.class));
//        Assert.assertEquals(500, res.getStatusCodeValue());
//    }
//
//    @Test
//    public void checkOkInGetEntity() throws Exception {
//        Vacancy vacancy = new Vacancy("HR", 50, 100 , VacancyState.OPEN, 5, new User(1));
//        vacancy.setId(1);
//
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.getEntityById(1)).thenReturn(vacancy);
//        controller.setAbstractDao(daoMock);
//
//        ResponseEntity res = this.controller.getEntity(1, mock(HttpServletRequest.class));
//
//        Assert.assertEquals(vacancy, res.getBody());
//    }
//
//    @Test
//    public void checkNotFoundInGetEntity() throws Exception {
//        Vacancy vacancy = new Vacancy("HR", 50, 100 , VacancyState.OPEN, 5, new User(1));
//        vacancy.setId(1);
//
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.getEntityById(1)).thenReturn(vacancy);
//        controller.setAbstractDao(daoMock);
//
//        ResponseEntity res = this.controller.getEntity(2, mock(HttpServletRequest.class));
//
//        Assert.assertEquals(404, res.getStatusCodeValue());
//    }
//
//    @Test
//    public void checkSQLExceptionInGetEntity() throws Exception {
//        Vacancy vacancy = new Vacancy("HR", 50, 100 , VacancyState.OPEN, 5, new User(1));
//        vacancy.setId(1);
//
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.getEntityById(1)).thenThrow(new SQLException());
//        controller.setAbstractDao(daoMock);
//
//        ResponseEntity res = this.controller.getEntity(1, mock(HttpServletRequest.class));
//
//        Assert.assertEquals(500, res.getStatusCodeValue());
//    }
//
//    @Test
//    public void checkOkInUpdateEntity() throws Exception {
//        Vacancy vacancy = new Vacancy("HR", 50, 100 , VacancyState.OPEN, 5, new User(1));
//        vacancy.setId(1);
//
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.updateEntity(vacancy)).thenReturn(true);
//
//        controller.setAbstractDao(daoMock);
//        ResponseEntity res = this.controller.updateEntity(1, vacancy, mock(HttpServletRequest.class));
//        Assert.assertEquals(200, res.getStatusCodeValue());
//    }
//
//    @Test
//    public void checkNotFoundInUpdateEntity() throws Exception {
//        Vacancy vacancy = new Vacancy("HR", 50, 100 , VacancyState.OPEN, 5, new User(1));
//        vacancy.setId(1);
//
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.updateEntity(vacancy)).thenReturn(false);
//
//        controller.setAbstractDao(daoMock);
//        ResponseEntity res = this.controller.updateEntity(2, vacancy, mock(HttpServletRequest.class));
//        Assert.assertEquals(404, res.getStatusCodeValue());
//    }
//
//    @Test
//    public void checkSQLExceptionInUpdateEntity() throws Exception {
//        Vacancy vacancy = new Vacancy("HR", 50, 100 , VacancyState.OPEN, 5, new User(1));
//        vacancy.setId(1);
//
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.updateEntity(vacancy)).thenThrow(new SQLException());
//
//        controller.setAbstractDao(daoMock);
//        ResponseEntity res = this.controller.updateEntity(1, vacancy, mock(HttpServletRequest.class));
//        Assert.assertEquals(500, res.getStatusCodeValue());
//    }
//
//    @Test
//    public void checkOkInDeleteEntity() throws Exception {
//        Vacancy vacancy = new Vacancy("HR", 50, 100 , VacancyState.OPEN, 5, new User(1));
//        vacancy.setId(1);
//
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.deleteEntity(vacancy)).thenReturn(true);
//        when(daoMock.getEntityById(1)).thenReturn(vacancy);
//
//        controller.setAbstractDao(daoMock);
//        ResponseEntity res = this.controller.deleteEntity(1, mock(HttpServletRequest.class));
//        Assert.assertEquals(200, res.getStatusCodeValue());
//    }
//
//    @Test
//    public void checkNotFoundInDeleteEntity() throws Exception {
//        Vacancy vacancy = new Vacancy("HR", 50, 100 , VacancyState.OPEN, 5, new User(1));
//        vacancy.setId(1);
//
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.deleteEntity(vacancy)).thenReturn(false);
//
//        controller.setAbstractDao(daoMock);
//        ResponseEntity res = this.controller.deleteEntity(2, mock(HttpServletRequest.class));
//        Assert.assertEquals(404, res.getStatusCodeValue());
//    }
//
//    @Test
//    public void checkSQLExceptionInDeleteEntity() throws Exception {
//        Vacancy vacancy = new Vacancy("HR", 50, 100 , VacancyState.OPEN, 5, new User(1));
//        vacancy.setId(1);
//
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.deleteEntity(vacancy)).thenThrow(new SQLException());
//        when(daoMock.getEntityById(1)).thenReturn(vacancy);
//
//        controller.setAbstractDao(daoMock);
//        ResponseEntity res = this.controller.deleteEntity(1, mock(HttpServletRequest.class));
//        Assert.assertEquals(500, res.getStatusCodeValue());
//    }
//
//
//}