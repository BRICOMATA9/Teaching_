import java.util.Arrays;
import java.util.Comparator;

class Person implements Comparable<Person> {
  private String firstName;
  private String surname;
  
  public Person(String firstName, String surname) {
    this.firstName = firstName;
    this.surname = surname;
  }
  public String getFirstName() {
    return firstName;
  }
  public String getSurname() {
    return surname;
  }
  public String toString() {
    return firstName + " " + surname;
  }
  
  public int compareTo(Person person) {
    int result = surname.compareTo(person.surname);
    return result == 0 ? firstName.compareTo(((Person) person).firstName) : result;
  }
}

class ComparePersons implements Comparator<Person> {

  public int compare(Person person1, Person person2) {
    int result = -person1.getSurname().compareTo(person2.getSurname());
    return result == 0 ? -person1.getFirstName().compareTo(person2.getFirstName()) : result;
  }
  
  public boolean equals(Object collator) {
    if (this == collator) {
      return true;
    }
    if (collator == null) {
      return false;
    }
    return getClass() == collator.getClass();
  }
}

public class MainClass {
  public static void main(String[] args) {
    Person[] authors = { new Person("A", "S"), 
                         new Person("J", "G"),
                         new Person("T", "C"), 
                         new Person("C", "S"), 
                         new Person("P", "C"), 
                         new Person("B", "B") };
    
    System.out.println("Original order:");
    for (Person author : authors) {
      System.out.println(author);
    }
    
    Arrays.sort(authors, new ComparePersons()); // Sort using comparator
    System.out.println("\nOrder after sorting using comparator:");
    for (Person author : authors) {
      System.out.println(author);
    }
    
    Arrays.sort(authors); // Sort using Comparable method
    System.out.println("\nOrder after sorting using Comparable method:");
    for (Person author : authors) {
      System.out.println(author);
    }
    
  }
}
