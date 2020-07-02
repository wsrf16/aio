

                                                                        Subscriber 1
                                     Topic 1                onEvent  ↗
                                ┌——————→ Listener —————┼
            Event               │                                   ↘
Publisher ————→ EventBus —┤                                      Subscriber 2
                                │   Topic 2                onEvent  ↗
                                ├——————→ Listener —————┼
                                │                                   ↘
                                │                                      Subscriber 3
                                │
                                │                                      Subscriber 4
                                │                                   ↗
                                └——————→ Listener —————┼
                                                                     ↘
                                                                        Subscriber 5





