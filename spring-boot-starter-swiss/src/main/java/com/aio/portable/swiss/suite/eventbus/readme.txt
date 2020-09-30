


                                                                                           onEvent  ↗ Handler A1
                                                            ┌——————→ Subscriber1 —————┤
                                                            │                tag1,tag2             ↘ Handler A2
                                                            │
            Event                                           │
        tag1,tag2,tag3                                      │                             onEvent  ↗ Handler B1
Publisher ————→ EventBus ——┬—→  Namespace ———→┼——————→ Subscriber2 —————┤  Handler B2
                                  │                        │                tag1,tag3             ↘ Handler B3
                                  │                        │
                                  │                        │
                                  │                        │                             onEvent  ↗ Handler C1
                                  │                        └— — — — → Subscriber3 —————┤
                                  │                                            tag4                ↘ Handler C2
                                  │
                                  │
                                  │
                                  │
                                  │
                                  └—→  Namespace ———→ ...





