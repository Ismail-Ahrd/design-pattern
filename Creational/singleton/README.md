# Singleton Pattern

## Singleton Pattern Principles:
- Singleton pattern restricts the instantiation of a class and ensures that only one instance of the class exists in the Java Virtual Machine.
- The singleton class must provide a global access point to get the instance of the class.
- Singleton pattern is used for logging, drivers objects, caching, and thread pool.
- Singleton design pattern is also used in other design patterns like Abstract Factory, Builder, Prototype, Facade, etc.
- Singleton design pattern is used in core Java classes also (for example, java.lang.Runtime, java.awt.Desktop).

## Java Singleton Pattern Implementation:
To implement a singleton pattern, we have different approaches, but all of them have the following common concepts:
- Private constructor to restrict instantiation of the class from other classes.
- Private static variable of the same class that is the only instance of the class.
- Public static method that returns the instance of the class, this is the global access point for the outer world to get the instance of the singleton class.
### 1. Eager initialization:
In eager initialization, the instance of the singleton class is created at the time of class loading. The drawback to eager initialization is that the method is created even though the client application might not be using it.<br />
If your singleton class is not using a lot of resources, this is the approach to use. But in most of the scenarios, singleton classes are created for resources such as File System, Database connections, etc. We should avoid the instantiation unless the client calls the getInstance method. Also, this method doesn’t provide any options for exception handling.<br />
See EagerInitializedSingleton.java file.
### 2. Static block initialization:
Static block initialization implementation is similar to eager initialization, except that instance of the class is created in the static block that provides the option for exception handling.<br />
Both eager initialization and static block initialization create the instance even before it’s being used and that is not the best practice to use.<br />
See StaticBlockSingleton.java file.
### 3. Lazy Initialization:
Lazy initialization method to implement the singleton pattern creates the instance in the global access method.
```java
public static LazyInitializedSingleton getInstance() {
        if (instance == null) {
            instance = new LazyInitializedSingleton();
        }
        return instance;
    }
```
The preceding implementation works fine in the case of the single-threaded environment, but when it comes to multi-threaded systems, it can cause issues if multiple threads are inside the if condition at the same time. It will destroy the singleton pattern and both threads will get different instances of the singleton class.<br />
See LazyInitializedSingleton.java file.
### 4. Thread Safe Singleton:
A simple way to create a thread-safe singleton class is to make the global access method synchronized so that only one thread can execute this method at a time. Here is a general implementation of this approach:
```java
public static synchronized ThreadSafeSingleton getInstance() {
        if (instance == null) {
            instance = new ThreadSafeSingleton();
        }
        return instance;
    }
```
The preceding implementation works fine and provides thread-safety, but it reduces the performance because of the cost associated with the synchronized method, although we need it only for the first few threads that might create separate instances. To avoid this extra overhead every time, double-checked locking principle is used. In this approach, the synchronized block is used inside the if condition with an additional check to ensure that only one instance of a singleton class is created. The following code snippet provides the double-checked locking implementation:
```java
public static ThreadSafeSingleton getInstanceUsingDoubleLocking() {
    if (instance == null) {
        synchronized (ThreadSafeSingleton.class) {
            if (instance == null) {
                instance = new ThreadSafeSingleton();
            }
        }
    }
    return instance;
}
```
See ThreadSafeSingleton.java file.
### 5. Bill Pugh Singleton Implementation:
Prior to Java 5, the Java memory model had a lot of issues, and the previous approaches used to fail in certain scenarios where too many threads tried to get the instance of the singleton class simultaneously. So Bill Pugh came up with a different approach to create the singleton class using an inner static helper class. 
```java
public class BillPughSingleton {

    private BillPughSingleton(){}

    private static class SingletonHelper {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
```
Notice the private inner static class that contains the instance of the singleton class. When the singleton class is loaded, SingletonHelper class is not loaded into memory and only when someone calls the getInstance() method, this class gets loaded and creates the singleton class instance. This is the most widely used approach for the singleton class as it doesn’t require synchronization.<br />
See BillPughSingleton.java file.
### 6. Using Reflection to destroy Singleton Pattern:
Reflection can be used to destroy all the previous singleton implementation approaches. Here is an example class:
```java
import java.lang.reflect.Constructor;

public class ReflectionSingletonTest {

    public static void main(String[] args) {
        EagerInitializedSingleton instanceOne = EagerInitializedSingleton.getInstance();
        EagerInitializedSingleton instanceTwo = null;
        try {
            Constructor[] constructors = EagerInitializedSingleton.class.getDeclaredConstructors();
            for (Constructor constructor : constructors) {
                // This code will destroy the singleton pattern
                constructor.setAccessible(true);
                instanceTwo = (EagerInitializedSingleton) constructor.newInstance();
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(instanceOne.hashCode());
        System.out.println(instanceTwo.hashCode());
    }

}
```
When you run the preceding test class, you will notice that hashCode of both instances is not the same which destroys the singleton pattern. Reflection is very powerful and used in a lot of frameworks like Spring and Hibernate.<br />
See ReflectionSingletonTest.java file.
### 7. Enum Singleton:
To overcome this situation with Reflection, Joshua Bloch suggests the use of enum to implement the singleton design pattern as Java ensures that any enum value is instantiated only once in a Java program. Since Java Enum values are globally accessible, so is the singleton. The drawback is that the enum type is somewhat inflexible (for example, it does not allow lazy initialization).<br />
See EnumSingleton.java file.
### 8. Serialization and Singleton:
Sometimes in distributed systems, we need to implement Serializable interface in the singleton class so that we can store its state in the file system and retrieve it at a later point in time.<br />
The problem with serialized singleton class is that whenever we deserialize it, it will create a new instance of the class.
So it destroys the singleton pattern. To overcome this scenario, all we need to do is provide the implementation of ```readResolve()``` method.
```java
protected Object readResolve() {
    return getInstance();
}
```
This method is automatically called during deserialization, and it returns the existing singleton instance. By providing this method, you ensure that the deserialization process does not create a new instance, and the singleton pattern is maintained (The default deserialization mechanism creates a new object based on the serialized data).<br />
See SerializedSingleton.java and ReflectionSingletonTest.java files