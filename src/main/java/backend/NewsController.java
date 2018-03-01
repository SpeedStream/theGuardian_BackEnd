package backend;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import backend.News;
import backend.NewsAdapter;
import backend.ApiMessage;
import backend.SpringMVCUtils;

import java.util.List;

@RestController
public class NewsController {

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/getArticles")
    @RequestMapping(value = "/getArticles/{office}/{startDate}/{endDate}/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody NewsAdapter news(@PathVariable(value = "office") String office,
            @PathVariable(value = "startDate") String startDate,
            @PathVariable(value = "endDate") String endDate){
        System.out.println("==== in news ====");
        String productionOffice = "";
        String jsonResponse = "";

        if (!office.equals("all")) { productionOffice = "production-office=" + office; }

        System.out.println("office: " + office + " | productionOffice: " + productionOffice + " | startDate: "+startDate+" | endDate: "+endDate);
        
        String url = "http://content.guardianapis.com/search?"
            + productionOffice
            + "&from-date=" + startDate  // Format -> YYYY-MM-DD
            + "&to-date=" + endDate     // Format -> YYYY-MM-DD
            + "&page-size=100&api-key=a8915dce-f35c-4cbf-8041-4de6f10ee8ca";

        System.out.println("\n\n" + url + "\n\n");
        JsonReader json = new JsonReader(url);

        try{
            jsonResponse = json.readJsonFromUrl();
        }catch(Exception ex){
            jsonResponse = "{\"exception\":\"" + ex + "\"}";
        }

        List<News> news = NewsObjectJson.extractFeatureFromJson(jsonResponse);

        return NewsAdapter.createNew(news);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleApiException(IllegalArgumentException e,
            HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return SpringMVCUtils.getOutputModel(new ApiMessage(e.getMessage()));
    }

    /**
     * Shows a more descriptive error message to the user when a submitted
     * object fails validation.
     * 
     * @param e
     *            the MethodArgumentNotValidException that we are handling.
     * @return A ModelAndView containing a single ApiMessage object. We also
     *         alter the response to have the status code 400.
     */
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ModelAndView handleBindingException(
            MethodArgumentNotValidException e, HttpServletResponse response) {

        // Build a list of all the validation errors to show to the user.
        // WARNING this may not be a good idea on a production website because
        // it may expose internal details such as the fact you are using Java,
        // JSR-303, etc. A generic BAD_REQUEST error would probably be better.
        String errors = buildErrorString(e.getBindingResult().getAllErrors());

        // set the response status code to indicate the request was bad.
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        return SpringMVCUtils.getOutputModel(new ApiMessage(errors));
    }

    /**
     * Concatenates the validation errors from the allErrors param into a single
     * string for display to a user.
     * 
     * @param allErrors
     * @return
     */
    private String buildErrorString(List<ObjectError> allErrors) {
        StringBuilder b = new StringBuilder();

        b.append(allErrors.get(0));

        // append any remaining errors
        for (int i = 1; i < allErrors.size(); i++) {
            b.append("\n");
            b.append(String.format("%s - %s", allErrors.get(i).getObjectName(),
                    allErrors.get(i).getDefaultMessage()));
        }

        return b.toString();
    }
}
