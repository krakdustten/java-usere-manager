package me.dylan.userManager.service;

import me.dylan.userManager.db.dao.UserDAO;
import me.dylan.userManager.db.model.Message;
import me.dylan.userManager.db.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Component
@Path("/users")
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() {
        List<User> users = userDAO.getAll();
        return users;
    }
}
