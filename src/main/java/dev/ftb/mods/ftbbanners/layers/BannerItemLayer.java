package dev.ftb.mods.ftbbanners.layers;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BannerItemLayer extends BannerLayer {
    public boolean spinning = false;
    public List<ItemStack> items = new ArrayList<ItemStack>() {{
        this.add(new ItemStack(Items.CLAY_BALL));
    }};

    @Override
    public void write(CompoundNBT nbt) {
        super.write(nbt);
        ListNBT list = new ListNBT();

        this.items.forEach(e -> list.add(e.save(new CompoundNBT())));

        nbt.put("items", list);
        nbt.putBoolean("spinning", this.spinning);
    }

    @Override
    public void read(CompoundNBT nbt) {
        super.read(nbt);

        ListNBT items = nbt.getList("items", Constants.NBT.TAG_COMPOUND);
        this.items = items.stream().map(e -> ItemStack.of((CompoundNBT) e)).collect(Collectors.toList());

        this.spinning = nbt.getBoolean("spinning");
    }
}
