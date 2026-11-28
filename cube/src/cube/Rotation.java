package cube;

public enum Rotation {
    CLOCKWISE {
        @Override
        public Index getRow(Index column) {
            return column;
        }
        @Override
        public Index getColumn(Index row) {
            return Axis.INDEX[Side.PLUS.getValue() - row.getValue()];
        }
    },
    COUNTER_CLOCKWISE {
        @Override
        public Index getRow(Index column) {
            return Axis.INDEX[Side.PLUS.getValue() - column.getValue()];
        }
        @Override
        public Index getColumn(Index row) {
            return row;
        }
    };
    
    public abstract Index getRow(Index column);
    public abstract Index getColumn(Index row);
}
