package net.minecraft.server;


import org.bukkit.ChatColor;

public class ItemPotionFall extends Item {

    public ItemPotionFall() {
        this.e(1);
    }

    public ItemStack b(ItemStack itemstack, World world, EntityHuman entityhuman) {
        if (!entityhuman.abilities.canInstantlyBuild) {
            --itemstack.count;
        }

        if (!world.isStatic) {
        	entityhuman.addEffect(new MobEffect(MobEffectList.fall.id, 2400, 0));
        }

        return itemstack.count <= 0 ? new ItemStack(Items.GLASS_BOTTLE) : itemstack;
    }

    public int d_(ItemStack itemstack) {
        return 15;
    }

    public EnumAnimation d(ItemStack itemstack) {
        return EnumAnimation.DRINK;
    }

    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        entityhuman.a(itemstack, this.d_(itemstack));
        return itemstack;
    }
    
    public String n(ItemStack itemstack) {
    	return ChatColor.GRAY + "Falling Resistance (2:00)";
    }
}