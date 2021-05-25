package dev.buckybackend.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WelcomeControllerTest {

    @Test
    void welcomeTest() {
        String a = "welcome";
        assertThat(a).isEqualTo("welcome");
    }
}