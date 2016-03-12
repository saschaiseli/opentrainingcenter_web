package ch.opentrainingcenter.util;

import java.io.PrintStream;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

/**
 * A component for creating personal greetings.
 */
@SessionScoped
public class Greeter implements Serializable {

    private static final long serialVersionUID = 1L;

    public void greet(final PrintStream to, final String name) {
        to.println(createGreeting(name));
    }

    public String createGreeting(final String name) {
        return "Hello, " + name + "!";
    }
}
