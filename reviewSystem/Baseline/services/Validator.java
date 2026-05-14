package reviewSystem.Baseline.services;

/**
 * Class as shown in the diagram.
 * Tracable interactions:
 * SubmissionController -> Validator validateFormat(data)
 * Validator -> SubmissionController returns "valid" or "invalid"
 */
public class Validator {
    public Validator() {
    }
    // Diagram: SubmissionController -> Validator validateFormat(data)
    // Returns "valid" or "invalid" to SubmissionController, to control flow in [alt] block.
    // logic not specified, so returns valid by defaut.
    public String validateFormat(Object data) {
        if (data == null) {
            return "invalid";
        }
        // check format
        return "valid";
    }
}
