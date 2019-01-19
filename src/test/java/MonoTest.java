import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class MonoTest {

  @Test
  public void firstTest() {
    Mono.just("Z")
    .log()
    .subscribe();
  }

  @Test
  public void monoWithConsumer() {
    Mono.just("z")
            .log()
            .subscribe(s -> System.out.println(s));
  }

  @Test
  public void monoWithDoOn() {
    Mono.just("z")
            .log()
            .doOnSubscribe(sub -> System.out.println("Subscribed: " + sub))
            .doOnRequest(requet -> System.out.println("Request : " + requet))
            .doOnSuccess(complete -> System.out.println("Complete " + complete))
            .subscribe(System.out::println);

  }

  @Test
  public void emptyMono() {
    Mono.empty()
            .log()
            .subscribe(System.out::println);
    //onNext will not be called
    //onComplete will be caled
  }

  @Test
  public void emptyCompleteConsumerMono() {
    Mono.empty()
            .log()
            .subscribe(System.out::println, null, () -> System.out.println("Done"));
    // empty mono will emits a complete signal
    // it is useful to emulate void return in traditional programming
  }

  @Test
  public void errorRuntimeExceptionMono() {
    Mono.error(new RuntimeException())
    .log().subscribe();
  }

  @Test
  public void errorExceptionMono() {
    Mono.error(new Exception())
            .log().subscribe();
  }

  @Test
  public void errorConsumerMono() {
    Mono.error(new Exception())
            .log()
            .subscribe(System.out::println,e -> System.out.println("error : " + e));
  }

  @Test
  public void errorDoOnError() {
    Mono.error(new Exception())
            .doOnError(e -> System.out.println("Error : " + e))
            .log().subscribe();
  }

  @Test
  public void errorOnErrorResumeMono() {
    Mono.error(new Exception())
            .onErrorResume(e -> {
              System.out.println("Error : " + e);
              return Mono.just("B");
            })
            .log().subscribe();

    // it's like catching exception
  }

  @Test
  public void errorOnErrorReturnMono() {
    Mono.error(new Exception())
            .onErrorReturn("B")
            .log().subscribe();

    // if you just want to return value not a mono then use the method onErrorReturn
  }


}
