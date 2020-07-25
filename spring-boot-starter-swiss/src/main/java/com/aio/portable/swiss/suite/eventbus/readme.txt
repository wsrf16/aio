

                                                                                                Topic 1      Subscriber A|Subscriber B
                                                                                    onEvent  ↗
                                                        ┌——————→ Listener —————┤
                                                        │                                   ↘
                                                        │                                      Topic 2      Subscriber F|Subscriber G
            Event                        Listener       │   Topic 2                onEvent  ↗
Publisher ————→ EventBus ——┬—→  Group ———→┼——————→ Listener —————┤
                                  │                    │                                   ↘
                                  │                    │                                      Topic 3      Subscriber M
                                  │                    │
                                  │                    │                                      Subscriber 4
                                  │                    │                          onEvent  ↗
                                  │                    └——————→ Listener —————┤
                                  │                                                         ↘
                                  │                                                            Subscriber 5
                                  │
                                  │
                                  │
                                  │     Listener
                                  └—→  Group ———→ ...





