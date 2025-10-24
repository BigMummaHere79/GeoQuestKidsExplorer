package com.example.geoquestkidsexplorer.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserProfileBuilderTest {

    private void userBuild(){
        var newUser = new UserProfileBuilder()
                .withEmail("shani1@.com").withUsername("Tori")
                .withLevel(5).withLevelsCompleted(5).withAvatar("👧 Explorer Girl")
                .withRole("Student").withScore(5);
    }

    @Test
    void withUsername() {
        var builder = new UserProfileBuilder();
        var returns = builder.withUsername("Tori");
        assertSame(builder,returns);
    }


    @Test
    void withAvatar() {
        var builder = new UserProfileBuilder();
        var returns = builder
                .withUsername("Tori")
                .withAvatar("👧 Explorer Girl")
                .withEmail("shani1@.com");
        assertSame(builder,returns);
    }

    @Test
    void withLevel() {
    }

    @Test
    void withRole() {
    }

    @Test
    void withScore() {
    }

    @Test
    void withLevelsCompleted() {
    }

    @Test
    void build() {
    }
}