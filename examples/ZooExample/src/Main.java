import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        // get an instance of each animal, then call each method 0-100 times
        Aardvark aardvark = new Aardvark();
        Badger badger = new Badger();
        Camel camel = new Camel();
        Deer deer = new Deer();
        Eagle eagle = new Eagle();
        Fox fox = new Fox();
        Gecko gecko = new Gecko();
        Horse horse = new Horse();
        Ibex ibex = new Ibex();
        Jaguar jaguar = new Jaguar();
        Kangaroo kangaroo = new Kangaroo();
        Lemur lemur = new Lemur();
        Mallard mallard = new Mallard();
        Newt newt = new Newt();
        Octopus octopus = new Octopus();
        Parrot parrot = new Parrot();
        Quail quail = new Quail();
        Rabbit rabbit = new Rabbit();
        Salmon salmon = new Salmon();
        Tortoise tortoise = new Tortoise();
        Uakari uakari = new Uakari();
        Vulture vulture = new Vulture();
        Walrus walrus = new Walrus();
        Xerus xerus = new Xerus();
        Yak yak = new Yak();
        Zebra zebra = new Zebra();

        // manually cast for older java versions
        Animal[] animals = { (Animal) aardvark, (Animal) badger, (Animal) camel, (Animal) deer, (Animal) eagle, (Animal) fox,
                (Animal) gecko, (Animal) horse, (Animal) ibex, (Animal) jaguar, (Animal) kangaroo, (Animal) lemur, (Animal) mallard,
                (Animal) newt, (Animal) octopus, (Animal) parrot, (Animal) quail, (Animal) rabbit, (Animal) salmon, (Animal) tortoise,
                (Animal) uakari, (Animal) vulture, (Animal) walrus, (Animal) xerus, (Animal) yak, (Animal) zebra};
        for (Animal animal : animals) {
            for (Method method : animal.getClass().getDeclaredMethods()) {
                // call the method anywhere from 0-1000 times
                for (int i = 0; i < Math.random() * 1000; i++) {
                    method.invoke(animal);
                }
            }
        }
    }
}
