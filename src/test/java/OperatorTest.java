import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class OperatorTest {

  @Test
  void map() {
    Flux.range(1, 10)
            .map(i -> i * 10)
            .subscribe(System.out::println);
  }

  @Test
  void flatMap() {
    /*flat map creates single */
    Flux.range(1, 10)
            .flatMap(i -> Flux.range(i * 10, 3))
            .subscribe(System.out::println);
  }

  @Test
  void flatMapMay() {
    Mono.just(3)
            .flatMapMany(i -> Flux.range(1, i))
            .subscribe(System.out::println);
  }

  @Test
  void concat() throws InterruptedException {
    Flux<Integer> oneToFive = Flux.range(1, 5)
            .delayElements(Duration.ofMillis(200));

    Flux<Integer> sixToTen = Flux.range(6, 5)
            .delayElements(Duration.ofMillis(400));

    Flux.concat(oneToFive, sixToTen)
            .subscribe(System.out::println);

    /*oneToFive.concatWith(sixToTen)
            .subscribe(System.out::println);*/

    Thread.sleep(4000);
  }

  @Test
  void merge() throws InterruptedException {
    Flux<Integer> oneToFive = Flux.range(1, 5)
            .delayElements(Duration.ofMillis(200));

    Flux<Integer> sixToTen = Flux.range(6, 5)
            .delayElements(Duration.ofMillis(400));

    Flux.merge(oneToFive, sixToTen)
            .subscribe(System.out::println);

    /*oneToFive.mergeWith(sixToTen)
            .subscribe(System.out::println);*/

    Thread.sleep(4000);
  }

  @Test
  void zip()  {
    Flux<Integer> oneToFive = Flux.range(1, 5);

    Flux<Integer> sixToTen = Flux.range(6, 5);

    /*Flux.zip(oneToFive, sixToTen,
            (item1, item2) -> item1 + ", " + item2)
            .subscribe(System.out::println);*/

    oneToFive.zipWith(sixToTen)
            .subscribe(System.out::println);


  }
}
