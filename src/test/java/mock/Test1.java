package mock;

import static org.mockito.Mockito.* ; 
import org.mockito.InOrder;
import org.mockito.Mock; 
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Test1 {
    
    List mockedList = mock(List.class);
    
    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}
    
   
    @Test
    public void testMethode1()
    {
        mockedList.add("one"); 
        mockedList.clear(); 
       verify(mockedList).add("one"); 
       verify(mockedList).clear();
    }
    
    @Test
    public void testMethod2()
    {
       /* LinkedList mockedList = mock(LinkedList.class);
        
        //stubbing
        when(mockedList.get(0)).thenReturn("first");
        (mockedList.get(1)).thenThrow(new RuntimeException());
        
        //following prints "first"
        System.out.println(mockedList.get(0));
        
        //following throws runtime exception
        System.out.println(mockedList.get(1));
        
        //following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));
         
        //Although it is possible to verify a stubbed invocation, usually it's just redundant
        //If your code cares what get(0) returns then something else breaks (often before even verify() gets executed).
        //If your code doesn't care what get(0) returns then it should not be stubbed. Not convinced? See here.
        verify(mockedList).get(0);
        LinkedList mockedLinkedList = mock(LinkedList.class); 
        //stubbing 
        When(mockedLinkedList.get(0)).thenReturn("first"); 
        */
    }
    @Test
    public void testMethod3()
    {
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);
        
        //using mocks
        firstMock.add("was called first");
        secondMock.add("was called second");
        
        
        
        //create inOrder object passing any mocks that need to be verified in order
        InOrder inOrder = inOrder(firstMock, secondMock);
        
        //following will make sure that firstMock was called before secondMock
        inOrder.verify(firstMock).add("was called first");
        inOrder.verify(secondMock).add("was called second");

    }
    
    @Test
    public void testMethod4()
    {
        mockedList.add("once");
        
        mockedList.add("twice");
        mockedList.add("twice");
        
        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");
        
        //following two verifications work exactly the same - times(1) is used by default
        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");
        
        //exact number of invocations verification
        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");
        
        //verification using never(). never() is an alias to times(0)
        verify(mockedList, never()).add("never happened");
        
        //verification using atLeastOnce()
        verify(mockedList, atLeastOnce()).add("three times");
        

    }
    @Test
    public void testMethod7()
    {
        List mockOne = mock(List.class);
        List mockTwo = mock(List.class);
        List mockThree = mock(List.class);
        //using mocks - only mockOne is interacted
        mockOne.add("one");
        //ordinary verification
        verify(mockOne).add("one");
        
        //verify that method was never called on a mock
        verify(mockOne, never()).add("two");
        
        //verify that other mocks were not interacted
        verifyZeroInteractions(mockTwo, mockThree);

    }

}
