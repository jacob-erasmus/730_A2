package reviewSystem.services;

public class Validator {
    // SubmissionController calls Validator validateFormat(data)
    // returns "valid" or "invalid"
    // If invalid will return error.
    // If valid will continue.

    public String validateFormat(Object data) {
        if (data == null) {
            return "invalid";
        }
        // check format
        return "valid";
    }
}
