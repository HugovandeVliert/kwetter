package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.ModelValidationException;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.services.interfaces.IKweetService;
import nl.fontys.kwetter.services.interfaces.IUserService;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.List;

@Scope(value = "session")
@Component(value = "adminController")
@ELBeanName(value = "adminController")
public class AdminController {
    private final IUserService userService;
    private final IKweetService kweetService;

    private User editingUser = new User();

    @Autowired
    public AdminController(IUserService userService, IKweetService kweetService) {
        this.userService = userService;
        this.kweetService = kweetService;
    }

    public User getEditingUser() {
        return editingUser;
    }

    public List<User> getUsers() {
        return userService.findAll();
    }

    public Role[] getRoles() {
        return Role.values();
    }

    public void login() throws IOException {
        // Temporary login validation
        User foundUser;

        try {
            foundUser = userService.find(editingUser.getUsername());
        } catch (ModelNotFoundException e) {
            return;
        }

        if (!foundUser.getPassword().equals(editingUser.getPassword())) {
            return;
        }

        if (foundUser.getRole() != Role.ADMIN) {
            return;
        }

        FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/users.jsf");
    }

    public void editUser(int id) throws ModelNotFoundException, IOException {
        editingUser = userService.find(id);

        //TODO: fix lazy loading issue
        // workaround: setting kweets this way because of lazy loading
        editingUser.setKweets(kweetService.findByUser(editingUser));

        FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/editingUser.jsf");
    }

    public void saveUser() throws ModelValidationException {
        userService.save(editingUser);
    }

    public void removeKweet(int id) throws ModelNotFoundException, IOException {
        kweetService.deleteById(id);

        FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/editingUser.jsf");
    }
}
