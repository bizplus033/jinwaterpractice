package com.example.jinwaterpractice.main.custom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CustomSetNavigationUtil {

    public static List<String> setNavigation(String... items) {
        //return Arrays.asList(items); 이건 불변 리스트를 만들어준다
         return new ArrayList<>(Arrays.asList(items));
    }
}
