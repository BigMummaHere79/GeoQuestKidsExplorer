package com.example.geoquestkidsexplorer.utils;

import com.example.geoquestkidsexplorer.controllers.LoginController;
import com.example.geoquestkidsexplorer.database.DatabaseServiceFactory;
import com.example.geoquestkidsexplorer.repositories.UserService;

/**
 * Factory for creating JavaFX controllers with their dependencies.
 * Centralizes dependency injection for controllers requiring services like UserService.
 */
public class ControllerFactory {
    private final UserService userService;

    /**
     * Constructor initializes the UserService dependency.
     */
    public ControllerFactory() {
        this.userService = DatabaseServiceFactory.createUserService();
    }

    /**
     * Creates a controller instance for the specified class, injecting dependencies as needed.
     * @param controllerClass The class of the controller to instantiate.
     * @return The instantiated controller.
     * @throws RuntimeException If instantiation fails.
     */
    public Object createController(Class<?> controllerClass) {
        if (controllerClass == LoginController.class) {
            return new LoginController(userService);
        }
        try {
            return controllerClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate controller: " + controllerClass.getName(), e);
        }
    }
}