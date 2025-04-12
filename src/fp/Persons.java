package fp;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Comparator;

public class Persons {

    private final List<Person> persons = List.of(
            new Person(1, "Alice", 22),
            new Person(2, "Bob", 20),
            new Person(3, "Carol", 21));

    @Test
    public void findsPersonById() {
        Optional<Person> personOptional = persons.stream()
                .filter(p -> p.getId() == 2)
                .findFirst();
        System.out.println("a) Leitud isik (Optional): " + personOptional);
        assertThat(personOptional).isPresent();
        assertThat(personOptional.get()).isEqualTo(new Person(2, "Bob", 20));
    }
    @Test
    public void removePersonById() {
        List<Person> personsWithoutId2 = persons.stream()
                .filter(p -> p.getId() != 2)
                .toList();
        System.out.println("b) Isikud ilma id=2: " + personsWithoutId2);
        assertThat(personsWithoutId2).containsExactlyInAnyOrder(
                new Person(1, "Alice", 22),
                new Person(3, "Carol", 21)
        );
        assertThat(personsWithoutId2).hasSize(2);
    }
    @Test
    public void findsPersonNames() {
        String names = persons.stream()
                .map(Person::getName)
                .collect(Collectors.joining(", "));

        System.out.println("c) Nimede sõne: " + names);
        assertThat(names).isEqualTo("Alice, Bob, Carol");
    }
    @Test
    public void reverseSortedByAge() {
        List<Person> sortedPersons = persons.stream()
                .sorted(Comparator.comparingInt(Person::getAge).reversed())
                .toList();
        System.out.println("d) Isikud sorteerituna vanuse järgi kahanevalt: " + sortedPersons);
        assertThat(sortedPersons).containsExactly(
                new Person(1, "Alice", 22),
                new Person(3, "Carol", 21),
                new Person(2, "Bob", 20)
        );
        assertThat(sortedPersons)
                .extracting(Person::getAge)
                .containsExactly(22, 21, 20);
    }
    public static class Person {
        private final int id;
        private final String name;
        private final int age;

        public Person(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public int getAge() { return age; }
        @Override
        public String toString() {
            return "Person{id=" + id + ", name='" + name + '\'' + ", age=" + age + '}';
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return id == person.id && age == person.age && java.util.Objects.equals(name, person.name);
        }
        @Override
        public int hashCode() {
            return java.util.Objects.hash(id, name, age);
        }
    }
}