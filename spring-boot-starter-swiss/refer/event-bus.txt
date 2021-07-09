


                                                                                           onEvent  ↗ Consumer A1
                                                            ┌——————→ Subscriber1 —————┤
                                                            │              topic1,topic2           ↘ Consumer A2
                                                            │
            Event                                           │
      topic1,topic2,topic3                                  │                             onEvent  ↗ Consumer B1
Publisher ————→ EventBus ——┬—→  Namespace ———→┼——————→ Subscriber2 —————┤  Consumer B2
                                  │                        │              topic1,topic3           ↘ Consumer B3
                                  │                        │
                                  │                        │
                                  │                        │                             onEvent  ↗ Consumer C1
                                  │                        └— — — — → Subscriber3 —————┤
                                  │                                            topic4              ↘ Consumer C2
                                  │
                                  │
                                  │
                                  │
                                  │
                                  └—→  Namespace ———→ ...





