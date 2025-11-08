package sk.ukf.mvcdemo.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // ✅ 404 ERROR
    @ExceptionHandler(ObjectNotFoundException.class)
    public String handleObjectNotFound(ObjectNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error/not-found";
    }

    // ✅ 500 ERROR (všetko ostatné)
    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error/server-error";
    }
}
