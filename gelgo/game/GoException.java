package game;

/**
 * GoException is an exception class extended from {@link Exception} so that try, catch
 * blocks can simply catch GoExceptions.
 *
 * @author Steve Asher
*/

public class GoException extends Exception
{
        public GoException(String error)
        {
                super(error);
        }
}

