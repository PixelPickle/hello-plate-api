package com.squirrelly_app.hello_plate_api.util;

import com.squirrelly_app.hello_plate_api.exception.RequiredValueException;
import io.micrometer.common.util.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UrlUtil {

    private static final String MENU_URL = "https://www.hellofresh.com/_next/data/%s/menus/%s-W%s.json?week=%s-W%s";

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

}
