package ru.shipcollision.api.mechanics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.time.Clock;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class MechanicsExecutor implements Runnable {
    @NotNull
    private static final Logger LOGGER = LoggerFactory.getLogger(MechanicsExecutor.class);
    private static final long STEP_TIME = 50;

    @NotNull
    private final GameMechanics gameMechanics;

    @NotNull
    private final Clock clock = Clock.systemDefaultZone();

    private final Executor tickExecutor = Executors.newSingleThreadExecutor();

    public MechanicsExecutor(@NotNull GameMechanics gameMechanics) {
        this.gameMechanics = gameMechanics;
    }

    @PostConstruct
    public void initAfterStartup() {
        tickExecutor.execute(this);
    }

    @Override
    public void run() {
        try {
            mainCycle();
        } finally {
            LOGGER.warn("Mechanic executor terminated");
        }
    }

    private void mainCycle() {
        while (true) {
            try {
                gameMechanics.gmStep();

                try {
                    Thread.sleep(STEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (Thread.currentThread().isInterrupted()) {
                    // TODO: gameMechanics.reset().
                    return;
                }
            } catch (RuntimeException e) {
                LOGGER.error("Mechanics executor was reseted due to exception", e);
                // TODO: gameMechanics.reset().
            }
        }
    }
}
