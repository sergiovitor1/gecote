package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.db.jpa.Transactional;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.compra.*;
import static play.data.Form.*;

import views.html.*;
import java.util.*;

import models.Compra;

/**
 * Created with IntelliJ IDEA.
 * User: Vitor
 * Date: 19/05/14
 * Time: 02:05
 * To change this template use File | Settings | File Templates.
 */
public class CompraCtl extends Controller {

    /**
     * Display the 'new Compra form'.
     */
    @Transactional(readOnly = true)
    public static Result create() {
        Form<Compra> compraForm = form(Compra.class);
        return ok(
                createFormCompra.render(compraForm)
        );
    }

}
