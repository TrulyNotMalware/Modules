package dev.notypie.lock.redis;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;
import redis.embedded.RedisServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@Slf4j
@Profile({"local","test"})
@Configuration
public class EmbeddedRedis {

    @Value("${core.embedded.redis.port:6379}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void init() throws IOException {
        if(!this.isRedisRunning()){
            this.redisServer = new RedisServer(this.redisPort);
            this.redisServer.start();
        }
        log.info("In-memory redis enabled at {}", this.redisPort);
    }

    @PreDestroy
    public void destroy(){
        this.redisServer.stop();
    }


    private boolean isRedisRunning() throws IOException {
        return isRunning(executeGrepProcessCommand(redisPort));
    }

    /**
     * Executes a shell command to grep for a specific port in the netstat output.
     *
     * @param redisPort the port to grep for
     * @return the Process object representing the executed shell command
     * @throws IOException if an I/O error occurs while executing the shell command
     */
    private Process executeGrepProcessCommand(int redisPort) throws IOException {
        String command = String.format("netstat -nat | grep LISTEN|grep %d", redisPort);
        String[] shell = {"/bin/sh", "-c", command};

        return Runtime.getRuntime().exec(shell);
    }

    /**
     * Checks if the specified process is running.
     *
     * @param process The process to check.
     * @return true if the process is running, false otherwise.
     * @throws RuntimeException If an exception occurs when reading the process's input stream.
     */
    private boolean isRunning(Process process) {
        String line;
        StringBuilder pidInfo = new StringBuilder();

        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            while ((line = input.readLine()) != null) {
                pidInfo.append(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return StringUtils.hasText(pidInfo.toString());
    }
}