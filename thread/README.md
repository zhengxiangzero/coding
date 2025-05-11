## Thread
### 1. [两个线程交替打印1-200](./src/main/java/club/zhengxiang/coding/thread/Thread1.java)
* 方法一：volatile + flag 忙等待模式，浪费大量CPU资源
* 方法二：synchronized 等待/通知机制，效率高，实现简单直观
* 方法三：一个 semaphore 公平模式，不能完全实现交替打印
  * 因为 semaphore 只能控制同时执行的线程数量，但不能控制具体的执行顺序，如果线程A释放许可时，线程B还没来得及调用 acquire 进入等待队列，那么线程A会再次获得许可
  * 同时 sleep 和 release 的执行顺序，也会影响交替打印的结果
  * sleep 在 release 之前，更容易交替打印
  ```text
  线程A: acquire() -> 获得许可
  线程A: 打印
  线程A: sleep(1) -> 线程A休眠,但仍持有许可
  // 这时许可不可用,所以线程B必须进入等待队列
  线程A: release() -> 许可直接给到等待队列中的线程B
  ```
  * sleep 在 release 之后，很难完全交替打印
  ```text
  线程A: acquire() -> 获得许可
  线程A: 打印
  线程A: release() -> 释放许可
  // 这时线程B调用 acquire() 会直接获取许可,而不是进入等待队列
  // 等线程A休眠结束后,许可可能已经被线程B使用并释放了
  // 这时线程A和线程B又会开始竞争可用的许可
  线程A: sleep(1) -> 线程A休眠1ms
  ```