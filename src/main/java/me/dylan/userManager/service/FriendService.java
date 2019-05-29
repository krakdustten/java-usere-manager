package me.dylan.userManager.service;

import me.dylan.userManager.db.dao.FriendDAO;
import me.dylan.userManager.db.dao.UserDAO;
import me.dylan.userManager.db.model.User;
import me.dylan.userManager.db.model.UserFriend;
import me.dylan.userManager.modelsJSON.FriendAddJSON;
import me.dylan.userManager.modelsJSON.FriendAllJSON;
import me.dylan.userManager.modelsJSON.FriendReturnJSON;
import me.dylan.userManager.util.MapError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Component
@Path("/friend")
public class FriendService {

    @Autowired
    private FriendDAO friendDAO;

    @Autowired
    private UserDAO userDAO;

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(FriendAddJSON fa) {
        if(fa.getCurrentID() == null || fa.getUsername() == null || fa.getFriendsname() == null)
            return MapError.JSON_STRUCTURE_WRONG.getError();
        User user = userDAO.getCID(fa.getUsername(), fa.getCurrentID());
        if(user == null) return MapError.USER_NOT_FOUND.getError();
        User friend = userDAO.get(fa.getFriendsname());
        if(friend == null) return MapError.USER_NOT_FOUND.getError();
        UserFriend check = friendDAO.get(user.getId(), friend.getId());
        if(check != null) return MapError.USERS_ALREADY_FRIENDS.getError();

        UserFriend userFriend = new UserFriend();
        userFriend.setUser1(user);
        userFriend.setUser2(friend);
        userFriend.setConfirmed(false);
        userFriend.setStartTime(new Date());
        friendDAO.insertNew(userFriend);

        return Response.status(Response.Status.OK).entity(userFriend).build();
    }

    @POST
    @Path("remove")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(FriendAddJSON fa) {
        if(fa.getCurrentID() == null || fa.getUsername() == null || fa.getFriendsname() == null)
            return MapError.JSON_STRUCTURE_WRONG.getError();
        User user = userDAO.getCID(fa.getUsername(), fa.getCurrentID());
        if(user == null) return MapError.USER_NOT_FOUND.getError();
        User friend = userDAO.get(fa.getFriendsname());
        if(friend == null) return MapError.USER_NOT_FOUND.getError();
        UserFriend check = friendDAO.get(user.getId(), friend.getId());
        if(check == null) return MapError.USERS_NOT_FRIENDS.getError();

        friendDAO.remove(check);
        Map<String, String> map = new HashMap<>();
        map.put("done", "ok");
        return Response.status(Response.Status.OK).entity(map).build();
    }

    @POST
    @Path("all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(FriendAllJSON fa) {
        if(fa.getCurrentID() == null || fa.getUsername() == null)
            return MapError.JSON_STRUCTURE_WRONG.getError();
        User user = userDAO.getCID(fa.getUsername(), fa.getCurrentID());
        if(user == null) return MapError.USER_NOT_FOUND.getError();

        List<UserFriend> friends = friendDAO.get(user.getId());
        List<FriendReturnJSON> frRet = new ArrayList<>();
        for (UserFriend uf: friends) {
            FriendReturnJSON frj = new FriendReturnJSON();
            frj.setId(uf.getId());
            frj.setAccepted(uf.isConfirmed());
            frj.setStartTime(uf.getStartTime());
            if(uf.getUser1().getId() == user.getId()){
                frj.setAskedByUser(true);
                frj.setName(uf.getUser2().getName());
            }else{
                frj.setAskedByUser(false);
                frj.setName(uf.getUser1().getName());
            }
            frRet.add(frj);
        }
        return Response.status(Response.Status.OK).entity(frRet).build();
    }

    @POST
    @Path("accept")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response accept(FriendAddJSON fa) {
        if(fa.getCurrentID() == null || fa.getUsername() == null || fa.getFriendsname() == null)
            return MapError.JSON_STRUCTURE_WRONG.getError();
        User user = userDAO.getCID(fa.getUsername(), fa.getCurrentID());
        if(user == null) return MapError.USER_NOT_FOUND.getError();
        User ufriend = userDAO.get(fa.getFriendsname());
        if(ufriend == null) return MapError.USER_NOT_FOUND.getError();
        UserFriend friend = friendDAO.getOneWay(user.getId(), ufriend.getId());
        if(friend == null) return MapError.USERS_NOT_FRIENDS.getError();
        friend.setConfirmed(true);

        friendDAO.update(friend);
        Map<String, String> map = new HashMap<>();
        map.put("done", "ok");
        return Response.status(Response.Status.OK).entity(map).build();
    }
}
