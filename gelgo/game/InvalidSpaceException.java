package game;

/**
 * InvalidSpaceException is an exception class extended from GoException. It is thrown
 * when a move operation is attempted on a {@link Board} space that is already occupied.
 *
 * @author Steve Asher
*/

public class InvalidSpaceException extends GoException
{
        InvalidSpaceException(String error)
        {
                super(error);
        }
}
 
