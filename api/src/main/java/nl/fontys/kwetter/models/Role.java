package nl.fontys.kwetter.models;

public enum Role {
    USER("User"),
    MODERATOR("Moderator"),
    ADMIN("Administrator");

    private String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
