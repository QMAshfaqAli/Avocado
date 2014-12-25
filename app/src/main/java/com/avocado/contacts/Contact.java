package com.avocado.contacts;

public class Contact {
    private String thumb;
    private String name;
    private String contact;
    private String email;

    public Contact(String name, String contact, String email, String thumb) {

        this.name = name;
        this.contact = contact;
        this.email = email;
        this.thumb = thumb;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
