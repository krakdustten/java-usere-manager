package me.dylan.userManager.service;

import me.dylan.userManager.db.dao.UserDAO;
import me.dylan.userManager.db.model.Message;
import me.dylan.userManager.db.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Component
@Path("/users")
public class UserService {

    private UserDAO userDAO = UserDAO.get();

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() {
        List<User> users = userDAO.getAll();
        return users;
    }

    @GET
    @Path("get/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String getUser(@PathParam("id") long userId) {
        User user = userDAO.get(userId);
        return user.toString();
    }

    @GET
    @Path("getuser/{name}")
    @Produces(MediaType.TEXT_HTML)
    public String getUser(@PathParam("name") String name) {
        User user = userDAO.get(name);
        return user.toString();
    }
}
