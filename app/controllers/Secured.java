package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;


/**
 * Created with IntelliJ IDEA.
 * User: Vitor
 * Date: 18/05/14
 * Time: 04:13
 * To change this template use File | Settings | File Templates.
 */


public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("email");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.Application.login());
    }


}