package TJCore.common.recipes.recipemaps;

import TJCore.common.recipes.ArmorInfuserRecipes;
import com.brandon3055.draconicevolution.api.itemupgrade.IUpgradableItem;
import com.brandon3055.draconicevolution.api.itemupgrade.UpgradeHelper;
import com.brandon3055.draconicevolution.items.ToolUpgrade;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.MatchingMode;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.api.recipes.ingredients.NBTIngredient;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

public class ArmorInfuserRecipeMap extends RecipeMap<SimpleRecipeBuilder> {
    
    public ArmorInfuserRecipeMap(String unlocalizedName, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, SimpleRecipeBuilder defaultRecipe, boolean isHidden) {
        super(unlocalizedName, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, defaultRecipe, isHidden);
    }
    
    /**
     * This is the REAL recipe checker.
     */
    @Nullable
    @Override
    public Recipe findRecipe(long voltage, List<ItemStack> inputs, List<FluidStack> fluidInputs, int outputFluidTankCapacity, MatchingMode matchingMode, boolean exactVoltage) {
        Recipe recipe = super.findRecipe(voltage, inputs, fluidInputs, outputFluidTankCapacity, matchingMode, exactVoltage);
        if (recipe != null && recipe.isHidden()) {
            ItemStack piece = ItemStack.EMPTY;
            for (ItemStack itemStack : inputs) {
                if (itemStack.getItem() instanceof IUpgradableItem) {
                    piece = itemStack;
                }
            }
            if (!piece.isEmpty()) {
                String upgradeName;
                IUpgradableItem asUp = (IUpgradableItem) piece.getItem();
                ItemStack upgrade = ItemStack.EMPTY;
                for (ItemStack stack : inputs) {
                    if (stack.getItem() instanceof ToolUpgrade) {
                        for (String upr : asUp.getValidUpgrades(piece)) {
                            if (ToolUpgrade.ID_TO_NAME.get(upgrade.getMetadata()).equals(upr)) {
                                upgrade = stack;
                                break;
                            }
                        }
                    }
                    if (!upgrade.isEmpty()) {
                        break;
                    }
                }
                upgradeName = ToolUpgrade.ID_TO_NAME.get(upgrade.getMetadata());
                int currLvl = UpgradeHelper.getUpgradeLevel(piece, upgradeName);
                if (currLvl == asUp.getMaxUpgradeLevel(piece, upgradeName)) {
                    return null;
                }
                if (!upgrade.isEmpty()) {
                    ItemStack armor = piece.copy();
                    UpgradeHelper.setUpgradeLevel(armor, upgradeName, currLvl + 1);
                    return new SimpleRecipeBuilder()
                            .append(ArmorInfuserRecipes.recipes[currLvl], 1, false, false)
                            .inputs(new CountableIngredient(new NBTIngredient(piece), 1))
                            .outputs(armor)
                            .build().getResult();
                }
            }
        }
        return null;
    }
}
