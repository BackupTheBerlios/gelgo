package game;

/**
 * OffBoardException is an exception class extended from GoException. It is thrown
 * when an operation is attempted on a {@link Board} outside of the Board's bounds.
 *
 * @author Steve Asher
*/

public class OffBoardException extends GoException 
{
        OffBoardException(String error)
        {
                super(error);
        }
}
