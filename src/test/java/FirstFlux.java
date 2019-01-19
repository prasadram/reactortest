import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.Random;

public class FirstFlux {

  @Test
  void firstFlux() {
    Flux.just("A", "B", "C")
            .log()
            .subscribe();
  }

  @Test
  void fluxFormIterable() {
    Flux.fromIterable(Arrays.asList("A", "B", "C"))
            .log()
            .subscribe();
  }

  @Test
  void fluxFormRange() {
    Flux.range(10, 5)
            .log()
            .subscribe();
  }

  @Test
  void fluxFormInterval() throws InterruptedException {
    // here onComplete will not be called because producer will
    // produce events on regular intervals
    // it stopped because thread got killed
    Flux.interval(Duration.ofSeconds(1))
            .log()
            .subscribe();
    Thread.sleep(5000);
  }

  @Test
  void fluxFormInterval1() throws InterruptedException {
    Flux.interval(Duration.ofSeconds(1))
            .log()
            .take(2) // it will take n elements from producer then cancels the subscription
            .subscribe();
    Thread.sleep(5000);
  }

  @Test
  void fluxRequest()  {
    Flux.range(1, 7)
            .log()
            .subscribe(null,
                    null,
                    null,
                    s -> s.request(3));
  }

  @Test
  void fluxCustomSubsciber() {
    Flux.range(1, 10)
            .log()
            .subscribe(new BaseSubscriber<Integer>() {
              int elementsToProcess = 4;
              int count = 0;

              @Override
              protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("subscribed!");
                request(elementsToProcess);
              }

              @Override
              protected void hookOnNext(Integer value) {
                count++;
                if (count == elementsToProcess) {
                  count = 0;
                  Random r = new Random();
                  elementsToProcess = r.ints(1, 5)
                          .findFirst().getAsInt();
                  request(elementsToProcess);
                }
              }
            });
  }

  @Test
  void fluxLimitRange() {
    Flux.range(1, 5)
            .log()
            .limitRate(3)
            .subscribe();
  }
}
