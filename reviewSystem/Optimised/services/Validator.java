package reviewSystem.Optimised.services;
import reviewSystem.Evaluation.Metrics;
/**
 * Tracable interactions:
 * SubmissionController -> Validator validate(data)
 * Validator -> SubmissionController returns boolean: isValid (optimisation is the return of boolean instead of string)
 */
public class Validator {
    public Validator() {
    }
    // Diagram: SubmissionController -> Validator validate(data)
    // Returns: boolean isValid.
    public boolean validate(Object data) {
        Metrics mc = Metrics.getInstance();
        mc.record("Validator.validate");
        if (data == null) {
            return false;
        }
        // check format
        return true;
    }
}
