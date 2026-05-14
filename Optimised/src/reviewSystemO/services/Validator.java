package reviewSystemO.services;

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
        if (data == null) {
            return false;
        }
        // check format
        return true;
    }
}
