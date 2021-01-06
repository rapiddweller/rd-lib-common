package com.rapiddweller.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class DeploymentErrorTest {
    @Test
    public void testConstructor() {
        DeploymentError actualDeploymentError = new DeploymentError();
        assertEquals("com.rapiddweller.common.DeploymentError", actualDeploymentError.toString());
        assertNull(actualDeploymentError.getLocalizedMessage());
        assertNull(actualDeploymentError.getCause());
        assertNull(actualDeploymentError.getMessage());
        assertEquals(0, actualDeploymentError.getSuppressed().length);
    }

    @Test
    public void testConstructor2() {
        DeploymentError actualDeploymentError = new DeploymentError("Not all who wander are lost");
        assertEquals("com.rapiddweller.common.DeploymentError: Not all who wander are lost",
                actualDeploymentError.toString());
        assertEquals("Not all who wander are lost", actualDeploymentError.getLocalizedMessage());
        assertNull(actualDeploymentError.getCause());
        assertEquals("Not all who wander are lost", actualDeploymentError.getMessage());
        assertEquals(0, actualDeploymentError.getSuppressed().length);
    }

    @Test
    public void testConstructor3() {
        Throwable throwable = new Throwable();
        assertSame((new DeploymentError("Not all who wander are lost", throwable)).getCause(), throwable);
    }

    @Test
    public void testConstructor4() {
        Throwable throwable = new Throwable();
        assertSame((new DeploymentError(throwable)).getCause(), throwable);
    }
}

