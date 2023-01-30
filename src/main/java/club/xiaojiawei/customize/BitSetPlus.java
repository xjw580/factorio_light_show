package club.xiaojiawei.customize;

import java.util.BitSet;

/**
 * @author 肖嘉威
 * @date 2023/1/30 上午10:11
 */
public class BitSetPlus extends BitSet {

    private final static BitSet BIT_SET = new BitSet();

    public BitSetPlus() {
        super(128);
    }

    public BitSetPlus(int nbits) {
        super(nbits);
    }

    public BitSetPlus itemMoveRight(int count) {
        if(!this.isEmpty()) {
            BitSet b = this.get(count, this.size());
            this.clear();
            this.or(b);
        }
        return this;
    }

    public BitSetPlus itemMoveLeft(int count) {
        if(!this.isEmpty()) {
            int bsSize = this.size() - count;
            BIT_SET.clear();
            for (int i = 0; i < bsSize; i++) {
                BIT_SET.set(count++ , this.get(i));
            }
            this.clear();
            this.or(BIT_SET);
        }
        return this;
    }

    public BitSetPlus moveRight(int count) {
        BitSetPlus bitSetPlus = new BitSetPlus();
        bitSetPlus.or(this.get(count, this.size()));
        return bitSetPlus;
    }

    public BitSetPlus moveLeft(int count) {
        BitSetPlus bitSetPlus = new BitSetPlus();
        int bsSize = this.size() - count;
        BIT_SET.clear();
        for (int i = 0; i < bsSize; i++) {
            BIT_SET.set(count++ , this.get(i));
        }
        bitSetPlus.or(BIT_SET);
        return bitSetPlus;
    }

    public BitSetPlus itemAnd(BitSet set) {
        super.and(set);
        return this;
    }

    public BitSetPlus ands(BitSet set){
        BitSetPlus bitSetPlus = new BitSetPlus();
        bitSetPlus.or(this);
        bitSetPlus.and(set);
        return bitSetPlus;
    }

    public BitSetPlus itemOr(BitSet set) {
        super.or(set);
        return this;
    }

    public long getValue(){
        return this.isEmpty()? 0L : this.toLongArray()[0];
    }
}
