package dev.notypie.requester;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Reference from <a href="https://techblog.woowahan.com/10795/">Woowahan Tech blog</a>
 */
@Slf4j
public class ConcurrentHttpRequester {

    private final ExecutorService executorService;
    private final CountDownLatch start;
    private final CountDownLatch end;
    private final AtomicInteger successCount;
    private final AtomicInteger failCount;

    public ConcurrentHttpRequester(final int poolSize){
        this.executorService = Executors.newFixedThreadPool(poolSize);
        this.start = new CountDownLatch(poolSize);
        this.end = new CountDownLatch(poolSize);
        this.successCount = new AtomicInteger(0);
        this.failCount = new AtomicInteger(0);
    }

    /**
     * Submits a request to the server using the given `requestBodySpec`.
     * asynchronously.
     *
     * This method will execute the request in a separate thread from the provided `executorService`,
     * allowing multiple requests to be sent concurrently.
     *
     * Upon receiving a response, the method checks the HTTP status code of the response.
     * If the code falls within the 2xx range (indicating a successful request), the `successCount` is incremented by one.
     * If the code falls within the 4xx range (indicating a client error), the `failCount` is incremented by one.
     *
     * @param requestBodySpec the request specification object used to build and send the request
     *
     * @throws InterruptedException if the current thread is interrupted while waiting for the request to be sent
     */
    public void submit(RestClient.RequestBodySpec requestBodySpec){
        this.executorService.submit(()->{
            try{
                start.countDown();
                start.await();
                ResponseEntity<Object> response = requestBodySpec.retrieve().toEntity(Object.class);
                if( response.getStatusCode().is2xxSuccessful() ) successCount.incrementAndGet();
                else if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) failCount.incrementAndGet();
            }
            catch (HttpClientErrorException e){
                if(e.getStatusCode().is4xxClientError() || e.getStatusCode().is5xxServerError())
                    failCount.incrementAndGet();
                else log.error(e.toString());
            } catch (Exception e){
                log.error(e.toString());
            } finally {
                end.countDown();
            }
        });
    }

    public void submit(RestClient.RequestHeadersSpec<?> requestHeadersSpec){
        this.executorService.submit(()->{
            try{
                start.countDown();
                start.await();
                ResponseEntity<Object> response = requestHeadersSpec.retrieve().toEntity(Object.class);
                log.info("response status :{}",response.getStatusCode());
                if( response.getStatusCode().is2xxSuccessful() ) successCount.incrementAndGet();
                else if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) failCount.incrementAndGet();
            } catch (HttpClientErrorException e){
                if(e.getStatusCode().is4xxClientError() || e.getStatusCode().is5xxServerError())
                    failCount.incrementAndGet();
                else log.error(e.toString());
            } catch (InterruptedException e) {
                log.error(e.toString());
            } finally {
                end.countDown();
            }
        });
    }

    public void await() {
        try {
            this.end.await();
        }catch (InterruptedException e){
            log.error(e.toString());
        }
    }

    public int getSuccessCount(){
        return this.successCount.get();
    }

    public int getFailCount(){
        return this.failCount.get();
    }
}