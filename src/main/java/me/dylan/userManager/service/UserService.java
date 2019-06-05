package me.dylan.userManager.service;

import me.dylan.userManager.modelsJSON.*;
import me.dylan.userManager.util.EmailHandler;
import me.dylan.userManager.util.MapError;
import me.dylan.userManager.util.Util;
import me.dylan.userManager.db.dao.UserDAO;
import me.dylan.userManager.db.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.SecureRandom;
import java.util.*;

@Component
@Path("/user")
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserLoginJSON ul) {
        if(ul.getPassword() == null){
            User user = userDAO.get(ul.getUsername());
            if(user == null) return MapError.USER_NOT_FOUND.getError();
            Map<String, String> map = new HashMap<>();
            map.put("salt", user.getSalt());
            return Response.status(Response.Status.OK).entity(map).build();
        }
        User user = userDAO.get(ul.getUsername(), ul.getPassword());
        if(user == null) return MapError.USER_NOT_FOUND.getError();

        SecureRandom random = new SecureRandom();
        byte cid[] = new byte[64];
        random.nextBytes(cid);

        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 7);

        user.setCurrentID(Util.bytesToHex(cid));
        user.setValidUntilID(c.getTime());
        userDAO.update(user);
        return Response.status(Response.Status.OK).entity(user).build();
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(UserRegisterJSON ur) {
        if(ur.getEmail() == null || ur.getPassword() == null || ur.getSalt() == null || ur.getUsername() == null){
            SecureRandom random = new SecureRandom();
            byte salt[] = new byte[64];
            random.nextBytes(salt);

            Map<String, String> map = new HashMap<>();
            map.put("salt", Util.bytesToHex(salt));
            return Response.status(Response.Status.OK).entity(map).build();
        }
        User check = userDAO.get(ur.getUsername());
        if(check != null) return MapError.USER_ALREADY_EXISTS.getError();

        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 7);

        SecureRandom random = new SecureRandom();
        byte cid[] = new byte[64];
        random.nextBytes(cid);

        User user = new User();
        user.setName(ur.getUsername());
        user.setPassword(ur.getPassword());
        user.setSalt(ur.getSalt());
        user.setEmail(ur.getEmail());
        user.setConfirmed(false);
        user.setRights(0);
        user.setValidUntilID(c.getTime());
        user.setCurrentID(Util.bytesToHex(cid));

        userDAO.insertNew(user);
        EmailHandler.sendConfirmation(user);
        user.setCurrentID("");

        return Response.status(Response.Status.OK).entity(user).build();
    }

    @GET
    @Path("register/confirm")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String confirm(@QueryParam("username") String username, @QueryParam("confirmID") String confirmID) {
        if(username == null || confirmID == null) return "Not all data given.";
        User user = userDAO.get(username);
        if(user == null) return MapError.USER_NOT_FOUND.getName();
        if(user.isConfirmed()) return "USer already confirmed.";
        if(user.getValidUntilID().before(new Date())){
            userDAO.remove(user);
            return "Expired.";
        }
        if(!user.getCurrentID().toLowerCase().startsWith(confirmID.toLowerCase()))
            return "ID given is wrong.";
        user.setConfirmed(true);
        user.setValidUntilID(new Date());
        userDAO.update(user);
        return "Successful.";
    }

    @POST
    @Path("remove")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(UserRemoveJSON uc) {
        if(uc.getCurrentID() == null || uc.getUsername() == null)
            return MapError.JSON_STRUCTURE_WRONG.getError();
        User user = userDAO.getCID(uc.getUsername(), uc.getCurrentID());
        if(user == null) return MapError.USER_NOT_FOUND.getError();
        if(uc.getUsertoremove() == null) {
            userDAO.remove(user);
            Map<String, String> map = new HashMap<>();
            map.put("done", "ok");
            return Response.status(Response.Status.OK).entity(map).build();
        }
        if(user.getRights() < 2500) return MapError.USER_RIGHTS_TO_LOW.getError();
        User userToRemove = userDAO.get(uc.getUsertoremove());
        if(userToRemove == null) return MapError.USER_NOT_FOUND.getError();

        userDAO.remove(userToRemove);
        Map<String, String> map = new HashMap<>();
        map.put("done", "ok");
        return Response.status(Response.Status.OK).entity(map).build();
    }

    @POST
    @Path("get")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(UserGetJSON ug) {
        if(ug.getUsername() == null)
            return MapError.JSON_STRUCTURE_WRONG.getError();
        if(ug.getCurrentID() == null){
            User user = userDAO.get(ug.getUsername());
            if(user == null) return MapError.USER_NOT_FOUND.getError();
            UserSmallReturnJSON usrj = new UserSmallReturnJSON();
            usrj.setId(user.getId());
            usrj.setName(user.getName());
            usrj.setEmail(user.getEmail());
            return Response.status(Response.Status.OK).entity(usrj).build();
        }
        User user = userDAO.getCID(ug.getUsername(), ug.getCurrentID());
        if(user == null) return MapError.USER_NOT_FOUND.getError();

        UserReturnJSON userR = new UserReturnJSON();
        userR.setId(user.getId());
        userR.setName(user.getName());
        userR.setEmail(user.getEmail());
        userR.setCurrentID(user.getCurrentID());
        userR.setValidUntilID(user.getValidUntilID());
        userR.setRights(user.getRights());
        return Response.status(Response.Status.OK).entity(userR).build();
    }

    @POST
    @Path("getall")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getall(UserGetJSON ug) {
        if(ug.getUsername() == null || ug.getCurrentID() == null)
            return MapError.JSON_STRUCTURE_WRONG.getError();
        User user = userDAO.getCID(ug.getUsername(), ug.getCurrentID());
        if(user == null) return MapError.USER_NOT_FOUND.getError();
        if(user.getRights() < 2500) return MapError.USER_RIGHTS_TO_LOW.getError();

        List<User> users = userDAO.getAll();

        return Response.status(Response.Status.OK).entity(users).build();
    }

    @POST
    @Path("role")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(UserRoleJSON ur) {
        if(ur.getUsername() == null || ur.getCurrentID() == null || ur.getSetname() == null || ur.getSetrights() < 0)
            return MapError.JSON_STRUCTURE_WRONG.getError();
        User user = userDAO.getCID(ur.getUsername(), ur.getCurrentID());
        if(user == null) return MapError.USER_NOT_FOUND.getError();
        if(user.getRights() < 2500) return MapError.USER_RIGHTS_TO_LOW.getError();
        User setu = userDAO.get(ur.getSetname());
        if(setu == null) return MapError.USER_NOT_FOUND.getError();

        setu.setRights(ur.getSetrights());
        userDAO.update(setu);

        Map<String, String> map = new HashMap<>();
        map.put("done", "ok");
        return Response.status(Response.Status.OK).entity(map).build();
    }
}
