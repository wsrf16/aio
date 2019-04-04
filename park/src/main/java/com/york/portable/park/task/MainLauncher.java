package com.york.portable.park.task;

import java.util.*;
import java.util.stream.Collectors;

public class MainLauncher {
    private static MainLauncher instance = new MainLauncher();
    boolean isRunning = false;

    public static MainLauncher instance() {
        return instance;
    }

    Map<Integer, AbsTaskHandler> taskHandlers = new TreeMap<Integer, AbsTaskHandler>();

    private MainLauncher() {
//        registerHandler();

//        for (int i = 0; i < taskHandlers.size() - 1; i++) {
//            if (taskHandlers.Values[i] != null && taskHandlers.Values[i + 1] != null)
//                taskHandlers.Values[i].SetNextHandler(taskHandlers.Values[i + 1]);
//            else
//                break;

        List<Map.Entry<Integer, AbsTaskHandler>> sortList = new ArrayList<>(taskHandlers.entrySet()).stream().sorted(Comparator.comparing(c -> c.getKey())).collect(Collectors.toList());
        for (Map.Entry<Integer, AbsTaskHandler> entry : sortList) {

        }
    }

//    private void registerHandler() {
//        //Activator.CreateInstance(handler.GetType());
//        Type[] types = Assembly.GetExecutingAssembly().GetTypes();
//        foreach(Type type in types)
//        {
//            RegisterHandlerAttribute attr = type.GetCustomAttribute < RegisterHandlerAttribute > (true);
//            if (attr != null) {
//                AbsTaskHandler handler = Activator.CreateInstance(type) as AbsTaskHandler;
//                taskHandlers.Add(attr.Sort, handler);
//            }
//        }
//    }
//
//    void Launch() {
//        this.IsRunning = true;
//        System.IO.File.WriteAllText("d:\\111.txt", "111");
//        taskHandlers.First().Value.Handle(TaskRequest.CreateInstance());
//        this.IsRunning = false;
//    }
}
