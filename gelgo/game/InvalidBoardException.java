package game;

/**
 * InvalidBoardException is an exception class extended from GoException. It is thrown
 * when an invalid operation is attempted on a {@link Board}.                                 
 *
 * @author Steve Asher
*/

public class InvalidBoardException extends GoException
{
        InvalidBoardException(String error)
        {
                super(error);
        }
}
 
