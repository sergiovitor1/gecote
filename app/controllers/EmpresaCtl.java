/**
 * @author Sérgio Vitor (sergiovitor1@gmail.com)
 * @since 15/05/2014
 *
 */
package controllers;

import models.Empresa;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.empresa.*;
import static play.data.Form.*;


/**
 * @author Sérgio Vitor (sergiovitor1@gmail.com)
 * @since 15/05/2014
 *
 */
@Security.Authenticated(Secured.class)
public class EmpresaCtl extends Controller {
    
    

    /**
     * Display the paginated list of Empresas.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filter Filter applied on Empresa names
     */
    @Transactional(readOnly=true)
    public static Result list(int page, String sortBy, String order, String filter) {
        return ok(
            listEmpresa.render(
                Empresa.page(page, 10, sortBy, order, filter),
                sortBy, order, filter
            )
        );
    }
    
    /**
     * Display the 'edit form' of a existing Empresa.
     *
     * @param id Id of the Empresa to edit
     */
    @Transactional(readOnly=true)
    public static Result edit(Long id) {
        Form<Empresa> empresaForm = form(Empresa.class).fill(
            Empresa.findById(id)
        );
        return ok(
            editFormEmpresa.render(id, empresaForm)
        );
    }
    
    /**
     * Handle the 'edit form' submission 
     *
     * @param id Id of the Empresa to edit
     */
    @Transactional
    public static Result update(Long id) {
        Form<Empresa> empresaForm = form(Empresa.class).bindFromRequest();
        if(empresaForm.hasErrors()) {
            return badRequest(editFormEmpresa.render(id, empresaForm));
        }
        empresaForm.get().update(id);
        flash("success", "Empresa " + empresaForm.get().nome + " has been updated");
        return Application.GO_HOME;
    }

    public EmpresaCtl() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * Display the 'new Empresa form'.
     */
    @Transactional(readOnly=true)
    public static Result create() {

        Form<Empresa> empresaForm = form(Empresa.class);

        return ok(
            createFormEmpresa.render(empresaForm)
        );
    }
    
    /**
     * Handle the 'new Empresa form' submission 
     */
    @Transactional
    public static Result save() {
        
        Form<Empresa> empresaForm = form(Empresa.class).bindFromRequest();

        if(empresaForm.hasErrors()) {
            return badRequest(createFormEmpresa.render(empresaForm));
        }
        
        empresaForm.get().save();
        flash("success", "Empresa criada com sucesso!");
        return Application.GO_HOME;
    }
    
    /**
     * Handle Empresa deletion
     */
    @Transactional
    public static Result delete(Long id) {
        Empresa.findById(id).delete();
        flash("success", "Empresa desativada com sucesso!");
        return Application.GO_HOME;
    }
    
   
}
            

