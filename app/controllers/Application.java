package controllers;

import models.Login;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;
import static play.data.Form.*;

/**
 * import models.Empresa;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.empresa.*;
import static play.data.Form.*;
 */


/**
 * Manage a database of Items
 */
public class Application extends Controller {
	
	
	/**
     * This result directly redirect to application home.
     */
    public static Result GO_HOME = redirect(
    		routes.EmpresaCtl.list(0, "nome", "asc", "")
    );
    
    /**
     * Handle default path requests, redirect to Items list
     */
    public static Result index() {
        return GO_HOME;
    }
    
    /**
     * Login page.
     */
    public static Result login() {
        return ok(
                login.render(form(Login.class))
        );
    }
    
    


    /**
     * Handle login form submission.
     */
    @Transactional(readOnly=true)
    public static Result authenticate() {

    	Form<Login> loginForm = form(Login.class).bindFromRequest();

        if(loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session("email", loginForm.get().email);
            return redirect(
                    routes.ItemCtl.create()
            );
        }
    }

    /**
     * Logout and clean the session.
     */
    @Transactional
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.Application.login()
        );
    }

}
            
