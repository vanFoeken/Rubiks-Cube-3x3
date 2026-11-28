package cube;

public enum Axis {
    x,
    y,
    z;
    
    public Axis getHorizontal(Side side) {
        return values()[(ordinal() + 1 + side.ordinal()) % values().length];
    }
    
    public Axis getVertical(Side side) {
        return values()[(ordinal() + 2 - side.ordinal()) % values().length];
    }
    
    final public static Index[] INDEX = {Side.MIN, Index.CENTER, Side.PLUS};    
}
