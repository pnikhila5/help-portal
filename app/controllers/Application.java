package controllers;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import views.html.*;

import models.*;

/**
 * Manage a Provider portal. 
 */
public class Application extends Controller {
    
    /**
     * This result directly redirect to application home.
     */
    public static Result GO_HOME = redirect(
        routes.Application.list(0, "fullname", "asc", "")
    );
    
    /**
     * Handle default path requests, redirect to Providers list
     */
    public static Result index() {
        return GO_HOME;
    }

    /**
     * Display the paginated list of Providers.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filter Filter applied on computer names
     */
    public static Result list(int page, String sortBy, String order, String filter) {
        return ok(
            list.render(
                Provider.page(page, 10, sortBy, order, filter),
                sortBy, order, filter
            )
        );
    }
    
    /**
     * Display the 'edit form' of a existing Providers.
     * Control comes here when you try to submit feedback for a professor.
     *
     * @param id Id of the computer to edit
     */
    public static Result edit(Long id) {
        Form<Provider> computerForm = form(Provider.class).fill(
            Provider.find.byId(id)
        );
        return ok(
            editForm.render(id, computerForm)
        );
    }
    
    /**
     * Handle the 'edit form' submission . 
     *
     * Control comes here AFTER submitting feedback for a provider.
     * @param id Id of the computer to edit
     */
    public static Result update(Long id) {
        Form<Provider> computerForm = form(Provider.class).bindFromRequest();
        if(computerForm.hasErrors()) {
            return badRequest(editForm.render(id, computerForm));
        }
      
      Provider provider = Provider.getProviderById(id);
      if(provider.numReviews == 0){
        provider.numReviews++;
      }
      
      int oldRating = provider.rating;
      int  oldTotal = oldRating * provider.numReviews;
      provider.numReviews++;
      int newRating = (oldTotal + Integer.parseInt(computerForm.data().get("rating")))/provider.numReviews;
      provider.rating = newRating;
      String newFeedback ="";
      if(provider.feedback == null){
          newFeedback = computerForm.data().get("feedback");
      }
      else {
          newFeedback = provider.feedback +", "+ computerForm.data().get("feedback");
      }
      provider.feedback = newFeedback;
      Provider.update(provider);
      flash("success"," Feedback for Provider "+provider.fullname+ " has been successfully received after transaction");
        return GO_HOME;
    }
    
    /**
     * Control comes here when you want to register a new provider.
     */
    public static Result create() {
        Form<Provider> computerForm = form(Provider.class);
        return ok(
            createForm.render(computerForm)
        );
    }
    
    /**
     * Control Comes here when you create a new provider and hit submit.  
     */
    public static Result save() {
        Form<Provider> computerForm = form(Provider.class).bindFromRequest();
        if(computerForm.hasErrors()) {
            return badRequest(createForm.render(computerForm));
        }
        computerForm.get().save();
        flash("success", "Provider " + computerForm.get().fullname + " has been created");
        return GO_HOME;
    }
    
    /**
     * Handle Provider deletion
     * 
     * NOT used for current project. 
     */
    public static Result delete(Long id) {
        return GO_HOME;
    }
    

}
            
