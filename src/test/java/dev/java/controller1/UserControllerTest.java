package dev.java.controller1;

import dev.java.db.daos1.AbstractDao;
import dev.java.db.model1.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController controller;

    @Before
    public void setUp() {
        this.controller = new UserController();
        this.controller.initialize();
    }

    @Test
    public void checkOkInGetAllEntities() throws Exception {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1);
        user1.setState(User.State.ACTIVE);
        user1.setPassword("sjkgksgs");
        user1.setName("Evgeniy");
        user1.setSurname("Zakrevskiy");
        user1.setEmail("zakrevskiy_evgeniy@mail.ru");

        User user2 = new User();
        user2.setId(2);
        user2.setEmail("pavel)g@mail.ru");
        user2.setSurname("Grudinskiy");
        user2.setName("Pavel");
        user2.setPassword("lkjsgkkhr");
        user2.setState(User.State.BLOCKED);

        users.add(user1);
        users.add(user2);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getSortedEntitiesPage(anyInt(), anyString(), anyBoolean(), anyInt())).thenReturn(users);
        controller.setAbstractDao(daoMock);

        ResponseEntity res = this.controller.getAllEntities(mock(HttpServletRequest.class));

        Assert.assertEquals(users, res.getBody());
    }

    @Test
    public void checkOkInCreateEntity() throws Exception {
        User user1 = new User();
        user1.setId(1);
        user1.setState(User.State.ACTIVE);
        user1.setPassword("sjkgksgs");
        user1.setName("Evgeniy");
        user1.setSurname("Zakrevskiy");
        user1.setEmail("zakrevskiy_evgeniy@mail.ru");
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.createEntity(user1)).thenReturn(true);
        controller.setAbstractDao(daoMock);
        controller.setUrl("/user/");
        ResponseEntity res = this.controller.createEntity(user1, mock(HttpServletRequest.class));
        Assert.assertEquals("/user/1", res.getHeaders().getLocation().toString());
    }

    @Test
    public void checkInvalidInputCreateEntity() throws Exception {
        User user1 = new User();
        user1.setId(1);
        user1.setState(User.State.ACTIVE);
        user1.setPassword("sjkgksgs");
        user1.setName("Evgeniy");
        user1.setSurname("Zakrevskiy");
        user1.setEmail("zakrevskiy_evgeniy@mail.ru");
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.createEntity(user1)).thenReturn(false);
        controller.setAbstractDao(daoMock);
        controller.setUrl("/user/");
        ResponseEntity res = this.controller.createEntity(user1, mock(HttpServletRequest.class));
        Assert.assertEquals("Invalid Input", res.getBody().toString());
    }

    @Test
    public void checkOkGetEntity() throws Exception {
        User user1 = new User();
        user1.setId(1);
        user1.setState(User.State.ACTIVE);
        user1.setPassword("sjkgksgs");
        user1.setName("Evgeniy");
        user1.setSurname("Zakrevskiy");
        user1.setEmail("zakrevskiy_evgeniy@mail.ru");

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(user1);
        controller.setAbstractDao(daoMock);

        ResponseEntity res = this.controller.getEntity(1, mock(HttpServletRequest.class));

        Assert.assertEquals(user1, res.getBody());
    }

    @Test
    public void checkNotFoundGetEntity() throws Exception {
        User user1 = new User();
        user1.setId(1);
        user1.setState(User.State.ACTIVE);
        user1.setPassword("sjkgksgs");
        user1.setName("Evgeniy");
        user1.setSurname("Zakrevskiy");
        user1.setEmail("zakrevskiy_evgeniy@mail.ru");
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(user1);
        controller.setAbstractDao(daoMock);

        ResponseEntity res = this.controller.getEntity(2, mock(HttpServletRequest.class));
        Assert.assertEquals(404, res.getStatusCodeValue());
    }

    @Test
    public void checkOkInUpdateEntity() throws Exception {
        User user1 = new User();
        user1.setId(1);
        user1.setState(User.State.ACTIVE);
        user1.setPassword("sjkgksgs");
        user1.setName("Evgeniy");
        user1.setSurname("Zakrevskiy");
        user1.setEmail("zakrevskiy_evgeniy@mail.ru");
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.updateEntity(user1)).thenReturn(true);

        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.updateEntity(1, user1, mock(HttpServletRequest.class));
        Assert.assertEquals(200, res.getStatusCodeValue());
    }

    @Test
    public void checkNotFoundInUpdateEntity() throws Exception {
        User user1 = new User();
        user1.setId(1);
        user1.setState(User.State.ACTIVE);
        user1.setPassword("sjkgksgs");
        user1.setName("Evgeniy");
        user1.setSurname("Zakrevskiy");
        user1.setEmail("zakrevskiy_evgeniy@mail.ru");
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.updateEntity(user1)).thenReturn(false);

        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.updateEntity(2, user1, mock(HttpServletRequest.class));
        Assert.assertEquals(404, res.getStatusCodeValue());
    }

    @Test
    public void checkOkInDeleteEntity() throws Exception {
        User user1 = new User();
        user1.setId(1);
        user1.setState(User.State.ACTIVE);
        user1.setPassword("sjkgksgs");
        user1.setName("Evgeniy");
        user1.setSurname("Zakrevskiy");
        user1.setEmail("zakrevskiy_evgeniy@mail.ru");

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.deleteEntityById(1)).thenReturn(true);

        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.deleteEntity(1, mock(HttpServletRequest.class));
        Assert.assertEquals(200, res.getStatusCodeValue());
    }

    @Test
    public void checkNotFoundInDeleteEntity() throws Exception {
        User user1 = new User();
        user1.setId(1);
        user1.setState(User.State.ACTIVE);
        user1.setPassword("sjkgksgs");
        user1.setName("Evgeniy");
        user1.setSurname("Zakrevskiy");
        user1.setEmail("zakrevskiy_evgeniy@mail.ru");

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.deleteEntityById(1)).thenReturn(false);

        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.deleteEntity(2, mock(HttpServletRequest.class));
        Assert.assertEquals(404, res.getStatusCodeValue());
    }
}