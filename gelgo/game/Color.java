package game;

import java.io.*;

/** Just provides two colors for {@link Stone}s. */
public final class Color  implements Serializable   // final class!!
{   
    private Color() {}         // private constructor!!

    public static final Color WHITE = new Color();
    public static final Color BLACK = new Color();
}
