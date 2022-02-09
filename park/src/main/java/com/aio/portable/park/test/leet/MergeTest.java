package com.aio.portable.park.test.leet;

import java.util.HashMap;

public class MergeTest {
    public static final void sort() {
        Integer[] a = {1,3,5,7,8};
        Integer[] b = {2,3,6,9,11};

        HashMap<Integer[], Integer> context = new HashMap<>();
        Integer[] currentArray;
        Integer[] result = new Integer[a.length + b.length];
        context.put(a, 0);
        context.put(b, 0);

        for (int i = 0; i < a.length + b.length; i++) {
            Integer aLoc = context.containsKey(a) ? context.get(a) : null;
            Integer bLoc = context.containsKey(b) ? context.get(b) : null;

            if (!context.containsKey(a) && !context.containsKey(b))
                break;
            else if (context.containsKey(a) && !context.containsKey(b))
                currentArray = a;
            else if (!context.containsKey(a) && context.containsKey(b))
                currentArray = b;
            else
                currentArray = a[aLoc] <= b[bLoc] ? a : b;

            Integer loc = context.get(currentArray);
            result[i] = currentArray[loc];
            if (loc + 1 >= currentArray.length)
                context.remove(currentArray);
            else
                context.put(currentArray, loc + 1);

        }
    }
}
