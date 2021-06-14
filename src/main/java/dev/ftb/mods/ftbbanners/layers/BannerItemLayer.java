package dev.ftb.mods.ftbbanners.layers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
	public void write(CompoundTag nbt) {
		super.write(nbt);
		ListTag list = new ListTag();

		this.items.forEach(e -> list.add(e.save(new CompoundTag())));

		nbt.put("items", list);
		nbt.putBoolean("spinning", this.spinning);
	}

	@Override
	public void read(CompoundTag nbt) {
		super.read(nbt);

		ListTag items = nbt.getList("items", Constants.NBT.TAG_COMPOUND);
		this.items = items.stream().map(e -> ItemStack.of((CompoundTag) e)).collect(Collectors.toList());

		this.spinning = nbt.getBoolean("spinning");
	}
}
