package com.p1.ek.model.objfiles;

public class Author {
    public Author(int authorId, String firstName, String lastName) {
        this.setAuthorId(authorId);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    public Author() {
        this(0, "", "");
    }

    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    private int authorId;
    private String firstName;
    private String lastName;
}
