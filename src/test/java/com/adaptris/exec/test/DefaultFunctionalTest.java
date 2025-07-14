package com.adaptris.exec.test;

import com.adaptris.testing.SingleAdapterFunctionalTest;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultFunctionalTest extends SingleAdapterFunctionalTest {
    Path failedMessagesPath = Path.of("failed-messages");
    private static final int processMonitorMs = 8000;

    @Override
    @BeforeAll
    public void setup() throws Exception {
        if (Files.exists(failedMessagesPath)) {
            Files.delete(failedMessagesPath);
        }
        super.setup();
    }

    @Override
    protected void customiseBootstrapProperties(Properties props) {
        if (!SystemUtils.IS_OS_WINDOWS) props.put("exec.tomcat.start.command", "./config/remove-files.sh");
        props.put("exec.tomcat.process.monitor.ms", String.valueOf(processMonitorMs));
    }

    @Test
    public void test() throws Exception {
        assertTrue(Files.exists(failedMessagesPath));

        try (Stream<Path> s = Files.list(failedMessagesPath)) {
            assertTrue(s.findAny().isPresent());
        }

        // 1 second after the process monitor kicks off, check if the directory is removed
        Thread.sleep(processMonitorMs + 1000);
        assertFalse(Files.exists(failedMessagesPath));
    }
}
