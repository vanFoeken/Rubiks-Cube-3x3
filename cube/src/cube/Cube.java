package cube;

import java.util.EnumMap;

final public class Cube {
    final private EnumMap<Sticker, Axis>[][][] cubelet = new EnumMap[Axis.INDEX.length][Axis.INDEX.length][Axis.INDEX.length];
    
    public Cube(Sticker[][] sticker) {
        for (Index x : Axis.INDEX) {
            for (Index y : Axis.INDEX) {
                for (Index z : Axis.INDEX) {
                    Index[] index = {x, y, z};

                    cubelet[x.getValue()][y.getValue()][z.getValue()] = new EnumMap(Sticker.class);
                    
                    for (Axis axis : Axis.values()) {
                        if (index[axis.ordinal()] instanceof Side) {
                            cubelet[x.getValue()][y.getValue()][z.getValue()].put(sticker[axis.ordinal()][((Side) index[axis.ordinal()]).ordinal()], axis);
                        }
                    }
                }
            }
        }
    }
    
    public void rotate(Axis axis, Side side, Index[] layers, Rotation rotation) {
        Axis vertical = axis.getVertical(side);
        Axis horizontal = axis.getHorizontal(side);
        
        for (Index layer : layers) {        
            EnumMap<Sticker, Axis>[][] cubelet = new EnumMap[Axis.INDEX.length][Axis.INDEX.length];
            
            for (Index row : Axis.INDEX) {
                for (Index column : Axis.INDEX) {
                    Index[] index = new Index[Axis.values().length];

                    index[axis.ordinal()] = layer;
                    index[vertical.ordinal()] = row;
                    index[horizontal.ordinal()] = column;

                    cubelet[row.getValue()][column.getValue()] = this.cubelet[index[Axis.x.ordinal()].getValue()][index[Axis.y.ordinal()].getValue()][index[Axis.z.ordinal()].getValue()];
                }
            }

            for (Index row : Axis.INDEX) {
                for (Index column : Axis.INDEX) {
                    Index[] index = new Index[Axis.values().length];

                    index[axis.ordinal()] = layer;
                    index[vertical.ordinal()] = rotation.getRow(column);
                    index[horizontal.ordinal()] = rotation.getColumn(row);

                    cubelet[row.getValue()][column.getValue()].keySet().forEach(facelet -> {
                        if (cubelet[row.getValue()][column.getValue()].get(facelet) == vertical) {
                            cubelet[row.getValue()][column.getValue()].put(facelet, horizontal);
                        } else if (cubelet[row.getValue()][column.getValue()].get(facelet) == horizontal) {
                            cubelet[row.getValue()][column.getValue()].put(facelet, vertical);
                        }
                    });

                    this.cubelet[index[Axis.x.ordinal()].getValue()][index[Axis.y.ordinal()].getValue()][index[Axis.z.ordinal()].getValue()] = cubelet[row.getValue()][column.getValue()];
                }
            }
        }
    }
    
    public Sticker[][][][] getStickers() {
        Sticker[][][][] sticker = new Sticker[Axis.values().length][Side.values().length][Axis.INDEX.length][Axis.INDEX.length];
        
        for (Index x : Axis.INDEX) {
            for (Index y : Axis.INDEX) {
                for (Index z : Axis.INDEX) {
                    Index[] index = {x, y, z};
                    
                    cubelet[x.getValue()][y.getValue()][z.getValue()].keySet().forEach(facelet -> {
                        Axis axis = cubelet[x.getValue()][y.getValue()][z.getValue()].get(facelet);
                        Side side = (Side) index[axis.ordinal()];
                        
                        sticker[axis.ordinal()][side.ordinal()][index[axis.getVertical(side).ordinal()].getValue()][index[axis.getHorizontal(side).ordinal()].getValue()] = facelet;
                    });
                }
            }
        }
        
        return sticker;
    }    
}
