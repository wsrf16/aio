


                                                                                           onEvent  ↗ Action A1
                                                            ┌——————→ Subscriber1 —————┤
                                                            │              topic1,topic2           ↘ Action A2
                                                            │
            Event                                           │
      topic1,topic2,topic3                                  │                             onEvent  ↗ Action B1
Publisher ————→ EventBus ——┬—→  Namespace ———→┼——————→ Subscriber2 —————┤  Action B2
                                  │                        │              topic1,topic3           ↘ Action B3
                                  │                        │
                                  │                        │
                                  │                        │                             onEvent  ↗ Action C1
                                  │                        └— — — — → Subscriber3 —————┤
                                  │                                            topic4              ↘ Action C2
                                  │
                                  │
                                  │
                                  │
                                  │
                                  └—→  Namespace ———→ ...





