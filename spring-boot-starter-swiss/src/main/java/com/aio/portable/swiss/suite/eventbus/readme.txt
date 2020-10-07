


                                                                                           onEvent  ↗ Handler A1
                                                            ┌——————→ Subscriber1 —————┤
                                                            │              topic1,topic2           ↘ Handler A2
                                                            │
            Event                                           │
      topic1,topic2,topic3                                  │                             onEvent  ↗ Handler B1
Publisher ————→ EventBus ——┬—→  Namespace ———→┼——————→ Subscriber2 —————┤  Handler B2
                                  │                        │              topic1,topic3           ↘ Handler B3
                                  │                        │
                                  │                        │
                                  │                        │                             onEvent  ↗ Handler C1
                                  │                        └— — — — → Subscriber3 —————┤
                                  │                                            topic4              ↘ Handler C2
                                  │
                                  │
                                  │
                                  │
                                  │
                                  └—→  Namespace ———→ ...





