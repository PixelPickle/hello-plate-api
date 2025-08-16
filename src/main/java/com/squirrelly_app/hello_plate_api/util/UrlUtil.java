package com.squirrelly_app.hello_plate_api.util;

import com.squirrelly_app.hello_plate_api.exception.RequiredValueException;
import com.squirrelly_app.hello_plate_api.model.document.Recipe;
import com.squirrelly_app.hello_plate_api.model.hello_fresh.MenuCourseRecipe;
import io.micrometer.common.util.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UrlUtil {

    private static final String MENU_URL = "https://www.hellofresh.com/_next/data/%s/menus/%s-W%s.json?week=%s-W%s";
    private static final String RECIPE_URL = "https://www.hellofresh.com/_next/data/%s/recipe-detail/%s-%s.json";

    public static @NotNull String getMenuUrl(@Nullable String magic, @NotNull String year, @NotNull String weekNumber) throws RequiredValueException {

        if (StringUtils.isBlank(magic)) {
            throw new RequiredValueException("Invalid Argument [magic] is Required");
        }

        if (StringUtils.isBlank(year)) {
            throw new RequiredValueException("Invalid Argument [year] is Required");
        }

        if (StringUtils.isBlank(weekNumber)) {
            throw new RequiredValueException("Invalid Argument [week] is Required");
        }

        return String.format(MENU_URL, magic, year, weekNumber, year, weekNumber);

    }

    public static @NotNull String getRecipeUrl(@Nullable String magic, @NotNull Recipe recipe) {

        if (StringUtils.isBlank(magic)) {
            throw new RequiredValueException("Invalid Argument [magic] is Required");
        }

        if (StringUtils.isBlank(recipe.getSlug())) {
            throw new RequiredValueException("Invalid Argument [slug] is Required");
        }

        if (StringUtils.isBlank(recipe.getId())) {
            throw new RequiredValueException("Invalid Argument [id] is Required");
        }

        return String.format(RECIPE_URL, magic, recipe.getSlug(), recipe.getId());

    }

    public static @NotNull String getRecipeUrl(@Nullable String magic, @NotNull MenuCourseRecipe recipe) {

        if (StringUtils.isBlank(magic)) {
            throw new RequiredValueException("Invalid Argument [magic] is Required");
        }

        if (StringUtils.isBlank(recipe.getSlug())) {
            throw new RequiredValueException("Invalid Argument [slug] is Required");
        }

        if (StringUtils.isBlank(recipe.getId())) {
            throw new RequiredValueException("Invalid Argument [id] is Required");
        }

        return String.format(RECIPE_URL, magic, recipe.getSlug(), recipe.getId());

    }

}
