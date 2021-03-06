package TJCore.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import gregtech.common.blocks.MetaBlocks;

public class TJMetaBlocks {
    
    public static DraconicCasings DRACONIC_CASING;
    
    public static void init() {
        DRACONIC_CASING = new DraconicCasings();
        DRACONIC_CASING.setRegistryName("draconic_casing");
    
    }
    
    @SideOnly(Side.CLIENT)
    public static void registerItemModels() {
        registerItemModel(DRACONIC_CASING);
    
    }
    
    @SideOnly(Side.CLIENT)
    private static void registerItemModel(Block block) {
        for (IBlockState state : block.getBlockState().getValidStates()) {
            //noinspection ConstantConditions
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block),
                    block.getMetaFromState(state),
                    new ModelResourceLocation(block.getRegistryName(),
                            MetaBlocks.statePropertiesToString(state.getProperties())));
        }
    }
    
    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> String getPropertyName(IProperty<T> property, Comparable<?> value) {
        return property.getName((T) value);
    }
}
