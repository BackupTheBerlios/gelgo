package network.general;
import java.util.*;

public class GoServerCommandQueue
{
	LinkedList list;
        
        public void push(GoServerCommand c)
        {
        	list.addLast(c);
        }
        
        public synchronized GoServerCommand pop()
        {
        	GoServerCommand tmp = null;
                
        	if (!isEmpty())
                {
        		tmp = (GoServerCommand) list.getFirst();
                	list.removeFirst();
                }
        	return tmp;
        }
        
        public boolean isEmpty()
        {
        	return list.isEmpty();
        }
        
        public GoServerCommandQueue()
        {
        	list = new LinkedList();
        }
}