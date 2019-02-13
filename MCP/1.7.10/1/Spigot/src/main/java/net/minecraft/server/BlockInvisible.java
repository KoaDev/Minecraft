package net.minecraft.server;

import java.util.Random;

public class BlockInvisible extends BlockHalfTransparent {

    public BlockInvisible(Material material, boolean flag) {
        super("invisible", material, flag);
        this.a(CreativeModeTab.b);
    }

    public int a(Random random) {
        return 0;
    }

    public boolean d() {
        return false;
    }

    protected boolean E() {
        return true;
    }
}