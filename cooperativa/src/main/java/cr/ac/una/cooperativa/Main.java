
package cr.ac.una.cooperativa;

import cr.ac.una.cooperativa.util.AppContext;
import java.util.Arrays;

/**
 *
 * @author stwar
 */
public class Main {
    
    public static void main(String[] args){
      if(args.length > 0){
      AppContext.getInstance().set("Vista", args[0]);
      }
        App.main(args);
    }
}
