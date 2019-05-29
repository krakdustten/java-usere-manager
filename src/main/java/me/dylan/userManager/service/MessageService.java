package me.dylan.userManager.service;

import me.dylan.userManager.db.dao.MessageDAO;
import me.dylan.userManager.db.dao.UserDAO;
import me.dylan.userManager.db.model.Message;
import me.dylan.userManager.db.model.User;
import me.dylan.userManager.modelsJSON.*;
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
@Path("/message")
public class MessageService {

    @Autowired
    private MessageDAO messageDAO;

    @Autowired
    private UserDAO userDAO;

    @POST
    @Path("new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newm(MessageNewJSON mn) {
        if(mn.getUsername() == null || mn.getCurrentID() == null || mn.getReceivername() == null || mn.getMessage() == null)
            return MapError.JSON_STRUCTURE_WRONG.getError();
        User user = userDAO.getCID(mn.getUsername(), mn.getCurrentID());
        if(user == null) return MapError.USER_NOT_FOUND.getError();
        User receiver = userDAO.get(mn.getReceivername());
        if(receiver == null) return MapError.USER_NOT_FOUND.getError();

        Message message = new Message();
        message.setSender(user);
        message.setReceiver(receiver);
        message.setMessage(mn.getMessage());
        message.setSendTime(new Date());
        message.setReceiverRead(false);
        messageDAO.insertNew(message);

        Map<String, String> map = new HashMap<>();
        map.put("done", "ok");
        return Response.status(Response.Status.OK).entity(map).build();
    }

    @POST
    @Path("remove")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(MessageRemoveJSON mr) {
        if(mr.getUsername() == null || mr.getCurrentID() == null || mr.getMessageID() < 0)
            return MapError.JSON_STRUCTURE_WRONG.getError();
        User user = userDAO.getCID(mr.getUsername(), mr.getCurrentID());
        if(user == null) return MapError.USER_NOT_FOUND.getError();
        Message message = messageDAO.get(mr.getMessageID(), user.getId());
        if(message == null) return MapError.MESSAGE_NOT_FOUND.getError();

        messageDAO.remove(message);
        Map<String, String> map = new HashMap<>();
        map.put("done", "ok");
        return Response.status(Response.Status.OK).entity(map).build();
    }

    @POST
    @Path("all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(MessageAllJSON ma) {
        if(ma.getUsername() == null || ma.getCurrentID() == null)
            return MapError.JSON_STRUCTURE_WRONG.getError();
        User user = userDAO.getCID(ma.getUsername(), ma.getCurrentID());
        if(user == null) return MapError.USER_NOT_FOUND.getError();

        List<Message> message = messageDAO.get(user.getId());
        List<MessageReturnJSON> mr = new ArrayList<>();
        for (Message m: message) {
            MessageReturnJSON mrj = new MessageReturnJSON();
            mrj.setId(m.getId());
            mrj.setSender(m.getSender().getName());
            mrj.setReceiver(m.getReceiver().getName());
            mrj.setMessage(m.getMessage());
            mrj.setSendTime(m.getSendTime());
            mrj.setReceiverRead(m.isReceiverRead());
            mr.add(mrj);
        }
        return Response.status(Response.Status.OK).entity(mr).build();
    }
}
