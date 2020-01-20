package io.moonman.emergingtechnology.integration.crafttweaker.machines;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import io.moonman.emergingtechnology.integration.crafttweaker.CraftTweakerHelper;
import io.moonman.emergingtechnology.recipes.RecipeProvider;
import io.moonman.emergingtechnology.recipes.classes.FabricatorRecipe;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.emergingtechnology.Fabricator")
public class Fabricator
{
    @ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input)
	{
		FabricatorRecipe r = new FabricatorRecipe(RecipeProvider.fabricatorRecipes.size() + 1, CraftTweakerHelper.toStack(output), CraftTweakerHelper.toStack(input));
		CraftTweakerAPI.apply(new Add(r));
	}

	private static class Add implements IAction
	{
		private final FabricatorRecipe recipe;

		public Add(FabricatorRecipe recipe)
		{
			this.recipe = recipe;
		}

		@Override
		public void apply()
		{
			RecipeProvider.fabricatorRecipes.add(recipe);
		}

		@Override
		public String describe()
		{
			return "Adding Fabricator Recipe for "+ recipe.getOutput().getDisplayName();
		}
	}

	@ZenMethod
	public static void removeRecipe(IItemStack output)
	{
		CraftTweakerAPI.apply(new Remove(CraftTweakerHelper.toStack(output)));
	}

	private static class Remove implements IAction
	{
		private final ItemStack output;
		List<FabricatorRecipe> removedRecipes;

		public Remove(ItemStack output)
		{
			this.output = output;
		}

		@Override
		public void apply()
		{
			removedRecipes = RecipeProvider.removeFabricatorRecipesByOutput(RecipeProvider.fabricatorRecipes, output);
		}

		@Override
		public String describe()
		{
			return "Removing Fabricator Recipe for "+ output.getDisplayName();
		}
	}

	@ZenMethod
	public static void removeAll()
	{
		CraftTweakerAPI.apply(new RemoveAll());
	}

	private static class RemoveAll implements IAction
	{
		List<FabricatorRecipe> removedRecipes;

		public RemoveAll(){
		}

		@Override
		public void apply()
		{
            removedRecipes = new ArrayList<>(RecipeProvider.fabricatorRecipes);
            RecipeProvider.fabricatorRecipes = new ArrayList<FabricatorRecipe>();
		}

		@Override
		public String describe()
		{
			return "Removing all Fabricator Recipes";
		}
	}
}