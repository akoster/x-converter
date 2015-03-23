# Week 24 #

> Now we can read content, try to find the URL's in the page.
> Hints:
  1. assuming this is an HTML page, URL's will be preceded by href= (case insensitive)
  1. consider presence/absence of surrounding quotes or whitespace
  1. consider relative URL's
  1. find the end of the URL by scanning for:
    1. tab: \t
    1. newline: \n
    1. carriage return: \r
    1. quote: " or '
    1. tag closure: >
    1. hash: # (marks the start of an anchor)

> Examples:
  1. 

&lt;A Href="mypic.gif"&gt;

showme

Unknown end tag for &lt;/a&gt;


  1. <a>intro</a>
  1. 

&lt;A href=www.google.com&gt;

google's site

&lt;/A&gt;



# Week 23 #

> Web crawler
  1. we are going to write a web crawler, which accesses a web pages using UrlConnection
  1. the crawler starts with an empty work queue
  1. an inital URL (command line argument) is added to the queue
  1. read the content from the URL (the content is an HTML page)
  1. find the hyperlinks (http://...) in the page and add them to the work queue
  1. keep handling work from the queue until a certain limit is reached or there is
> > no more work on the queue
  1. limit can be: elapsed time or number of handled pages


> for this week start with reading content from a URL using Java

# Week 20 #

  1. Create an application which rotates each character in an input String 180 degrees (see WordRotations)
  1. The same for 90 degrees clockwise
  1. download a dictionary of english words somewhere
  1. Extend the application so that it can find all the rotonyms in the dictionary
  1. Extend the application to also find rotodromes in the dictionary

# Week 19 #

Read about the 'bridge pattern'. Look up Gang of Four (GoF) software design patterns.
Think about using this pattern to abstract both the commands and the storage implementations from the DataStore application.

See doc/design.txt
Create a rough design for the converter application so that we can do conversions using converter plugins.

# Week 13 #

Add 'clear' and 'delete' commands to StorageTest (first expand Storage with two abstract methods for this).

Add log4j.properties

Apply the 'command pattern':
Create an interface called 'Command' with two methods:
> public void execute(Scanner s);
> public boolean is(String command);
For each command (set, read, store, etc) create an implementation of this Command interface. When a user enters a command, the corresponding Command implementation should be instantiated, and the execute method should be called on it.

# Week 12 #

Add 'clear' and 'delete' commands to StorageTest (first expand Storage with two abstract methods for this).

Read about Google Guice (look at an online example of how this works)

Add maven2 to the project (create a pom.xml and create the correct project structure)
Add Log4j, Junit4 and Google guice dependencies to the pom.

# Week 11 #

Read about dependency injection (also called 'inversion of control'). Notable frameworks for this in Java are Spring and Google Guice. Bonus: resolve [issue 3](https://code.google.com/p/x-converter/issues/detail?id=3).

Resolve [issue 4](https://code.google.com/p/x-converter/issues/detail?id=4): Expand StorageTest so that the user can input commands