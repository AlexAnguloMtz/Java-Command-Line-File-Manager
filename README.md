Personal project to sharpen my understanding of the **'Hexagonal Architecture'**, also called **'Clean Architecture'**
by **Robert C. Martin**. It is a Java Command Line File Manager. (It is in Spanish but my upcoming projects will be in English).

This project should be executed directly from a Terminal.

- Architecture: 
Hexagonal Architecture with the following layers: 
    - Domain Layer 
        - Services Layer
    - Application Layer
    - View Layer

- Design Patterns used: 
    - Command Pattern
    - Facade Pattern
    - Dependency Injection
    - The five SOLID Principles

- Domain Layer: 
    - The 'FileService' package, which contains classes that are the core of our application. 
      These classes manage the navigation between directories and the CRUD operations on the local file system.

    - The 'CommandService' package, which contains the 'CommandService' class. 
      This class defines an inner 'Command' interface which essentially declares a single most important method 
      called 'execute()'. This is so we can use the **Command Design Pattern** to manage files. 
      The CommandService class receives in its constructor a collection of classes that implement the 
      'Command' interface, thus the Main class of the program applies **(Dependency Injection)** 
      to define the behaviuor of the application. 

    - Every user operation in the program is represented by a different Command implementation class.
      This follows the **Single Responsibility Principle** because every class has only one reason to change. 
      This follows as well the **Open Closed Principle.** because we can add in any moment a brand new class
      that implements the 'Command' interface, and give our program new features and functionality. 
      This is referred by Robert C. Martin as a **'Plugin Architecture'**. 

    - The 'Commands' package, which includes all the implementations for the Command Pattern. 
      They are 'CreateFileCommand', 'WriteFileCommand', 'ReadCommand', 'ListCommand', 'AdvanceCommand',
      'GoBackCommand' and 'ExitCommand'. These classes manipulate the core classes in our 'FileService' package
      to perform the required tasks. These collection of Commands can be easily extended with new classes
      to add new functionality.

- Application Layer: 
    - Contains 'FileManagerImplementation', a class that acts as a thin layer to coordinate the application, thus
      it is essentially a Facade Pattern implementation.

- View Layer: 
    - Has the responsibility of handling the command line user interface and the messages that the user can 
      read on screen. 

Overall this was a fun project and I learned a lot with it, which I am grateful for.         
