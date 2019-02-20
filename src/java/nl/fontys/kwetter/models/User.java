package nl.fontys.kwetter.models;

import java.util.Set;

public class User {
    private Integer id;
    private String username;
    private String name;
    private String email;
    private Role role;
    private byte[] picture;
    private String bio;
    private String location;
    private String website;

    private Set<User> following;
    private Set<User> followers;
    private Set<Kweet> kweets;
    private Set<Kweet> likedKweets;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public Set<Kweet> getKweets() {
        return kweets;
    }

    public void setKweets(Set<Kweet> kweets) {
        this.kweets = kweets;
    }

    public Set<Kweet> getLikedKweets() {
        return likedKweets;
    }

    public void setLikedKweets(Set<Kweet> likedKweets) {
        this.likedKweets = likedKweets;
    }
}
