package controllers;

import models.Item;
import play.data.Form;
import play.mvc.*;
import play.db.jpa.Transactional;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.item.*;
import static play.data.Form.*;


/**
 * Manage a database of Items
 */
@Security.Authenticated(Secured.class)
public class ItemCtl extends Controller {


    /**
     * This result directly redirect to application home.
     */
    public static Result GO_HOME = redirect(
            routes.ItemCtl.list(0, "nome", "asc", "")
    );

    /**
     * Display the paginated list of Items.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filter Filter applied on Item names
     */
    @Transactional(readOnly=true)
    public static Result list(int page, String sortBy, String order, String filter) {
        return ok(
            listItem.render(
                Item.page(page, 10, sortBy, order, filter),
                sortBy, order, filter
            )
        );
    }
    
    /**
     * Display the 'edit form' of a existing Item.
     *
     * @param id Id of the Item to edit
     */
    @Transactional(readOnly=true)
    public static Result edit(Long id) {
        Form<Item> itemForm = form(Item.class).fill(
            Item.findById(id)
        );
        return ok(
            editFormItem.render(id, itemForm)
        );
    }
    
    /**
     * Handle the 'edit form' submission 
     *
     * @param id Id of the Item to edit
     */
    @Transactional
    public static Result update(Long id) {
        Form<Item> itemForm = form(Item.class).bindFromRequest();
        if(itemForm.hasErrors()) {
            return badRequest(editFormItem.render(id, itemForm));
        }
        itemForm.get().update(id);
        flash("success", "Item " + itemForm.get().nome + " has been updated");
        return GO_HOME;
    }
    
    /**
     * Display the 'new Item form'.
     */
    @Transactional(readOnly=true)
    public static Result create() {
        Form<Item> itemForm = form(Item.class);
        return ok(
            createFormItem.render(itemForm)
        );
    }
    
    /**
     * Handle the 'new Item form' submission 
     */
    @Transactional
    public static Result save() {
    	
        Form<Item> itemForm = form(Item.class).bindFromRequest();
        if(itemForm.hasErrors()) {
            return badRequest(createFormItem.render(itemForm));
        }
        
        itemForm.get().save();
        flash("success", "Item " + itemForm.get().nome + " has been created");
        return GO_HOME;
    }
    
    /**
     * Handle Item deletion
     */
    @Transactional
    public static Result delete(Long id) {
        Item.findById(id).delete();
        flash("success", "Item apagado com sucesso");
        return GO_HOME;
    }
    
   
}
            
