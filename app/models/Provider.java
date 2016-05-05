package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

/**
 * Provider entity managed by Ebean.
 * This class is used to interact with the database and manages the model.
 * 
 * The model components are autowired using Ebeans.
 * This class then talks to the database. In our case an in memory db called H2 that comes with the play framework.
 */
@Entity 
public class Provider extends Model {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;
    
   public String fullname;
   public String tech;
   public String experience;
   public String email;
   public String feedback;
   
   public int rating;
   public int numReviews;
   
    /**
     * Generic query helper for entity Provider with id Long
     */
    public static Finder<Long,Provider> find = new Finder<Long,Provider>(Long.class, Provider.class); 
    /**
     * Returns the set of providers when you enter the application
     * 
     * */
    public static Map <String,String> options(){
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Provider c:Provider.find.orderBy("tech").findList()){
            options.put(c.id.toString(),c.fullname);
        }
        
        return options;
    }
    /**
     * Return a page of Provider
     *
     * @param page Page to display
     * @param pageSize Number of providers per page
     * @param sortBy Computer property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     */
    public static Page<Provider> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("tech", "%" + filter + "%")
                .findPagingList(pageSize)
                .setFetchAhead(false)
                .getPage(page);
    }
    
    /**Gets a single provider from the db based on id 
     * 
     * We use this method to calculateavg rating for a provider and reviews etc.
     * */
    public static Provider getProviderById(Long id){
        return Provider.find.byId(id);
    }
    
    /**Updates a single provider after feedback has been received.
     * */
    public static void update(Provider p){
        p.save();
    }
}

