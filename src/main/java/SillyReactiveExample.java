import reactor.core.publisher.Flux;
import reactor.core.publisher.Timed;

public class SillyReactiveExample {
    public static void main(String[] args) {
        Flux<Timed<Integer>> intPattern = Flux.range(0, 10000)
                .map(l -> 10000 - l)
                .window(10)
                .concatMap(flux -> flux.reduce(Integer::sum))
                // showcase difference
//                .concat(flux -> flux.reduce((a, b) -> a * b))
                .onBackpressureDrop()
                .takeWhile(num -> num % 5 == 0)
                .timed().log();
        intPattern.subscribe(
                value -> System.out.println("We got " + value.get() + " in " + value.elapsed().getNano() + " nanoseconds"),
                error -> System.err.println("Did something bad here " + error)
        );
        intPattern.all(integerTimed -> integerTimed
                        .get()
                        .toString()
                        .endsWith("55"))
                .subscribe(
                        v -> System.out.println("All summed values over a window end in 55? " + v)
                );

    }
}
