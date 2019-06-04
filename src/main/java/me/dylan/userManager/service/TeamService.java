package me.dylan.userManager.service;

import me.dylan.userManager.db.dao.TeamDAO;
import me.dylan.userManager.db.dao.UserDAO;
import me.dylan.userManager.db.model.Team;
import me.dylan.userManager.db.model.TeamUser;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Path("/team")
public class TeamService {
    @Autowired
    private TeamDAO teamDAO;
    @Autowired
    private UserDAO userDAO;

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(TeamCreateJSON tc) {
        if(tc.getUsername() == null || tc.getCurrentID() == null || tc.getTeamname() == null)
            return MapError.JSON_STRUCTURE_WRONG.getError();
        User user = userDAO.getCID(tc.getUsername(), tc.getCurrentID());
        if(user == null) return MapError.USER_NOT_FOUND.getError();
        if(user.getRights() < 250) return MapError.USER_RIGHTS_TO_LOW.getError();
        Team check = teamDAO.get(tc.getTeamname());
        if(check != null) return MapError.TEAM_ALREADY_EXISTS.getError();

        Team team = new Team();
        TeamUser teamUser = new TeamUser();
        team.setName(tc.getTeamname());
        team.addTeamUser(teamUser);
        teamUser.setUser(user);
        teamUser.setRights(2500);
        teamDAO.insertNew(team);

        Map<String, String> map = new HashMap<>();
        map.put("done", "ok");
        return Response.status(Response.Status.OK).entity(map).build();
    }

    @POST
    @Path("remove")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(TeamCreateJSON tc) {
        if(tc.getUsername() == null || tc.getCurrentID() == null || tc.getTeamname() == null)
            return MapError.JSON_STRUCTURE_WRONG.getError();
        User user = userDAO.getCID(tc.getUsername(), tc.getCurrentID());
        if(user == null) return MapError.USER_NOT_FOUND.getError();
        Team checkT = teamDAO.get(tc.getTeamname());
        if(checkT == null) return MapError.TEAM_NOT_FOUND.getError();
        TeamUser checkTU = teamDAO.get(user.getId(), checkT.getId());
        if(checkTU == null) return MapError.USER_NOT_FOUND_IN_TEAM.getError();
        if(checkTU.getRights() < 2500) return MapError.USER_TEAM_RIGHTS_TOO_LOW.getError();

        teamDAO.remove(checkT);

        Map<String, String> map = new HashMap<>();
        map.put("done", "ok");
        return Response.status(Response.Status.OK).entity(map).build();
    }

    @POST
    @Path("get")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(TeamCreateJSON tc) {
        if(tc.getUsername() == null || tc.getCurrentID() == null)
            return MapError.JSON_STRUCTURE_WRONG.getError();
        User user = userDAO.getCID(tc.getUsername(), tc.getCurrentID());
        if(user == null) return MapError.USER_NOT_FOUND.getError();
        if(tc.getTeamname() == null){
            List<TeamUser> tul = teamDAO.getAllU(user.getId());
            List<TeamReturnJSON> trl = new ArrayList<>();
            for(TeamUser tu : tul){
                TeamReturnJSON tr = new TeamReturnJSON();
                tr.setName(tu.getTeam().getName());
                tr.setRights(tu.getRights());
                trl.add(tr);
            }
            return Response.status(Response.Status.OK).entity(trl).build();
        }
        else{
            Team team = teamDAO.get(tc.getTeamname());
            if(team == null) return MapError.TEAM_NOT_FOUND.getError();
            TeamUser teamUser = teamDAO.get(user.getId(), team.getId());
            if(teamUser == null) return MapError.USER_NOT_FOUND_IN_TEAM.getError();
            List<TeamUser> tul = team.getTeamUsers();

            List<TeamUsersReturnJSON> turl = new ArrayList<>();
            for(TeamUser tu : tul){
                TeamUsersReturnJSON tur = new TeamUsersReturnJSON();
                tur.setName(tu.getUser().getName());
                tur.setRights(tu.getRights());
                turl.add(tur);
            }

            TeamUserListReturnJSON tulr = new TeamUserListReturnJSON();
            tulr.setId(team.getId());
            tulr.setUserlist(turl);

            return Response.status(Response.Status.OK).entity(tulr).build();
        }
    }

    @POST
    @Path("member/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response memberAdd(TeamMemberAddJSON tma) {
        if(tma.getUsername() == null || tma.getCurrentID() == null || tma.getTeamname() == null || tma.getMembername() == null || tma.getMemberrights() < 0)
            return MapError.JSON_STRUCTURE_WRONG.getError();
        User user = userDAO.getCID(tma.getUsername(), tma.getCurrentID());
        if(user == null) return MapError.USER_NOT_FOUND.getError();
        Team team = teamDAO.get(tma.getTeamname());
        if(team == null) return MapError.TEAM_NOT_FOUND.getError();
        TeamUser teamUser = teamDAO.get(user.getId(), team.getId());
        if(teamUser == null) return MapError.USER_NOT_FOUND_IN_TEAM.getError();
        if(teamUser.getRights() < 100) return MapError.USER_TEAM_RIGHTS_TOO_LOW.getError();
        User member = userDAO.get(tma.getMembername());
        if(member == null) return MapError.USER_NOT_FOUND.getError();
        TeamUser teamMember = teamDAO.get(member.getId(), team.getId());
        if(teamMember != null) return MapError.USER_ALREADT_IN_TEAM.getError();

        TeamUser newMember = new TeamUser();
        newMember.setUser(member);
        newMember.setTeam(team);
        newMember.setRights(
                tma.getMemberrights() > teamUser.getRights() ?
                        teamUser.getRights(): tma.getMemberrights());

        teamDAO.insertNew(newMember);

        Map<String, String> map = new HashMap<>();
        map.put("done", "ok");
        return Response.status(Response.Status.OK).entity(map).build();
    }

    @POST
    @Path("member/remove")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response memberRemove(TeamMemberRemoveJSON tma) {
        if(tma.getUsername() == null || tma.getCurrentID() == null || tma.getTeamname() == null || tma.getMembername() == null)
            return MapError.JSON_STRUCTURE_WRONG.getError();
        User user = userDAO.getCID(tma.getUsername(), tma.getCurrentID());
        if(user == null) return MapError.USER_NOT_FOUND.getError();
        Team team = teamDAO.get(tma.getTeamname());
        if(team == null) return MapError.TEAM_NOT_FOUND.getError();
        TeamUser teamUser = teamDAO.get(user.getId(), team.getId());
        if(teamUser == null) return MapError.USER_NOT_FOUND_IN_TEAM.getError();
        User member = userDAO.get(tma.getMembername());
        if(member == null) return MapError.USER_NOT_FOUND.getError();
        TeamUser teamMember = teamDAO.get(member.getId(), team.getId());
        if(teamMember == null) return MapError.USER_NOT_FOUND_IN_TEAM.getError();
        if(user.getId() != member.getId() && teamUser.getRights() < 100) return MapError.USER_TEAM_RIGHTS_TOO_LOW.getError();
        if(teamUser.getRights() < teamMember.getRights()) return  MapError.USER_TEAM_RIGHTS_TOO_LOW.getError();

        if(team.getTeamUsers().size() <= 1)
            teamDAO.remove(team);
        else{
            TeamUser maxRights;
            if(team.getTeamUsers().get(0).getId() != teamMember.getId())
                maxRights = team.getTeamUsers().get(0);
            else
                maxRights = team.getTeamUsers().get(1);
            for(int i = 0; i < team.getTeamUsers().size(); i++) {
                TeamUser check = team.getTeamUsers().get(i);
                if(check.getId() != teamMember.getId())
                    if (maxRights.getRights() < check.getRights())
                        maxRights = check;
            }
            if(maxRights.getRights() < 2500){
                maxRights.setRights(2500);
                teamDAO.update(maxRights);
            }
            teamDAO.remove(teamMember);
        }


        Map<String, String> map = new HashMap<>();
        map.put("done", "ok");
        return Response.status(Response.Status.OK).entity(map).build();
    }

    @POST
    @Path("member/role")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response memberRemove(TeamMemberAddJSON tma) {
        if(tma.getUsername() == null || tma.getCurrentID() == null || tma.getTeamname() == null || tma.getMembername() == null || tma.getMemberrights() < 0)
            return MapError.JSON_STRUCTURE_WRONG.getError();
        User user = userDAO.getCID(tma.getUsername(), tma.getCurrentID());
        if(user == null) return MapError.USER_NOT_FOUND.getError();
        Team team = teamDAO.get(tma.getTeamname());
        if(team == null) return MapError.TEAM_NOT_FOUND.getError();
        TeamUser teamUser = teamDAO.get(user.getId(), team.getId());
        if(teamUser == null) return MapError.USER_NOT_FOUND_IN_TEAM.getError();
        User member = userDAO.get(tma.getMembername());
        if(member == null) return MapError.USER_NOT_FOUND.getError();
        TeamUser teamMember = teamDAO.get(member.getId(), team.getId());
        if(teamMember == null) return MapError.USER_NOT_FOUND_IN_TEAM.getError();
        if(teamMember.getRights() > teamUser.getRights() || tma.getMemberrights() > teamUser.getRights())
            return MapError.USER_TEAM_RIGHTS_TOO_LOW.getError();

        if(teamMember.getRights() >= 2500){
            boolean ok = false;
            for(int i = 0; i < team.getTeamUsers().size(); i++){
                if(team.getTeamUsers().get(i).getRights() >= 2500){
                    ok = true;
                    break;
                }
            }
            if(!ok){
                Map<String, String> map = new HashMap<>();
                map.put("done", "nok");
                return Response.status(Response.Status.OK).entity(map).build();
            }
        }

        teamMember.setRights(tma.getMemberrights());
        teamDAO.update(teamMember);

        Map<String, String> map = new HashMap<>();
        map.put("done", "ok");
        return Response.status(Response.Status.OK).entity(map).build();
    }
}
